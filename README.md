# Compass Scholarship Program API

In this technical challenge, I aim to develop a RESTful API using the Spring Boot framework in Java 17 and Maven 3.0.9. The API should handle the basic operations of the four HTTP verbs: GET, POST, PUT, and DELETE. The application's domain revolves around the Compass Scholarship Program classes, allowing registration of organizers (Coordinators, instructors, and Scrum Masters), students, classes, and squads.

in this project, I chose to use MySQL and create the entities, Class, Squad, Student, and internal, Lombok was used for a cleaner code, and I structured the API in four controllers, all in their "V1" version, for data validation it was validator and restrictions were used in order to make the data more concise, exception handlers were used for error handling.

The bank's architecture was designed to support multiple internal students in different projects and squads. It is possible to create an entire class with members or add them one by one or in lists. Squads are defined based on the experience of the students, which is measured from one to three, zero for novices, two for intermediates, and three for experts, thus being balanced so that advanced members can help the novices.

Classes can only be initiated with fifteen students, one Scrum Master, one Coordinator, and three Instructors. There were multiple ways to create a class, either by creating a fully formed class directly, or by creating a class and students without a direct link and then adding their IDs in the form of a list. It is possible to remove students and internals, which removes them from their respective classes. For security, the system prevents the creation of duplicate email addresses.

Upon initiating a class, the students are divided equally into teams or groups, with a leveling mechanism that balances them based on their experience level, measured from one to three: no experience, novice, and experienced.

Each class records its start and completion dates when initiated and concluded respectively. Once a class is initiated, it cannot be modified, but interns can be added through a dedicated route.

## 🔑 Mandatory requirements

- document your progress and choices, use the README for this, and also to teach how to run your project.
- Import the postman's collection, pay attention to the body of the requests.
- Project Metadata Group next pattern your name and last name (name. last name).
- Minimum 3 branches.
- Minimum 5 commits.

## :closed_book: Table of Contents

- [Getting Started](#getting-started)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [Endpoints](#endpoints)
- [Testing](#testing)
- [Technologies](#technologies)
- [Considerations](#Considerations)
______________________________________________________________________________________________________________________________________________________________________________________________________________________________
# :vulcan_salute:Getting Started

## :memo:Prerequisites

- Java 17
- Maven 3.0.9
- MySQL database
- Postman

______________________________________________________________________________________________________________________________________________________________________________________________________________________________
## :hammer_and_wrench:Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/white-Reis/compass-scholarship-api.git
   cd compass-scholarship-api
   mvn clean install
   mvn spring-boot:run

If you don't have Maven installed, you can download it from here - https://maven.apache.org/download.cgi

______________________________________________________________________________________________________________________________________________________________________________________________________________________________
## :round_pushpin:EndPoints

You can use the Postman collection to interact with the Compass Scholarship Program API. Download the Postman collection by clicking the link below:

[Download Postman Collection](https://www.postman.com/speeding-equinox-52035/workspace/compass2023/collection/27688899-bcce56e9-ade6-4b72-b8fe-897066dc0dbd?action=share&creator=27688899)

To use the collection:
1. Open Postman.
2. Import the downloaded collection.
3. Follow the API requests and responses to interact with the endpoints.

______________________________________________________________________________________________________________________________________________________________________________________________________________________________
## :zap:Testing

Run the automated tests by executing the following command:
   ```bash
   mvn test

````      
______________________________________________________________________________________________________________________________________________________________________________________________________________________________
## 🚀Technologies

- Java 17
- Maven 4.0.0
- MySQL database
- Git
- Spring Boot 3.0.9
- Validator
- Lombok
- JPA (Java Persistence API)
- ModelMapper

### :mechanical_arm:Considerations


feel free to use this project and modify!

