# Expense Tracker - Spring Boot version
This sample project's goal is to study the design of Spring Boot projects, as well as Java and Architecture good practices.

## Application description
The application is a simple expenses controller. The user registers all their expenses (value,
date, expense type) as soon as they occur and the system can provide reports, charts, lists
and any possibly other types of aggregations that I may think of.

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
    - package: `service`
    - receives information or requests from the presentation layer, performs business logic
    and uses the persistence layer to store and retrieve data
- Persistence layer:
    - package: `repository`
    - retrieves and store data using any database configured through Spring Boot. It is not database
    dependent.
    
The code is developed around the Entities in the system. The package `model` contains these entities.
Each controller, dto, service and repository provides functions related to a single entity.

### Code and architecture decisions
- Follow most of the checkstyle suggestions;
- Use as most Spring Boot features and annotations as possible;
- Never expose models: always use DTOs for requests and responses;
- Do all possible validations by annotations on the requests DTO: that guarantees that only valid data
goes to the business logic layer;
- Do not add validations in the persistence layer: that means the business logic performed its operations
with possibly invalid data;
- Services always return models (not DTOs), so the methods can be better reused in the business logic layer. When
exposing the values through the API, the controllers are responsible for converting them to DTOs, since
this is a requirement for presentation;
- When adding entities values in the system, the services can receive request dtos and transform
them into Models, since this is business logic. 
- Never return null. If the returned object can be null, return Optional<> of the object.
In case of lists, return empty list.
- Follow the most REST API conventions as possible 


### REST API
- Endpoints contain NOUS, and not ACTIONS. The action is implied by the Http Request method;
- When possible, mantatory parameters as path variables `(/entities/id)` and optional ones
as url paramters `(/entities?name=me)`
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