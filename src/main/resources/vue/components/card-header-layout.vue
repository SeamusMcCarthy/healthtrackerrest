<template id="card-header-layout">
  <div class="card-header">
    <div class="row">
      <slot></slot>
    </div>
  </div>
</template>

<script>
app.component("card-header-layout", {
  template: "#card-header-layout",
});
</script>
