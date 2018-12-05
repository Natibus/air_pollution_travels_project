const express = require('express');
const morgan = require('morgan');
const path = require('path');
const app = express();
const bodyParser = require('body-parser');
const server = require('http').Server(app);
const io = require('socket.io')(server);
const natsHandler = require('./app/natsHandler');

// Sends static files  from the public path directory
app.use(express.static(path.join(__dirname, '/dist')));

// Use morgan to log request in dev mode
app.use(morgan('dev'));

app.use(bodyParser.json());

app.use(bodyParser.urlencoded({extended: true}));

const port = 4000;

server.listen(port);

console.log('App listening on port ' + port);

io.on('connection', (socket) => {
  natsHandler.registerSocket(socket);

  socket.on('disconnect', () => {
    natsHandler.removeSocket(socket);
  });
});

app.use((req, res, next) => {
  // Website you wish to allow to connect
  res.setHeader('Access-Control-Allow-Origin', 'http://localhost:' + port);

  // Request methods you wish to allow
  res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE');

  // Request headers you wish to allow
  res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type');

  // Pass to next layer of middleware
  next()
});

// Server index.html page when request to the root is made
app.get('/', (req, res) => {
  res.sendFile(path.join(__dirname, '/dist/index.html'));
});