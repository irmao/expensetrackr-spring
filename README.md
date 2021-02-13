# Expense Tracker - Spring Boot version
This sample project's goal is to study the design of Spring Boot projects, as well as Java and Architecture good practices.

## Application description
The application is a simple expenses controller. The user registers all their expenses (value,
date, expense type) as soon as they occur and the system can provide reports, charts, lists
and any possibly other types of aggregations that I may think of.

## CI & Code Quality
The has Continuous Integration powered by Github Actions. After commits are made to the `main` branch, the following tools analyse the code:
- Checkstyle (https://github.com/checkstyle/checkstyle)
    - If any issue is found, it breaks the build and Github Actions acuses it
- JaCoCo (https://www.eclemma.org/jacoco/)
    - After passing through the previous analysis, maven run the tests and checks the code coverage. If the minimum requirement is not met, the build fails and Github Actions acuses it. The minimum coverage at the moment is 30%.
- SonarCloud (https://sonarcloud.io/)
    - After passing through the previous analysis, Sonar verifies the code and publishes the report here:
        - https://sonarcloud.io/dashboard?id=irmao_expensetrackr-spring

## Development Type
### TDD (Test-Driven-Development)

**Description**: Requirements turn into test uses cases, which will firstly fail
because there is no implementation of the feature yet. Then, the code is improved
so the test pass. Afterwards, if required, some code refactoring is made to clean-up
the code as it grows. 

**Motivation**: Guarantee that automated tests literally cover all code. Guarantee
that no extra work is made beyond the requisites.

## Architecture

The application is divided in two main parts: the backend and the frontend. The backend
is responsible for control all business logic and persist the data. The frontend is responsible
for getting the input data and showing the aggregated results.

Communication between the frontend and the backend is made by a REST API that the backend will expose.

**Since this is a Spring Boot, Java and backend architecture study, the frontend development is
not even in the roadmap yet. All following information is regarding the backend portion.**

### Code Structure

The coded is divided in 3 layers: presentation, business logic and persistence.

- Presentation layer
    - package: `.api.*` (`.controller`, `.dto`, ...)
    - part of the application that is exposed to collect input and provide results
    - communicates only with the Business Logic Layer
- Business logic layer:
    - package: `domain.*` (`.service`, `.exception`, ...)
    - receives information or requests from the presentation layer, performs business logic
    and uses the persistence layer to store and retrieve data
- Persistence layer:
    - package: `repository`
    - retrieves and store data using any database configured through Spring Boot. It is not database
    dependent.
    
The code is developed around the Entities in the system. The package `model` contains these entities.
Each controller, dto, service and repository provides functions related to a single entity.

### Code and architecture decisions
- Follow most of the checkstyle suggestions
    - Motivation: they are there for a good reason. But do not trust all of them: always look for their motivation.
- Use as most Spring Boot features and annotations as possible
    - Motivation: prevent from doing manual things when they are already automated.
- Never expose models: always use DTOs for requests and responses
    - Motivation: expose only necessary. If the model grows, we are able to decide if the new features will be exposed or not. If the model has lists, they are not returned unless necessary.
- Do all possible validations by annotations on the requests DTO
    - Motivation: that guarantees that only valid data goes to the business logic layer.
- Do not add validations in the persistence layer
    - Motivation: that means the business logic performed its operations with possibly invalid data
- Services always return models (not DTOs)
    - Motivation: the methods can be better reused in the business logic layer. Also, when exposing the values through the API, the controllers are responsible for converting them to DTOs because this is a requirement for presentation, so it must be done in the presentation layer.
- When adding entities values in the system, the services can receive request dtos and transform them into Models
    - Motivation: since this is business logic (for example receiving related entities ids and fetching the corresponding models; deciding if they can be null or not; etc).
- Never return null. If the returned object can be null, return Optional<> of the object. In case of lists, return empty list
    - Motivation: avoid unwanted NullPointerException due to forgetting to check; explicitly inform whether a method return a nullable value or not will facilitate the understanding of the code; allows functional style programming.
- Follow the most REST API conventions as possible
    - Motivation: Force to learn REST conventions and guidelines. It is easier to a system to use the public API.
- Apply as most as functional style programming as possible, following good practices (https://dzone.com/articles/functional-programming-patterns-with-java-8)
    - Motivation: Force to learn functional style. It is easier to understand the code if it is well written
- In `application.properties`, start all custom property with `app.`
    - Motivation: so one can know for sure it is not used internally by some library.
- When a variable does not change and should not change, declare it as final, even if it is a local variable.
    - Motivation: prevent logic errors; allows the compiler to optimize the class file
- Authentication endpoint is: `POST /authentication/credentials`
    - Motivation: `POST`: user is creating their token. `/authentication/credentials`: REST conventions state that we should use nouns along with the HTTP methods, and `/login` is a verb

### REST API
- Endpoints contain NOUS, and not ACTIONS. The action is implied by the Http Request method;
- When possible, mandatory parameters as path variables `(/entities/id)` and optional ones
as url parameters `(/entities?name=me)`
- Endpoints in plural
- Common endpoint examples:
    - `GET /expenses`
    - `GET /expenses/id`
    - `POST /expenses`
    - `PUT /expenses/id`
    - `DELETE /expenses/id`

- Success codes and body responses:
    - GET: 200. Body with found content. If no content is found, returns 200 with empty list
    - GET one: 200. Body with found content
    - POST: 201. Body with GET one URI for that entity
    - PUT: 204
    - DELETE: 204 (no content)
    
- Error responses:
    - Validation errors: 400 (bad request)
    - Not found: 404 (GET one, PUT, DELETE)
    - Trying to insert duplicated entity: 409 (conflict)
    - Trying to remove entity in use: 409 (conflict)
