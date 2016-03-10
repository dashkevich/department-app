# department-app
The project consists of two applications. The first application is a RESTful web service that provides access to the data (employees and departments lists). A second web application - client module that provides an interface to display data in tabular form and allows you to create, edit, and delete the data.

## Project building
- Build project with maven ``` mvn install ```
- Deploy project to tomcat
  - Copy ```/rest/target/rest.war``` and ```/web-app/target/app.war``` files to your ```tomcat/webapps``` folder
  - Start tomcat and open [http://localhost:8080/rest](http://localhost:8080/rest) for the rest module and [http://localhost:8080/app](http://localhost:8080/app) for the web-app module

In demo h2 database is used. If you want use MySQL database:
- Open the file:  ```\dao\src\main\resources\db.properties``` and set the following parameters: 
``` 
jdbc.dbname=name
jdbc.username=root
jdbc.password=password
```
