'use strict';

const fs = require('fs');
const xml2js = require('xml2js');
const parser = new xml2js.Parser();
const dateFormat = require('dateformat');

const file = __dirname + '/simulation.xml';

let interval;
let intervalSize = 1;
let vehicleCount;
let simulationCallback;
let logger;

module.exports = {
	startSimulation: startSimulation,
	stopSimulation: stopSimulation,
	resetSimulation: resetSimulation,
	setLogger: setLogger,
};

function setLogger(molecularLogger) {
	logger = molecularLogger;
}

function startSimulation(simulationInterval, simulationVehicleCount, callback) {
	stopInterval();

	intervalSize = simulationInterval;
	vehicleCount = simulationVehicleCount;
	simulationCallback = callback;

	return new Promise((resolve) => {
		readFile(file, handleResult(callback, resolve));
	});
}

function stopSimulation() {
	if (!simulationCallback) {
		logger.error('Can not stop Simulation if no simulation started yet');
		return;
	}

	stopInterval();
}

function resetSimulation() {
	if (!simulationCallback) {
		logger.error('Can not reset Simulation if no simulation started yet');
		return;
	}

	stopInterval();
	startSimulation(intervalSize, vehicleCount, simulationCallback);
}

function readFile(fileName, callback) {
	fs.readFile(fileName, function(err, data) {
		parser.parseString(data, function(err, result) {
			if (err) {
				console.error(err);
				return;
			}
			callback(result);
		});
	});

}

function stopInterval() {
	if (interval) {
		clearInterval(interval);
		interval = undefined;
	}
}

function handleResult(callback, onFinish) {
	return function(result) {
		const timesteps = result['fcd-export'].timestep;
		const length = timesteps.length;
		let trackedInLastInterval = [];

		let i = 0;

		if (!length) {
			logger.error('Simulation is empty!');
			return;
		}

		logger.info(`The simulation has ${length} steps. Interval is ${intervalSize}. Vehicle count is ${vehicleCount || 'not limited'}`);

		interval = setInterval(() => {
			const vehicles = timesteps[i].vehicle || [];

			if (!vehicles.length) {
				logger.info(`At step ${i} no vehicles are on the road`);
			} else {
				logger.info(`At step ${i} ${vehicles.length} vehicles are on the road`);
				const date = dateFormat(new Date(), 'yyyy-mm-dd hh:MM:ss');
				let trackedVehicles;

				if (vehicleCount) {
					// filter out the vehicles that were already part of the last interval
					trackedVehicles = vehicles.filter(vehicle => {
						return trackedInLastInterval.indexOf(vehicle['$'].id) !== -1;
					});

					// if we do not have enough vehicles yet but the interval still has some add them to the simulation
					if (trackedVehicles.length < vehicleCount && vehicles.length >= trackedVehicles.length) {
						// filter only not yet used vehicles
						vehicles.filter(vehicle => {
							return trackedInLastInterval.indexOf(vehicle['$'].id) === -1;
						}).forEach(vehicle => {
							// add until vehicle count is reached
							if (trackedVehicles.length < vehicleCount) {
								trackedVehicles.push(vehicle);
							}
						});
					}

					// save current vehicles for next interval
					trackedInLastInterval = trackedVehicles.map(vehicle => vehicle['$'].id);
				} else {
					trackedVehicles = vehicles;
				}

				trackedVehicles.forEach(vehicle => {
					const parsedVehicle = {
						carId: vehicle['$'].id,
						long: vehicle['$'].x,
						lat: vehicle['$'].y,
						accuracy: 100,
						timestamp: date,
					};

					logger.info(`At step ${i} ${parsedVehicle.carId} is at ${parsedVehicle.long} ${parsedVehicle.lat} doing ${vehicle['$'].speed}km/h.`);
					callback(parsedVehicle);
				});
			}

			i += intervalSize;

			if (i >= length) {
				stopInterval();
				onFinish();
			}
		}, 1000 * intervalSize);
	};
}
