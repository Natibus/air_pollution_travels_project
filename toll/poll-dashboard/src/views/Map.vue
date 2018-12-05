<template>
  <div>
    <div class="home">
      <div class="settings">
        <h5>Simulation Configuration</h5>

        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
          <input class="mdl-textfield__input" type="number" min="1" step="1" id="interval" v-model="form.interval">
          <label class="mdl-textfield__label" for="interval">Update interval size (seconds)</label>
        </div>

        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
          <input class="mdl-textfield__input" type="number" min="1" step="1" id="amount" v-model="form.amount">
          <label class="mdl-textfield__label" for="amount">Amount of cars to track</label>
        </div>

        <div>
          <button
            class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored mdl-js-ripple-effect"
            v-on:click="startSimulation"
          >
            Start Simulation
          </button>
        </div>

        <div>
          <button
            class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored mdl-js-ripple-effect mdl-button--accent"
            v-on:click="stopSimulation"
          >
            Stop Simulation
          </button>
        </div>

        <div>
          <button
            class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored mdl-js-ripple-effect mdl-button--accent"
            v-on:click="restartSimulation"
          >
            Restart Simulation
          </button>
        </div>
      </div>

      <l-map
        class="map"
        :zoom="zoom"
        :center="center"
      >
        <l-tile-layer :url="url"></l-tile-layer>

        <l-marker
          v-for="vehicle in vehicles"
          :lat-lng="vehicle.marker"
          :duration="1"
          :icon="vehicle.icon"
          :key="vehicle.id"
        >
          <l-popup>
            <div>{{vehicle.poll}}</div>
          </l-popup>
        </l-marker>
      </l-map>

    </div>

    <h5>Vehicle prices</h5>
    <ul class=" mdl-list">
      <li class="mdl-list__item" v-for="vehicle in vehicles">
        <i class="material-icons" v-if="vehicle.id.indexOf('truck') !== -1">local_shipping</i>
        <i class="material-icons" v-if="vehicle.id.indexOf('veh') !== -1">directions_car</i>
        <i class="material-icons" v-if="vehicle.id.indexOf('moto') !== -1">directions_bike</i>
        <span class="mdl-list__item-primary-content">
          {{ vehicle.id }}
        </span>
        <animated-number
          :value="getVehiclePoll(vehicle)"
          :formatValue="formatToPrice"
          :duration="300"
        />

      </li>
    </ul>

  </div>
</template>

<script>
  import {LMap, LTileLayer, LMarker, LPopup} from 'vue2-leaflet';
  import AnimatedNumber from 'animated-number-vue';
  import LMovingMarker from 'vue2-leaflet-movingmarker'

  export default {
    name: 'mapview',
    components: {
      LMap,
      LTileLayer,
      LMarker,
      LPopup,
      LMovingMarker,
      AnimatedNumber
    },
    data() {
      return {
        form: {
          amount: '',
          interval: 1,
        },
        url: 'http://{s}.tile.osm.org/{z}/{x}/{y}.png',
        center: [52.51781, 13.400772],
        marker: L.latLng(52.51781, 13.400772),
        zoom: 14,
        vehicles: [],
        showInfoWindow: false,
        infoWindowContent: '',
        currentMarkerObject: null,
        currentMarker: null,
        bounds: null
      }
    },
    methods: {
      formatToPrice(value) {
        return `${value.toFixed(2)}€`;
      },
      getVehiclePoll(vehicle) {
        let poll = 0;
        vehicle.events.map(event => {
          poll += event.poll
        });
        return poll;
      },
      saveVehicleInformation(newVehicle) {
        let vehicle = this.vehicles.find(existingVehicle => existingVehicle.id === newVehicle.id);
        if (!vehicle) {
          vehicle = {
            id: newVehicle.id,
            icon: L.icon({
              iconUrl: newVehicle.icon,
              iconSize: [21, 31],
              iconAnchor: [10.5, 31],
              popupAnchor: [4, -25]
            }),
            events: [],
            marker: L.latLng(newVehicle.lat, newVehicle.lng),
          };
          this.vehicles.push(vehicle);
        }

        vehicle.marker.lat = newVehicle.lat;
        vehicle.marker.lng = newVehicle.lng;
        vehicle.events.push(newVehicle);
        this.$nextTick();
      },
      startSimulation() {
        this.axios.get('http://localhost:8760/api/start', {
          params: {
            vehicles: this.form.amount,
            interval: this.form.interval,
          }
        }).then(response => {
          console.log(response);
        });
      },
      restartSimulation() {
        this.axios.get('http://localhost:8760/api/reset').then(response => {
          console.log(response);
        });
      },
      stopSimulation() {
        this.axios.get('http://localhost:8760/api/stop').then(response => {
          console.log(response);
        });
      }
    },
    sockets: {
      'location.update'(event) {
        this.saveVehicleInformation(event);
      },

      'location.reset'() {
        console.log('location reset received');
        this.vehicles = [];
      },
    },
  }
</script>

<style scoped>
  @import "http://cdn.leafletjs.com/leaflet-0.7.5/leaflet.css";

  .map {
    height: calc(100vh - 64px - 16px - 16px);
    flex: 0 1 75%;
  }

  .settings {
    flex: 0 0 25%;
  }

  .home {
    display: flex;
    justify-content: flex-start;
    align-items: stretch;
    position: relative;
  }

  button {
    margin-bottom: 20px;
  }

  .mdl-textfield {
    margin-right: 12px;
    max-width: 250px;
  }

  .material-icons {
    width: 24px;
    margin-right: 12px;
  }
</style>
