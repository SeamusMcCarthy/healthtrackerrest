<template id="card-content-layout">
  <div class="card bg-light mb-3" v-if="!noUserFound">
    <slot></slot>
  </div>
</template>

<script>
app.component("card-content-layout", {
  template: "#card-content-layout",
});
</script>
