PeakTogether
============

### To create local datasource

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
