# IOSL docker compose project for toll simulator

## Setup
Load all submodules by running
``` bash
$ git submodule update --init --recursive
```

## Running the project (simulator only)
``` bash
$ docker-compose up -d --build
```
The `-d` flag starts docker-compose in detached mode. Omit it to see the logs of all containers.

## Running the project with microservices (e.g. Hemera)
``` bash
$ docker-compose -f docker-compose.yml -f docker-compose.hemera.yml up -d --build
```


### Reading logs of one of the containers
``` bash
$ docker-compose logs CONTAINER_NAME
```
Names are e.g. `toll-simulator`, `nats-subscriber`

### Stopping the containers
``` bash
$ docker-compose stop
```


## Public Endpoints
- [Poll Dashboard](http://localhost) (Includes Weave Scope)
- [Weave Scope](http://127.0.0.1:4040/#!/state/{%22topologyId%22:%22containers%22})
- [Toll Simulator](http://localhost:8760)