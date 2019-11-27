# darkskyapp
Project Name: Darksky weather report application 

This is the Web application to display the weather forecast from Darksky api. 

Pre-requistes/technologies:
Java version: 1.8 
Maven
SPRING boot
Angularjs
UI bootstrap

=============================== Setup and compiling the program===============
1.Extract the project archive.
 2.Navigate to project folder
 3.Open command prompt terminal and navigate to the project root directory mentioned in the step2. 
4.Enter maven command 'mvn compile' which will compile the source code of the application. 
5. once the compile is done , enter maven command "mvn clean package" which will create the executable jar file of this project. 
Once build is successful ,you can see the generated jar file under '/rootdir/target/' folder.

================================ Running the program===================
1.Use the spring boot run command to run the application which will launch the Web application. 

or run the prgram by runnning Spring boot starter class

In the same command terminal enter the below command to run the program. $ java -cp target/darkskyweatherapplication-0.0.1-SNAPSHOT.jar \com.ram.darksky.starter.DarkskyApplicationStarter
If the above command doesn't identify the main class , use the below command. $ java -cp target/darkskyweatherapplication-0.0.1-SNAPSHOT.jar com.ram.darksky.starter.DarkskyApplicationStarter


2.open Web browser and enter url 'http://localhost:8080/home
3.Enter latitude and longitude values in the given input form and submit to get the forecast.

To-do:
Because of an issue with authentication of the Google maps cloud api with my API key, couldn't fetch the coordinates of the locations hence only direct latitude and longitudes are provided in the input form.
Data persistence layer needs to be implemented to stote the forecast in the database. 
Logging is pending and some validations at UI level are pending. 
