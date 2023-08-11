# Compass Scholarship Program API

In this technical challenge, I aim to develop a RESTful API using the Spring Boot framework in Java 17 and Maven 3.0.9. The API should handle the basic operations of the four HTTP verbs: GET, POST, PUT, and DELETE. The application's domain revolves around the Compass Scholarship Program classes, allowing registration of organizers (Coordinators, instructors, and Scrum Masters), students, classes, and squads.

in this project, I chose to use MySQL and create the entities, Class, Squad, Student, and internal, Lombok was used for a cleaner code, and I structured the API in three controllers, all in their "V1" version, for data validation it was validator and restrictions were used in order to make the data more concise, exception handlers were used for error handling.

The bank's architecture was designed to support multiple internal students in different projects and squads. It is possible to create an entire class with members or add them one by one or in lists. Squads are defined based on the experience of the students, which is measured from one to three, zero for novices, two for intermediates, and three for experts, thus being balanced so that advanced members can help the novices.



## 🔑 Mandatory requirements

- document your progress and choices, use the README for this, and also to teach how to run your project.
- Import the postman's collection, pay attention to the body of the requests.
- Project Metadata Group next pattern your name and last name (name. last name).
- Minimum 3 branches.
- Minimum 5 commits.

## Table of Contents

- [Getting Started](#getting-started)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [Endpoints](#endpoints)
- [Testing](#testing)
- [Technologies](#technologies)
## Getting Started

### Prerequisites

- Java 17
- Maven 3.0.9
- MySQL database
- Git

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/white-Reis/compass-scholarship-api.git
   cd compass-scholarship-api
______________________________________________________________________________________________________________________________________________________________________________________________________________________________
## 🚀Technologies

- Java 17
- Maven 4.0.0
- MySQL database
- Git
- SPRING BOOT 3.0.9
