<!-- the "home-page" element is passed as a parameter to VueComponent in the JavalinConfig file -->
<template id="trainer-overview">
  <app-layout>
<!--    <h1> Welcome to Health Tracker App</h1>-->
    <div class="row">
      <div class="col">
        <div class="card">
          <h5 class="card-header">Registered Users</h5>
          <div class="card-body">
            <h5 class="card-title">{{users.length}} users</h5>
            <a href="/users" class="btn btn-primary">More Details...</a>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="card">
          <h5 class="card-header">My Users</h5>
          <div class="card-body">
            <h5 class="card-title">{{myUsers.length}} users</h5>
            <a v-bind:href="`/trainer/${trainerId}/users`" class="btn btn-primary">More Details...</a>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="card">
          <h5 class="card-header">Exercises</h5>
          <div class="card-body">
            <h5 class="card-title">{{exercises.length}} exercises</h5>
            <a href="/exercises" class="btn btn-primary">More Details...</a>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="card">
          <h5 class="card-header">Plans</h5>
          <div class="card-body">
            <h5 class="card-title">{{plans.length}} plans</h5>
            <a href="/plans" class="btn btn-primary">More Details...</a>
          </div>
        </div>
      </div>
    </div>
  </app-layout>
</template>

<script>
app.component('trainer-overview',
    {
      template: "#trainer-overview",
      data: () => ({
        users: [],
        myUsers: [],
        exercises: [],
        plans: [],
        trainerId: [],
      }),
      created() {
        const trainerId = this.$javalin.pathParams["trainer-id"];
        this.trainerId = trainerId
        if (trainerId !== window.sessionStorage.getItem('id')) {
          window.location.href = '/'
          window.sessionStorage.removeItem('accType')
          window.sessionStorage.removeItem('id')
          alert('Only the logged in trainer profile can be viewed. Logging you out!')
        }
        axios.get("/api/users")
            .then(res => {
              this.users = res.data
            })
            .catch(() => alert("Error while fetching users"));
        axios.get("/api/exercises")
            .then(res => this.exercises = res.data)
            .catch(() => alert("Error while fetching exercises"));
        axios.get("/api/plans")
            .then(res => this.plans = res.data)
            .catch(() => alert("Error while fetching plans"));
        axios.get(`/api/trainers/${trainerId}/users`)
            .then(res => this.myUsers = res.data)
            .catch(() => alert("Error while fetching trainer's users"));
      }
    });
</script>