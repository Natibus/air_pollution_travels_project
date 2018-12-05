<template>
  <div class="scope">
    <iframe src="http://127.0.0.1:4040"></iframe>
  </div>
</template>

<style scoped>
  .scope {
    flex-grow: 1;
  }

  iframe {
    width: 100%;
    height: calc(100vh - 64px - 16px - 16px);
    border: none;
    overflow: hidden;
  }
</style>
