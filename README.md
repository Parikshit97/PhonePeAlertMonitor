<h1> Alert Monitor </h1>

<h3>Tech Stack</h3>
<hr>
<ol>
  <li>JAVA - Spring Framework</li>
  <li>MongoDB</li>
  <li>Docker</li>
  <li>Kubernetes</li>
</ol>


<h3>Setup</h3>
<hr>

<i>Note: Please use 'main' branch for checking purpose.</i>

<p>
<ol>
  <li>Clone the repository using <code>git clone git@github.com:Parikshit97/alertmonitor.git</code></li>
  <li>Within alertmonitor package, run <code>docker compose up</code></li>
</ol>
</p>

<h3>CURLs</h3>
<hr>
<h4>Client Configuration CURL</h4> <br>

```  
curl --location '127.0.0.1:8080' \
--header 'Content-Type: application/json' \
--data '{
  "alertConfigList": [
    {
      "client": "X",
      "eventType": "PAYMENT_EXCEPTION",
      "alertConfig": {
        "type": "TUMBLING_WINDOW",
        "count": 10,
        "windowSizeInSecs": 10
      },
      "dispatchStrategyList": [
        {
          "type": "CONSOLE",
          "message": "issue in payment"
        },
        {
          "type": "EMAIL",
          "subject": "payment exception threshold breached"
        }
      ]
    },
    {
      "client": "X",
      "eventType": "USERSERVICE_EXCEPTION",
      "alertConfig": {
        "type": "SLIDING_WINDOW",
        "count": 10,
        "windowSizeInSecs": 10
      },
      "dispatchStrategyList": [
        {
          "type": "CONSOLE",
          "message": "issue in user service"
        }
      ]
    }
  ]
}
'
  
```

<h4>Raise Exception</h4> <br>
Note: Possible - eventType: USERSERVICE_EXCEPTION, PAYMENT_EXCEPTION

<b><i> Also, in order for logs to be generated, this request will need to be sent
more than count times within windowSizeInSecs. </i></b>

```

curl --location '127.0.0.1:8080/raise' \
--header 'Content-Type: application/json' \
--data '{
    "eventType": "USERSERVICE_EXCEPTION",
    "client": "X"
}'

```

<h4>Published Docker Image</h4><br>
URL : https://hub.docker.com/repository/docker/parikshit3097/phonepe-alertmonitor/general


<h4>Output Check</h4><br>
To check the output, I used docker desktop to check running docker contianer logs. 
After the following the above (package setup and exception raise) steps, check the running docker container logs in docker desktop.
<br><br>
Docker Container Logs Screesnhot:
<br><br>

![image](https://github.com/Parikshit97/alertmonitor/assets/30137444/2ca8edcd-a0ed-40aa-a242-5ef337db6aa0)


<br><br>
Another way, to check docker container logs, through terminal, <code>docker logs <i> [containerid] </i> </code> (<i>Replace the container id of the docker container name 'alertmonitor-backend' </i>) 
<br> <br>
Refer to the screenshots below:

<br><br>

![image](https://github.com/Parikshit97/alertmonitor/assets/30137444/c970b3c6-45f2-491d-bf8d-1a1e5a3e901f)

<br><br>

![image](https://github.com/Parikshit97/alertmonitor/assets/30137444/cd4cbc49-262f-460c-8c46-61362d72d4cd)

<br><br>

<h4>MongoDB Collections</h4><br>
The primary logic of <i>TUMBLING_WINDOW</i> and <i>SLIDING_WINDOW</i> is implemented using colections or tables in mongodb. MongoDB collections are accessible using MongoExpress 
accessible over URL : http://0.0.0.0:8081/ after the docker container is UP and RUNNING.
<br><br>

Screesnhots of Database: (We are using alertmonitor)

<br><br>

<img width="1512" alt="image" src="https://github.com/Parikshit97/alertmonitor/assets/30137444/bdd8044c-a67c-4ef8-9119-38351bebabc8">

<br><br>

Screesnhots of Collections: (clientConfigurations and globalCounters)

<br><br>

<img width="1512" alt="image" src="https://github.com/Parikshit97/alertmonitor/assets/30137444/a35efc88-f421-43de-900c-5620b26a21a2">

<br><br>

Screenshot of clientConfigurations Collection:

<br><br>

<img width="1512" alt="image" src="https://github.com/Parikshit97/alertmonitor/assets/30137444/2414bcb4-c852-4be2-a8d8-1e0e1f9d1875">

<br><br>

Screenshot of globalCounters Collection:

<br><br>

<img width="1512" alt="image" src="https://github.com/Parikshit97/alertmonitor/assets/30137444/55f24ce2-0aac-4a56-a6cb-7f821c7fe5a0">

<br><br>



































