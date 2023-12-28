<template id="exercise-overview">
  <app-layout>
    <div class="card bg-light mb-3">
      <div class="card-header">
        <div class="row">
          <div class="col-6">
            Exercises
          </div>
          <div class="col" align="right">
            <button rel="tooltip" title="Add"
                    class="btn btn-info btn-simple btn-link"
                    @click="hideForm=!hideForm">
              <i class="fa fa-plus" aria-hidden="true"></i>
            </button>
          </div>
        </div>
      </div>
      <div class="card-body" :class="{ 'd-none': hideForm }">
        <form id="addExercise">
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-exercise-name">Name</span>
            </div>
            <input type="text" class="form-control" v-model="formData.name" name="name" placeholder="Name"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-exercise-type">Type</span>
            </div>
            <input type="text" class="form-control" v-model="formData.type" name="type" placeholder="Type"/>
          </div>
        </form>
        <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link" @click="addExercise()">Add Exercise</button>
      </div>
    </div>

    <div>
      <table class="table">
        <thead>
        <tr>
          <th scope="col">#</th>
          <th scope="col">Name</th>
          <th scope="col">Type</th>
          <th scope="col">Update</th>
          <th scope="col">Delete</th>
        </tr>
        </thead>
        <tr v-for="(exercise,index) in exercises" v-bind:key="index">
          <td>{{exercise.id}}</td>
          <td>{{exercise.name}}</td>
          <td>{{exercise.type}}</td>
          <td>
            <a :href="`/exercises/${exercise.id}`">
              <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link">
                <i class="fa fa-pencil" aria-hidden="true"></i>
              </button>
            </a>
          </td>
          <td>
            <button rel="tooltip" title="Delete" class="btn btn-info btn-simple btn-link"
                    @click="deleteExercise(exercise, index)">
              <i class="fas fa-trash" aria-hidden="true"></i>
            </button>
          </td>
        </tr>
      </table>
    </div>
  </app-layout>
</template>
<script>
app.component("exercise-overview", {
  template: "#exercise-overview",
  data: () => ({
    formData: [],
    hideForm: true,
    exercises: [],
  }),
  created() {
    this.fetchExercises();
  },
  methods: {
    fetchExercises: function () {
      axios.get("/api/exercises")
          .then(res => this.exercises = res.data)
          .catch(() => alert("Error while fetching exercises"));
    },
    addExercise: function (){
      const url = `/api/exercises`;
      axios.post(url,
          {
            name: this.formData.name,
            type: this.formData.type,
          })
          .then(response => {
            this.exercises.push(response.data)
            this.hideForm= true;
          })
          .catch(error => {
            console.log(error)
          })
    },
    deleteExercise: function (exercise, index) {
      if (confirm('Are you sure you want to delete this exercise? This action cannot be undone.', 'Warning')) {
        //user confirmed delete
        const exerciseId = exercise.id;
        const url = `/api/exercises/${exerciseId}`;
        axios.delete(url)
            .then(response =>
                //delete from the local state so Vue will reload list automatically
                this.exercises.splice(index, 1).push(response.data))
            .catch(function (error) {
              console.log(error)
            });
      }
    },
  }
});
</script>