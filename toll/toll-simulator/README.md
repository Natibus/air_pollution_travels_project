# toll-simulator
This is a Moleculer Microservice for IoSL Ws18/19 which task it is to emit events with geo location data about vehicles in Berlin.
The geo location of the vehicles is simulated using [SUMO](http://sumo.dlr.de/index.html).

## Message Broker
Current implementation uses NATS.

The namespace is `MOL-iosl2018`.

The Node Id is `toll-simulator`.

## Configuration of environment variables
* The NATS server has to be configured using environment variables (`NATS_URL`, `NATS_USER`, `NATS_PASSWORD`).


## Events
### `location.update`
After start, the service emits `location.update` events which contain a json payload with the format:
``` json
{
    "messageId":    123,
    "carId":        123,
    "timestamp":    "yyyy-mm-dd hh:MM:ss"
    "accuracy":     50,
    "lat":          123.456,
    "lon":          123.456
}
```
### `location.reset`
Once the simulation finished, a `location.reset` event will be emitted. Subscribers should listen for it to reset any saved status for vehicles. The simulation will restart from beginning after a short timeout (2s).

## Methods
### `status`
The Microservice exposes the endpoint `/api/status`. A `GET` request to that endpoint will return a string. For health checks.

### `start`
The Microservice exposes the endpoint `/api/start`. A `GET` request to that endpoint will start the simulation. The simulation can be configured using the parameters `vehicles` and `interval`.

| Property | Type | Description | Default |
| :--- | :--- | :--- | :--- |
| `vehicles` _(optional)_ | int | Number of tracked vehicles in the simulation | all vehicles |
| `interval` _(optional)_ | int | Update interval size in seconds | `1` |

### `stop`
The Microservice exposes the endpoint `/api/stop`. A `GET` request to that endpoint will stop the simulation.

### `reset`
The Microservice exposes the endpoint `/api/reset`. A `GET` request to that endpoint will restart the simulation with the last configuration.

## TODO:
* Reduce accuracy of geo data
* Use bigger simulation

## Build Setup

``` bash
### Install dependencies
npm install

### Start developing with REPL
npm run dev

### Start production
npm start

### Run ESLint
npm run lint
```

### Run in Docker

```bash
$ docker build -t YOUR_CONTAINER .
```


```bash
$ docker run -p YOUR_PORT:3000 YOUR_CONTAINER
```

```bash
$ docker-compose up -d --build
```
