# micronaut-kafka-container-test-example
This project contains an example on how to have a producer/listener using:

* kafka
* Avro messaging format
* Protocol Buffer messaging format
* Kafka test container with a mock schema registry
* Micronaut microservices
  * JDK 17
  * Maven
  * JUnit
  * AssertJ/Awaitability 

# How to run
* Software required
  * JDK 17 (tested with GraalVM 17.0.4) 
  * Protocol Buffer executable (3.21.9)
  * Apache maven (3.8.6)
  * Docker running (tested with Docker Desktop 4.13.1)
  * Micronaut 

# Staying relevant
I'll do my best with keeping this tutorial up-to-date.  Please write me or put in a comment if you would like me to upgrade the software list above and make appropriate changes to the code.

# tutorial (OSX)

## Install sdkman:

```
curl -s "https://get.sdkman.io" | bash
```
This installs sdkman.  

## Install java, micronaut, maven

Then install the following:
sdk install java 
sdk install micronaut 
sdk install maven 

## Install homebrew
```
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```

## Install docker
**NOTE**: This is supposed to be a cask since it's a desktop app.  Take note of the command below:
```
brew install --cask docker 
```

Then make sure to start docker desktop!


## Install protocol buffers
```
brew install protobuf
```

## Run it!
```
git clone git@github.com:krickert/micronaut-kafka-container-test-example.git
cd micronaut-kafka-container-test-example
mvn clean compile install test
```

## Troubleshooting
I've found a few things can dirty the environment:

### Delete ~/.micronaut if docker is hanging
The testing resources provided by micronaut are stored in ~/.micronaut.  However, when the JDK default changes it tends to break the service.  If tests hang, try this first.
