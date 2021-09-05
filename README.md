<h4 align="center">
  <p>Auth API</p>
  
  <p>This application was developed in order to train authorization/authentication concepts.</p>

  <p>This project is based in CRUD with Architeture Authenticated with unitary and integration tests.</p>
  
  <p>At time, this app accepts both basic and JWT auth.</p>
  
</h4>

<p align="center">
  <a href="#rocket-technologies">Technologies</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#information_source-how-to-use">How To Use</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#thumbsup-how-to-contribute">How To Contribute</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#memo-license">License</a>
</p>

<p align="center">
<img alt="Collage" src="https://ik.imagekit.io/27ewoxssse/crud-auth_WAXN6kJoP.png"> 
</p>

## :rocket: Technologies

This project was developed with the following technologies:

- API:

  - [Kotlin](https://kotlinlang.org/)
  - [Quarkus](https://quarkus.io/)
  - [Mongo](https://www.mongodb.com/pt-br)
  - [Morphia](https://github.com/MorphiaOrg/morphia)
  - [JAX-RS](https://en.wikipedia.org/wiki/Jakarta_RESTful_Web_Services)
  - [Hibernate Validator](http://hibernate.org/validator/)
  
- Utils:

  - [Swagger](https://quarkus.io/guides/openapi-swaggerui)
  - [Kotlin-Logging](https://github.com/MicroUtils/kotlin-logging)
  
- Tests:
 
  - [JUnit5](https://junit.org/junit5/docs/current/user-guide/)
  - [RestAssured](https://rest-assured.io/)
  - [TestContainers](https://www.testcontainers.org/)

## :information_source: How to use
To clone and run this application, you'll need [Git](https://git-scm.com), [Maven](https://maven.apache.org/) and [Mongo](https://www.mongodb.com/pt-br) installed on your computer. From your command line:

```bash
# Clone this repository
$ git clone https://github.com/Duduxs/auth-api

# Go into the repository
$ cd auth-api
```

To run the API server:

```bash
# put at bash mvn: compile quarkus:dev
```
Now access on your browser: http://localhost:8080 [Tests -> 8084]

<p align="center">
  docs -> http://localhost:8080/q/doc
<img alt="Doc" src="https://ik.imagekit.io/27ewoxssse/swagger_I4B3s_lF-.png"> 
</p>

## :thumbsup: How To Contribute

-  Make a fork;
-  Create a branch with your feature: `git checkout -b my-feature`;
-  Commit changes: `git commit -m 'feat: My new feature'`;
-  Make a push to your branch: `git push origin my-feature`.

## :memo: License
This project is under the MIT license. See the [LICENSE](https://github.com/Duduxs/auth-api/blob/main/LICENSE) for more information.

---

<h4 align="center">
    Made by Eduardo José 😆 <a href="https://www.linkedin.com/in/eduarddojose/" target="_blank">Contact me!</a>
</h4>
