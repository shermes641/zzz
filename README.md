
# **Powerhive Queen meter service 2014-07-18**

The queen meter service beta is currently deployed on two servers. 
1. The RESTful Java based application is deployed on the powerhive HEROKU account. 
2. The <a href="http://powerhive.webfactional.com/qms/docs/">NODEJS</a></b> app based application is deployed on the powerhive WebFaction account.

This document will talk about the JAVA application.

NOTE: The <a href="http://www.oracle.com/technetwork/java/javase/documentation/index-jsp-135444.html">JAVADOC</a> for this app can be viewed at <a href="http://powerhive.webfactional.com/qms/docs/javadocs">DOCS</a>

--------------

##**JAVA APP**

This app is the main touch point for the admin or user.


This application can respond to POSTed queen meter json data by placing the data into a redis cache for speed, then inserts the redis data into a postgres DB. 

<b>In practice, this application used too many resources for this feature and the <a href="http://powerhive.webfactional.com/qms/docs/">NODEJS</a> app is used in production.</b>

Also note this app can be run locally using <b>HTTPS</b>, but on HEROKU it is **HTTP** only.
 
----------
<a name="impl"></a>
##**IMPLEMENTATION**

| FEATURES | | |
|:-------------|--:|--:|
| HTTP only |HTTPS running locally||
| Supports redis and postgress | | |
| Stores POSTed meter data to DB |<b>NOTE: <a href="http://powerhive.webfactional.com/qms/docs/">NODEJS</a></b> app handles this in production | |
| Provides statistics and performance metrics | | |
| Trim DB | | |
| Authenticated access |**TODO:** finish implementation | |

This application requires the following to be installed locally:
<a name="vers"></a>

|App|Version||
|:-------------|--:|--:|
| JAVA|  1.7.0_55 | <a href="http://www.oracle.com/technetwork/java/javase/downloads/java-se-jre-7-download-432155.html">http://www.oracle.com/technetwork/java/javase/downloads/java-se-jre-7-download-432155.html/</a> |
| Apache Maven  | 3.1.0 | <a href="https://maven.apache.org/">https://maven.apache.org/</a> |
| REDIS |  2.8.9 | Uses RedisGreen addon <a href="https://dashboard.redisgreen.net/">https://dashboard.redisgreen.net/</a> |
| PostgreSQL | 9.1 | <a href="http://www.postgresql.org/">http://www.postgresql.org/</a> |

Queen meter data can be POSTed to the main app (<a href="http://meter-service.herokuapp.com/meterdata">http://meter-service.herokuapp.com/meterdata</a>) in 2 formats:

A json array with 20 circuits of data.
    
        [{"circuit_id":1,"queen_id":1,"day_wh_lifetime":20,"night_wh_lifetime":84,"day_wh_today":189.090909090909,"night_wh_today":273.090909090909,"total_wh_today":189.090909090909,"credit_balance":2,"data_date_time":"2014-05-22T22:30:49"}, 
		{"circuit_id":2,"queen_id":1,"day_wh_lifetime":30,"night_wh_lifetime":124,"day_wh_today":280,"night_wh_today":404,"total_wh_today":280,"credit_balance":3,"data_date_time":"2014-05-22T22:30:49"}, 
		........... ]
    
or json with a single element named "message" that is a json array with 20 circuits of data.

        {"message":[{"circuit_id":1,"queen_id":1,"day_wh_lifetime":20,"night_wh_lifetime":84,"day_wh_today":189.090909090909,"night_wh_today":273.090909090909,"total_wh_today":189.090909090909,"credit_balance":2,"data_date_time":"2014-05-22T22:30:49"}, 
		{"circuit_id":2,"queen_id":1,"day_wh_lifetime":30,"night_wh_lifetime":124,"day_wh_today":280,"night_wh_today":404,"total_wh_today":280,"credit_balance":3,"data_date_time":"2014-05-22T22:30:49"}, 
		........... }]}
		

### **Overview**

* Restful API to get and set Powerhive meter data from/into a PostgreSQL backend.

* The `migrations.xml` file illustrates the usage of `dropwizard-migrations` which can create your database prior to running
your application for the first time, and add version control to your schema.

* The `meterservice.yml` file holds the configuration parameters for the application

####**TODO**

1. Authenticate access to the API, whitelist, API key, etc.
2. Improve error handling.
3. Remove REDIS dependency
3. Add DB date trimming
4. Add DB aggrigation
3. Add configuration file
4. Remove meter data POST code (NODEJS app handles that)
5. Limit # of records returned


