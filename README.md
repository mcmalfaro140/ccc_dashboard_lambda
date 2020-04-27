# Commonwealth Casualty Company AWS Resources Dashboard Backend
## California State University, Los Angeles.

### By Misael Corvera, Jateni Dida, Alexander Horejsi, Yi Wang, and Zac You.
### Faculty Advisor : Dr. Chengyu Sun.

This project constains two different folders.
1) LogNotifications
2) LogNotificationTester.

### Deploy Instructions for LogNotifications:

The project requires the following enviroment variable:

1. DATABASE_URL
2. DATABASE_USERNAME
3. DATABASE_PASSWORD
5. EXCEPTION_SNS_TOPIC_ARN

Set the following value to the handler when deploying the lambda function to AWS:
handler = "com.ccc.logs.notifications.LambdaFunctionHandler::handleRequest"

Once the lambda function is running, add subscription triggers for every log group name that you want to send log to the lambda function.
Also set the filter pattern = '{ $.level = "ERROR" || $.level = "WARN" }'

### Running Instructions for LogNotificationTester:

The project requires the following enviroment variable:

1. AWS_ACCESS_KEY_ID
2. AWS_SECRET_ACCESS_KEY
3. AWS_REGION

Open project with Eclipse and run it. The project should open port 5000.
