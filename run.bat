ECHO OFF
GOTO CASE_%1
:CASE_
    ECHO 0 for run local
    ECHO 1 for run heroku
    ECHO 2 for run local migrate
    ECHO 3 for migrate and run local
    GOTO END_SWITCH
:CASE_0
    java -jar target/meterservice-0.7.1-SNAPSHOT.jar server meterservice.yml
    GOTO END_SWITCH
:CASE_1
    heroku open --app meterservice-meter-service --account work
    GOTO END_SWITCH
:CASE_2
    java -jar target/meterservice-0.7.1-SNAPSHOT.jar db migrate meterservice.yml
    GOTO END_SWITCH
:CASE_3
    java -jar target/meterservice-0.7.1-SNAPSHOT.jar db migrate meterservice.yml
    java -jar target/meterservice-0.7.1-SNAPSHOT.jar server meterservice.yml    
:END_SWITCH




