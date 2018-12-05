'use strict';

const ApiGateway = require('moleculer-web');

module.exports = {
	name: 'api',
	mixins: [ApiGateway],

	settings: {
		port: process.env.PORT || 3000,

		routes: [{
			path: '/api',
			whitelist: [
				'**'
			],
			aliases: {
				'GET /status': 'status.status',
				'GET /start': 'simulator.start',
				'GET /stop': 'simulator.stop',
				'GET /reset': 'simulator.reset',
			},
			mappingPolicy: 'restrict',
		}],

		assets: {
			folder: 'public'
		},

    cors: {
      // Configures the Access-Control-Allow-Origin CORS header.
      origin: "*",
      // Configures the Access-Control-Allow-Methods CORS header.
      methods: ["GET", "OPTIONS", "POST", "PUT", "DELETE"],
      // Configures the Access-Control-Allow-Headers CORS header.
      allowedHeaders: [],
      // Configures the Access-Control-Expose-Headers CORS header.
      exposedHeaders: [],
      // Configures the Access-Control-Allow-Credentials CORS header.
      credentials: false,
      // Configures the Access-Control-Max-Age CORS header.
      maxAge: 3600
    },
	}
};
