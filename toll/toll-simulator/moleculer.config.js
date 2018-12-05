'use strict';

module.exports = {
	namespace: 'iosl2018',
	nodeID: 'toll-simulator',

	logger: true,
	logLevel: 'info',
	logFormatter: 'default',

	transporter: {
		type: 'NATS',
		options: {
			url: process.env.NATS_URL,
			user: process.env.NATS_USER,
			pass: process.env.NATS_PASSWORD,
		}
	},

	serializer: 'JSON',

	requestTimeout: 10 * 1000,
	retryPolicy: {
		enabled: false,
	},

	maxCallLevel: 100,
	heartbeatInterval: 5,
	heartbeatTimeout: 15,

	disableBalancer: true,

	registry: {
		strategy: 'RoundRobin',
	},

	validation: false,
	validator: null,

	internalServices: false,
	internalMiddlewares: false,

	hotReload: false,

	// Called after broker created.
	created(broker) {
		broker.logger.info(`Connected to NATS server at ${process.env.NATS_URL}`);
	},
};