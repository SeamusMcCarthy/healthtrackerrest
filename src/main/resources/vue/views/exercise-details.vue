<template id="exercise-details">
  <app-layout>
    <div v-if="noExerciseFound">
      <p> We're sorry, we were not able to retrieve this exercise.</p>
      <p> View <a :href="'/exercises'">all exercises</a>.</p>
    </div>
    <div class="card bg-light mb-3" v-if="!noExerciseFound">
      <div class="card-header">
        <div class="row">
          <div class="col-6"> Exercise Details </div>
          <div class="col" align="right">
            <button rel="tooltip" title="Update"
                    class="btn btn-info btn-simple btn-link"
                    @click="updateExercise()">
              <i class="far fa-save" aria-hidden="true"></i>
            </button>
            <button rel="tooltip" title="Delete"
                    class="btn btn-info btn-simple btn-link"
                    @click="deleteExercise()">
              <i class="fas fa-trash" aria-hidden="true"></i>
            </button>
          </div>
        </div>
      </div>
      <div class="card-body">
        <form>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-exercise-id">Exercise ID</span>
            </div>
            <input type="number" class="form-control" v-model="exercise.id" name="id" readonly placeholder="Id"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-exercise-name">Name</span>
            </div>
            <input type="text" class="form-control" v-model="exercise.name" name="name" placeholder="Name"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-exercise-type">Type</span>
            </div>
            <input type="number" class="form-control" v-model="exercise.type" name="type" placeholder="Type"/>
          </div>
        </form>
      </div>
    </div>
  </app-layout>
</template>

<script>
app.component("exercise-details", {
  template: "#exercise-details",
  data: () => ({
    exercise: null,
    noExerciseFound: false,
    formData: [],
  }),
  created: function () {
    const exerciseId = this.$javalin.pathParams["exercise-id"];

    const url = `/api/exercises/${exerciseId}`
    axios.get(url)
        .then(res => this.exercise = res.data)
        .catch(error => {
          console.log("No exercise found for id passed in the path parameter: " + error)
          this.noExerciseFound = true
        });
  },
  methods: {
    updateExercise: function () {
      const exerciseId = this.$javalin.pathParams["exercise-id"];
      const url = `/api/exercises/${exerciseId}`
      axios.patch(url,
          {
            name: this.exercise.name,
            type: this.exercise.type,
          })
          .then(response =>
              this.exercise.push(response.data))
          .catch(error => {
            console.log(error)
          })
      alert("Exercise updated!")
    },
    deleteExercise: function () {
      if (confirm("Do you really want to delete?")) {
        const exerciseId = this.$javalin.pathParams["exercise-id"];
        const url = `/api/exercises/${exerciseId}`
        axios.delete(url)
            .then(response => {
              alert("Exercise deleted")
              //display the /exercises endpoint
              window.location.href = '/exercises';
            })
            .catch(function (error) {
              console.log(error)
            });
      }
    },
  },

});
</script>