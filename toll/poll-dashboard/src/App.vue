<template>
  <div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
    <header class="mdl-layout__header">
      <div class="mdl-layout__header-row">
        <span class="mdl-layout-title">Poll Dashboard - {{ stateName }}</span>
        <div class="mdl-layout-spacer"></div>
        <nav class="mdl-navigation mdl-layout--large-screen-only">
          <router-link class="mdl-navigation__link" to="/">Simulation</router-link>
          <router-link class="mdl-navigation__link" to="/scope">Scope</router-link>
          <router-link class="mdl-navigation__link" to="/events">Events</router-link>
        </nav>
      </div>
    </header>
    <div class="mdl-layout__drawer">
      <span class="mdl-layout-title">Poll Dashboard</span>
      <nav class="mdl-navigation">
        <router-link class="mdl-navigation__link" to="/" v-on:click.native="close">Simulation</router-link>
        <router-link class="mdl-navigation__link" to="/scope" v-on:click.native="close">Scope</router-link>
        <router-link class="mdl-navigation__link" to="/events" v-on:click.native="close">Events</router-link>
      </nav>
    </div>
    <main class="mdl-layout__content">
      <router-view/>
    </main>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        stateName: '',
      }
    },
    methods: {
      close() {
        const d = document.querySelector('.mdl-layout');
        d.MaterialLayout.toggleDrawer();
      },
    },
    watch: {
      $route(to) {
        this.stateName = to.name;
      }
    }
  }
</script>


<style>
  .mdl-layout__content {
    display: flex;
    flex-direction: column;
  }
</style>
