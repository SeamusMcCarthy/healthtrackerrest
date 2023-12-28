<!-- the "home-page" element is passed as a parameter to VueComponent in the JavalinConfig file -->
<template id="carousel-page">
  <app-layout>
    <div class="row">
      <div class="w-80 mx-auto">
        <h2 id="siteHeader">Gym-Bob - Your Health Tracker App!!</h2>
      </div>
    </div>

    <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
      <ol class="carousel-indicators">
        <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
        <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
        <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
      </ol>
      <div class="carousel-inner">
        <div class="carousel-item active">
          <img class="d-block w-100" src="https://images.pexels.com/photos/1954524/pexels-photo-1954524.jpeg" alt="Running machine">
        </div>
        <div class="carousel-item">
          <img class="d-block w-100" src="https://images.pexels.com/photos/1552242/pexels-photo-1552242.jpeg" alt="Workout with ropes">
        </div>
        <div class="carousel-item">
          <img class="d-block w-100" src="https://images.pexels.com/photos/841130/pexels-photo-841130.jpeg" alt="Lifting weights">
        </div>
      </div>
      <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
      </a>
      <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
      </a>
    </div>
  </app-layout>
</template>

<script>
app.component('carousel-page',
    {
      template: "#carousel-page",
    });
</script>

<style>
  @import url('https://fonts.googleapis.com/css2?family=Michroma&display=swap');
  #siteHeader {
    font-family: 'Michroma', sans-serif ;
  }
</style>