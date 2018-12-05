'use strict';

const URL = process.env.NATS_URL;
const NATS = require('nats');
const nats = NATS.connect({url: URL, json: true, name: 'poll-dashboard'});
const sockets = {};

const handler = {
  registerSocket: registerSocket,
  removeSocket: removeSocket,
};

function registerSocket(skt) {
  sockets[skt.id] = skt;
}

function removeSocket(skt) {
  delete sockets[skt.id];
}

function publishEvent(type, data) {
  Object.values(sockets).map(socket => {
    socket.emit(type, data);
  });
}

nats.subscribe('MOL-iosl2018.EVENTB.toll-simulator.location.update',event => {
  const data = {
    id: event.data.carId,
    lat: parseFloat(event.data.lat),
    lng: parseFloat(event.data.long),
    poll: Math.random(),
    icon: getIcon(event.data.carId),
  };

  publishEvent('location.update', data);
});

nats.subscribe('MOL-iosl2018.EVENTB.toll-simulator.location.reset', () => {
  publishEvent('location.reset');
});

nats.subscribe('>', (data) => {
  publishEvent('nats', data);
});

function getIcon(id) {
  if (id.indexOf('truck') !== -1) {
    return '/images/truck.svg'
  } else if (id.indexOf('moto') !== -1) {
    return '/images/moto.svg'
  } else {
    return '/images/car.svg'
  }
}

module.exports = handler;