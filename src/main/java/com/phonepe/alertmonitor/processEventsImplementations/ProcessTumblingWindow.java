package com.phonepe.alertmonitor.processEventsImplementations;

import com.phonepe.alertmonitor.repositories.ClientConfigurationRepository;
import com.phonepe.alertmonitor.repositories.GlobalCounterRepository;
import com.phonepe.alertmonitor.entities.ClientConfiguration;
import com.phonepe.alertmonitor.entities.GlobalCounter;
import com.phonepe.alertmonitor.enums.EventType;
import com.phonepe.alertmonitor.exceptionRequests.ExceptionRaise;
import com.phonepe.alertmonitor.interfaces.WindowOperation;
import com.phonepe.alertmonitor.logDispatcher.LogDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.Optional;


@Component
public class ProcessTumblingWindow implements WindowOperation {

    private final LogDispatcher logDispatcher;
    private final GlobalCounterRepository globalCounterRepository;

    private final ClientConfigurationRepository clientConfigurationRepository;

    @Autowired
    public ProcessTumblingWindow(LogDispatcher logDispatcher,
                                 ClientConfigurationRepository clientConfigurationRepository,
                                 GlobalCounterRepository globalCounterRepository
                                 ) {
        this.clientConfigurationRepository = clientConfigurationRepository;
        this.logDispatcher = logDispatcher;
        this.globalCounterRepository = globalCounterRepository;
    }

    @Override
    public void updateGlobalCounterSlidingWindow(ExceptionRaise exceptionRaise) {
        GlobalCounter globalCounter = findByClientAndEventType(
                exceptionRaise.getClient(),
                exceptionRaise.getEventType());
        ClientConfiguration clientConfiguration = getByClientAndEventType(globalCounter.getClient(),
                globalCounter.getEventType());
        Long currentEpochSecond = Instant.now().getEpochSecond();
        logDispatcher.logStart(clientConfiguration);
        if (globalCounter != null) {
            Long window = currentEpochSecond - globalCounter.getTimeNow();
            if (window <= globalCounter.getWindowSizeInSecs()) {
                if (globalCounter.getGc() + 1 >= globalCounter.getCount()) {
                    /**
                     threshold breached
                     */
                    logDispatcher.logThreshold(globalCounter);

                    if (clientConfiguration != null) {
                        clientConfiguration.getDispatchStrategyList().stream().forEach(dispatchStrategy -> {
                            switch (dispatchStrategy.getType()) {
                                case CONSOLE:
                                    logDispatcher.logConsoleDispatch(dispatchStrategy);
                                    break;
                                case EMAIL:
                                    logDispatcher.logEmailDispatch();
                                    break;
                            }
                        });
                    }
                }
            }

            if (currentEpochSecond - globalCounter.getTimeUpdated() >= globalCounter.getWindowSizeInSecs()) {
                /**
                 * threshold did not breach
                 * check window, if exceeded, update the gc to 1 and timenow to current epoch
                 */
                globalCounter.setTimeUpdated(globalCounter.getTimeNow());
                globalCounter.setTimeNow(currentEpochSecond);
                globalCounter.setGc(1L);
                saveGlobalCounter(globalCounter);
            } else {
                // Increment gc and save
                globalCounter.setGc(globalCounter.getGc() + 1);
                saveGlobalCounter(globalCounter);
            }
        }
        logDispatcher.logEnd(clientConfiguration);
    }

    private ClientConfiguration getByClientAndEventType(String client, EventType eventType) {
        Optional<ClientConfiguration> optionalClientConfiguration = clientConfigurationRepository
                .findByClientAndEventType(client, eventType);
        return optionalClientConfiguration.orElse(null);
    }

    private GlobalCounter findByClientAndEventType(String client, EventType eventType) {
        Optional<GlobalCounter> optionalGlobalCounter = globalCounterRepository.findByClientAndEventType(client, eventType);
        return optionalGlobalCounter.orElse(null);
    }

    private void saveGlobalCounter(GlobalCounter globalCounter) {
        globalCounterRepository.save(globalCounter);
    }
}
