package com.phonepe.alertmonitor.interfaces;

import com.phonepe.alertmonitor.exceptionRequests.ExceptionRaise;

public interface WindowOperation {
    void updateGlobalCounterSlidingWindow(ExceptionRaise exceptionRaise);
}
