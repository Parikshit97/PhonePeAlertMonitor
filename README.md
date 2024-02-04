<h1> Alert Monitor </h1>

<h3>Setup</h3>
<hr>

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

Docker Container Logs Screesnhot:


Another way, to check docker container logs, through terminal, <code>docker logs <i> [containerid] </i> </code> (<i>Replace the container id of the docker container name 'alertmonitor-backend' </i>) 
<br> <br>
Refer to the screenshot below:
















