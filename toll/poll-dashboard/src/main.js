import Vue from 'vue';
import App from './App.vue';
import router from './router';
import VueSocketIO from 'vue-socket.io';
import axios from 'axios';
import VueAxios from 'vue-axios';
import { L } from 'vue2-leaflet';

delete L.Icon.Default.prototype._getIconUrl;

L.Icon.Default.mergeOptions({
  iconRetinaUrl: require('@/assets/car.svg'),
  iconUrl: require('@/assets/car.svg'),
  shadowUrl: require('@/assets/car-shadow.png')
});

Vue.use(new VueSocketIO({
  connection: 'http://localhost:4000',
}));

Vue.use(VueAxios, axios);

Vue.config.productionTip = true;

new Vue({
  router,
  render: h => h(App),
}).$mount('#app');
