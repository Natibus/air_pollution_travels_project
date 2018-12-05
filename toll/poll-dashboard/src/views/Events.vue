<template>
  <div class="home">
    <table class="mdl-data-table mdl-js-data-table mdl-shadow--2dp">
      <thead>
      <tr>
        <th class="mdl-data-table__cell--non-numeric">Topic</th>
        <th class="mdl-data-table__cell--non-numeric">Timestamp</th>
        <th class="mdl-data-table__cell--non-numeric">Sender</th>
        <th class="mdl-data-table__cell--non-numeric">Payload</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="event in events">
        <td class="mdl-data-table__cell--non-numeric" v-bind:style="{color: event.color}">{{event.event}}</td>
        <td class="mdl-data-table__cell--non-numeric">{{event.timestamp.toLocaleString()}}</td>
        <td class="mdl-data-table__cell--non-numeric">{{event.sender}}</td>
        <td class="mdl-data-table__cell--non-numeric">{{event.data}}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
  const colors = ['black', 'green', 'navy', 'red', 'yellow', 'orange', 'purple', 'teal'];
  let nextIndex = 0;
  const mappedColors = {};

  export default {
    name: 'events',

    data() {
      return {
        events: [],
      }
    },
    sockets: {
      'nats'(event) {
        console.log(event);
        event.timestamp = new Date();

        event.event = event.event || 'heartbeat';
        event.data = event.data || `CPU: ${event.cpu}%`;

        if (!mappedColors[event.event]) {
          mappedColors[event.event] = colors[nextIndex];
          nextIndex++;
        }

        event.color = mappedColors[event.event];

        this.events.unshift(event);
      },
    }
  }
</script>

<style scoped>
  table {
    width: 100%;
  }

  td {
    max-width: 250px;
    overflow: hidden;
    text-overflow: ellipsis;
  }
</style>
