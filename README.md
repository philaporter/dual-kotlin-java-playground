### Kotlin playground

Motivation: I finally realized that Kotlin is better than Java at basically everything, so I guess it's time to learn 
how to Kotlin.

This application attempts (succeeds?) at leveraging suspendable functions and coroutines in tandem with a main Java 
process that asynchronously processes CompletableFuture objects returned by the coroutines.

### Requirements
```
gradle
Java 8 (I'm using AdoptOpenJDK and Hotspot)
```

### Getting started
```shell script
gradle clean build
java -jar build/libs/kotlin-playground-1.0.0.jar
```
