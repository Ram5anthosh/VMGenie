==================================================================
Instructions to compile, test and run SE252 Project 0 Startup Code
==================================================================

1) Make sure to install Maven 3.1 and Java 1.7, and update paths to point to mvn and java. Set Maven to use the IISc proxy, if required. Set the JAVA_HOME and M2_HOME environment variables to point to your Java and Maven base folders. Make sure java, javac and mvn are in your execution path.
---------------------------
2) Test REST web service on "host1" (replace host1 with the name or IP address of the machine you're running on)

cd se252/project-0
mvn -e clean test -Dtest=se252.jan15.calvinandhobbes.project0.EchoServiceTest#testEchoService -DargLine="-DserviceUrl=http://host1:8081/project0/"

This will compile the code and run a self-test for sanity check.
---------------------------
3) Compile and run Echo REST Web Service on "host1", passing the hostname of your machine as param
cd se252/project-0
mvn compile
mvn exec:java -Dexec.mainClass="se252.jan15.calvinandhobbes.project0.EchoServiceLauncher" -Dexec.args="'host1' '8081'"

You should be able to visit the web service URL http://host1:8081/project0/echo?msg=foo from a browser (or using wget or curl commands) acting as a REST client. The response should be a JSON message.
---------------------------
4) Once you have the REST web service running from #3, compile and run the Web Form Server on "host2", passing base URL location of REST service and public hostname of "host2" as params

cd se252/project-0
mvn compile
mvn exec:java -Dexec.mainClass="se252.jan15.calvinandhobbes.project0.EchoWebformLauncher" -Dexec.args="'http://host1:8081/project0/' 'host2' '8080'"

You should be able to visit the webform on host2 using a browser. Submitting the form will trigger the REST client present in the form implementation and call the REST web service on host1.
http://host2:8080/project0/web
---------------------------

(c) Yogesh Simmhan, 2015
This work is licensed under a Attribution 4.0 International (CC BY 4.0).
http://creativecommons.org/licenses/by/4.0/
See IISc SERC 'SE252:Intro to Cloud Computing' Course Webpage: http://www.serc.iisc.ernet.in/~simmhan/SE252/