'use strict';
const simulator = require('../simulator/simulator');
let messageId = 0;
let simulationPromise;

module.exports = {
	name: 'simulator',

  settings: {
    topic: 'toll-simulator'
  },

	actions: {
		start(context) {
			if (context.params.vehicles && isNaN(context.params.vehicles)) {
				this.logger.error('vehicles has an invalid type.');
				return 'Invalid vehicle type';
			}

			if (context.params.interval && isNaN(context.params.interval)) {
				this.logger.error('interval has an invalid type.');
				return 'Invalid interval type';
			}

			this.startSimulation(context.params.interval, context.params.vehicles);
			return 'Start successful';
		},

		stop() {
			this.stopSimulation();
			return 'Stop successful';
		},

		reset() {
			this.resetSimulation();
			return 'Reset successful';
		},

	},

	/**
	 * Methods
	 */
	methods: {
		sendUpdates(payload) {
			// append message id to payload
			payload.messageId = messageId;

			// emit `location.update` event
			this.broker.emit('location.update', payload, [this.settings.topic]);

			// increase message id
			messageId++;
		},

		sendReset() {
			// emit `location.reset` event
			this.broker.emit('location.reset', {messageId}, [this.settings.topic]);

			// increase message id
			messageId++;
		},

		resetSimulation() {
			simulator.resetSimulation();
			this.sendReset();
		},

		stopSimulation() {
			simulator.stopSimulation();
			simulationPromise = undefined;
			this.sendReset();
		},

		startSimulation(itvl, vhcls) {
			if (simulationPromise) {
				this.stopSimulation();
			}

			const interval = isNaN(parseInt(itvl)) ? 1 : parseInt(itvl);
			const vehicles = isNaN(parseInt(vhcls)) ? null : parseInt(vhcls);
			simulationPromise = simulator.startSimulation(interval, vehicles, this.sendUpdates);

			// when the simulation finished
			simulationPromise.then(() => {
				this.sendReset();

				// start the next iteration with a short timeout
				setTimeout(() => {
					this.startSimulation(interval, vehicles);
				}, 2000);
			});
		},
	},

	/**
	 * Service started lifecycle event handler
	 */
	started() {
		simulator.setLogger(this.logger);
		this.startSimulation();
	},

	/**
	 * Service stopped lifecycle event handler
	 */
	stopped() {
		simulator.stopSimulation();
	}
};