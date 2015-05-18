# bxms-workshops
A repository to share workshops for Red Hat JBoss [BPM Suite](https://www.redhat.com/en/technologies/jboss-middleware/bpm) and [BRMS](http://www.redhat.com/en/technologies/jboss-middleware/business-rules)


## Spring MVC BRMS App

This is a simple app showing how to use BRMS in a Spring MVC application, derived from [Maciej Swiderski's Spring jBPM App](http://mswiderski.blogspot.com/2014/11/cross-framework-services-in-jbpm-62.html) 

### Getting the app to work locally

#### Prerequisites
* JDK 1.7
* JBoss Developer Studio or Eclipse with maven and JBoss Tools installes
* EAP 6.4
* EAP 6.4 configured as a server in JBDS/Eclipse to run with standalone-full.xml profile
* git

#### Steps to stand up
1. Clone this repo: `git clone https://github.com/sherl0cks/bxms-workshops.git` 
2. Import bxms-workshops/brms-spring-mvc-workshop into JBDS/Eclipse as a maven project
3. Run `mvn clean install` in JBDS\Eclipse on the root project (brms-spring-mvc)
4. Import bxms-workshops/kie-server.war into JBDS/Eclipse as a war file.
4. Clone the business rules project: `git clone https://github.com/sherl0cks/workshop-business-rules`
5. Import workshop-business-rules\brms-spring-mvc into JBDS/Eclipse as a maven project 
6. Run `mvn clean install` in JBDS\Eclipse on the business-rules project
7. In JBDS/Eclipse, Right click->Run-As->run on server for spring-mvc-war. Choose your EAP 6.4 with standalone-full.xml
8. In JBDS/Eclipse, Right click->Run-As->run on server for kie-server. Choose your EAP 6.4 with standalone-full.xml
9. go to [http://localhost:8080/spring-mvc-war](http://localhost:8080/spring-mvc-war). This should show the home page. 10. Try running rules local and remote and make sure it all works.



