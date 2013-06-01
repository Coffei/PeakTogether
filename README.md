.PeakTogether
============

### How to add hibernate spatial with postgis to JBoss AS 7

1. Create a module containing the postgresql JDBC driver and postgis:
- create a directory "modules/org/postgresql/main" in you JBoss AS 7 directory.
- copy the postgres.jar file (containing a postgresql JDBC4 driver) and the postgis-jdbc-1.5.2.jar file (should be in the peaktogether-ear/target/peaktogether/lib directory) to the newly created directory
- create a new file /modules/org/postgresql/main/module.xml containing the following xml code:

<?xml version="1.0" encoding="UTF-8"?>
<module xmlns="urn:jboss:module:1.0" name="org.postgresql">
    <resources>
        <resource-root path="postgres.jar"/>
        <resource-root path="postgis-jdbc-1.5.2.jar"/>
    </resources>
    <dependencies>
        <module name="javax.api"/>
        <module name="javax.transaction.api"/>
    </dependencies>
</module>

2. Add the required jar files to the hibernate module
- Copy hibernate-spatial-4.0-M1.jar and jts-1.12.jar (again, in the lib directory)to "/modules/org/hibernate/main"
- Add the following xml fragments to resources tag in the file /modules/org/hibernate/main/module.xml:

<resource-root path="hibernate-spatial-4.0-M1.jar"/>
<resource-root path="jts-1.12.jar"/>

- Add the following xml fragment to the dependencies tag in the same file

<module name="org.postgresql"/>

3. Define the postgres JDBC driver in the /standalone/configuration/standalone.xml 
- add the following xml fragments to the drivers tag int the standalone.xml:

<driver name="postgresql" module="org.postgresql">
    <xa-datasource-class>org.postgresql.xa.PGXADataSource</xa-datasource-class>
</driver>

NOTE: If done correctly, you should see a message similiar to
"Deploying non-JDBC-compliant driver class org.postgresql.Driver (version 9.2)"
when starting JBoss AS 7.


### To create local datasource
NOTE: See the How to add hibernate spatial with postgis to JBoss AS 7 section of this readme on information
regarding the deployment of postgres.jar

1. Run **"mvn package -Plocal-datasource"**

This will create postgresql datasource to localhost database *peaktogether* with username and password *peaktogether*.
There must be postgresql JDBC4 driver deployed as *postgres.jar*.

### To create local datasource for tests

1. Run **"mvn package -Plocal-test-datasource"**

This will create postgresql datasource to locahost database *peaktogetherTest* with username and password *peaktogether*.
There must be postgresql JDBC4 driver deployed as *postgres.jar*.

### To test with Arquillian use profile "test".

1. Ensure you have JBoss AS7 running (clean install is recommended)
2. Run **"mvn clean test -Ptest"** and tests should get executed
3. Results can be found in target/surefire-reports

### How to install PostGis2 on Fedora 18 ###
Take a look at a tutorial at http://www.diegognesi.it/?p=196, it should work up to tne the initdb command.
You can init db by:
```
/usr/pgsql-${version}/bin/initdb -D ${working_directory}
Example:
/usr/pgsql-9.2/bin/initdb -D /var/lib/pgsql/9.2/data
```
Create a user *peaktogether* with superuser priviliges and create his DB *peaktogether* 
(and *peaktogetherTest* if you want to test). Then login to **psql** with that user and execute:
```
CREATE EXTENSION postgis;
```
