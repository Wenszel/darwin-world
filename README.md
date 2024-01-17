<h1 align="center">Darwin World</h1>

Evolution simulation written in Java 17 with JavaFX. You can create your own world with animals and check how will they behave based on their genes!
This project was created with strong focus on Clean Code practices, SOLID principles, and design patterns.

## Installation

1. Make sure you have installed [JDK for Java 17](https://www.oracle.com/pl/java/technologies/downloads/#java17).
2. Follow these commands (instead of ./gradlew type gradlew on Windows):

       cd DarwinWorld
       ./gradlew build
       ./gradlew run
3. Enjoy the application! Check out the predefined configuration CSV files located in the main directory, which you can load from the app's main menu.

## Main features

* Implemented design patterns: [Observer](https://github.com/Wenszel/darwin-world/blob/main/DarwinWorld/src/main/java/agh/ics/oop/model/SimulationListener.java) for handling map changes, [Strategy](https://github.com/Wenszel/darwin-world/blob/dev/DarwinWorld/src/main/java/agh/ics/oop/model/Config/variants/MutationVariant.java) to handle multiple variants of the simulation, [Factory](https://github.com/Wenszel/darwin-world/tree/main/DarwinWorld/src/main/java/agh/ics/oop/model/factories) for creating different map types, [Builder](https://github.com/Wenszel/darwin-world/blob/main/DarwinWorld/src/main/java/agh/ics/oop/model/stats/AnimalStatisticsBuilder.java) for complex object constructions.
* Followed SOLID principles for scalable and maintainable codebase.
* Implemented efficient [algorithm](https://github.com/Wenszel/darwin-world) for counting all offspring of an animal.
* Ensured code quality with JUnit tests.
* Managed the project workflow with Git and GitHub Projects adopting the Kanban approach.
* Implemented multithreading for executing simulations in separate threads and for running excessive calculations in the background.

## Technologies
* Java 17
* JavaFX
* Gradle
* JUnit
* Git

## Screenshots
![menu](https://github.com/Wenszel/darwin-world/blob/main/images/menu.png)
![simulation](https://github.com/Wenszel/darwin-world/blob/main/images/simulation.png)

