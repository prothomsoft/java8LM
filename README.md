# Java 8 workshop driven by tests

## Import into IDE

### IntelliJ Idea

`File -> Import Project`

### Eclipse

Execute:

    ./gradlew eclipse

(on Windows double click `gradlew-eclipse.bat`)

Later: `File -> Import -> Existing Project Into Workspace`

## Smoke testing

### Gradle

Run `./gradlew clean check` to make sure you have JDK 8 and all dependencies in place.

(`gradlew.bat clean check` on Windows)

### IDE

Run `J01_HelloWorldTest.java` from your favourite IDE and make sure it compiles and passes.

## Authors

 - [Tomasz Nurkiewicz](https://twitter.com/tnurkiewicz/)
 - [Marcin Zajączkowski](https://twitter.com/SolidSoftBlog/)

## License

This project is released under version 2.0 of the [Apache License](http://www.apache.org/licenses/LICENSE-2.0).