# **Endpoints**

* All endpoints can be access via HTTP or HTTPS (HTTPS only on localhost)

    http://localhost:8280
    https://localhost:8489
    http://meter-service.herokuapp.com/

* Get all the meter data in JSON format **TODO** limit # of records to 100 or so

        GET     /meterdata (com.powerhive.meterservice.resources.MeterDataResource)
    
* Add meter data to the DB. 

        POST    /meterdata (com.powerhive.meterservice.resources.MeterDataResource)

* Get a particular record by id in JSON format

        GET     /meterdata/{meterId} (com.powerhive.meterservice.resources.MdResource)

* Get a particular data record by id and display in Mustache template

        GET     /meterdata/{meterId}/view_mustache (com.powerhive.meterservice.resources.MdResource)

* Example basic authentication   any user name   pw:  secret

        GET     /protected (com.powerhive.meterservice.resources.ProtectedResource)
    
    
* Admin menu

        GET         /admin    (Returns a menu)

|Operational Menu  | |
| :------------ | -----:|
| Metrics | Application performance info          |
|  |
| Ping      | Responds with 'pong' if application is running|
|  |
| Threads     |  Application threading info    |
|  |
| Healthcheck | Basic status check |
|  |

# **Running The Application Locally**

#### **SSL setup**

The `meterservice.keystore` file contains the self signed certificate used by jetty for SSL 
**NOTE:** SSL only works on localhost
    
    keyStorePath: meterservice.keystore
    keyStorePassword: drone123
 
* The certificate was generated using the following command

    keytool -keystore meterservice.keystore -alias meterservice -genkey -keyalg RSA
        
Enter keystore password:
    
    drone123
What is your first and last name?

    [Unknown]:  Steve Hermes

What is the name of your organizational unit?

    [Unknown]:  Engineering

What is the name of your organization?

    [Unknown]:  Powerhive
	
What is the name of your City or Locality?

	  [Unknown]:  Berkeley

What is the name of your State or Province?

	  [Unknown]:  CA

What is the two-letter country code for this unit?

	  [Unknown]:  US

Is CN=Steve Hermes, OU=Engineering, O=Powerhive, L=Berkeley, ST=CA, C=US correct?
	  [no]:  yes

Enter key password for <meterservice> (RETURN if same as keystore password):
		
	<cr>
		
# **Building The Application Locally**

To test the example application run the following commands.

* To package the example run from the appliction root:

        mvn package

* To setup the database run:

        java -jar target/meterservice-0.7.1-SNAPSHOT.jar db migrate meterservice.yml

* To run the server run:

        java -jar target/meterservice-0.7.1-SNAPSHOT.jar server meterservice.yml

* To hit the Hello World endpoint:

    http://localhost:8280/hello-world

* To post meter data into the application:

        curl -H "Content-Type: application/json" -X POST -d @md.txt http://localhost:8280/meterdata

* Then, to see all the data, go to: **NOTE:** only do this on localhost, it returns alot of data

    http://localhost:8280/meterdata

----------

<a name="deploy"></a>
#**DEPLOYMENT**
The application is currently deployed on powerhive Heroku.

| ACCESS INFO | | | |
|:-------------|:--|--:|:--:|
| **APP:**       |  meter-service | | |
| **URL:** | <a href="http://meterservice-meter-service.herokuapp.com/">http://meterservice-meter-service.herokuapp.com</a> | | |
| **DASHBOARD:** | <a href="https://dashboard.heroku.com/apps/meter-service/resources">https://dashboard.heroku.com/apps/meter-service/resources</a> | | |
| **USER:** |       powerhive | | |
| **PW:** |         !drone321! | | |


Uploading changed source files to Heroku will trigger a maven rebuild.
**Note:** If deploying to a Linux server, after the initial deployment, only the shaded jar needs to be uploaded. 


Directory structure:

    DIR repo
        Dropwizard and Postgres libs
    DIR SQL
        Supporting SQL queries
    DIR src
        All java source
    meterservice-config-heroku.yml
        Config for Heroku deployment
    meterservice.keystore
        SSl self signed cert
    meterservice.yml
        Local Config
    pom.xml
        Maven pom
    Procfile
        Process file for Heroku deployment
    README.md
        This file
    restart.bat
        Restart Heroku instance
    log.bat
        View Heroku log    
    run.bat
        Run locally, DB creation/migration
    ud20.txt
        Fake json meter data
