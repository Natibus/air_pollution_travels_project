'use strict';

module.exports = {
	name: 'status',
	actions: {
		/**
		 * Returns success message to provide a health check
		 *
		 * @returns
		 */
		status() {
			return 'Success';
		},
	},
};