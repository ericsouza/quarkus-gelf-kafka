## Example project

This project is a trial to use Quarkus with GELF logging with kafka transport.

### The Problem

Basic what is happening is that GELF logging is not working in the native mode and prints `LogManager error of type WRITE_FAILURE: Could not send GELF message` to console.

### How to run?

First you need to start the kafka and kafka console manager (provided by redpanda docker-compose.yaml)

```bash
docker-compose up
```

After that you can access the redpanda console at http://localhost:8089

#### JVM Mode

just run `quarkus dev` and call the hello endpoint: `curl localhost:9597/hello`. Go back to the kafka console and you will see a new topic create with name `quarkus-gelf-kafka` with all the application logs

#### Native mode

build the native binary:

```bash
quarkus build --native
```

Now run:
```bash
./target/quarkus-gelf-kafka-1.0.0-SNAPSHOT-runner
``` 

You can see that is printed a message: `LogManager error of type WRITE_FAILURE: Could not send GELF message` and if you call the hello endpoint again nothing is sent to kafka.
