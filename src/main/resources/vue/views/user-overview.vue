<template id="user-overview">
  <app-layout>
    <div class="card bg-light mb-3">
      <div class="card-header">
        <div class="row">
          <div class="col-6">
            Users
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
        <form id="addUser">
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-name">Name</span>
            </div>
            <input type="text" class="form-control" v-model="formData.name" name="name" placeholder="Name"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-email">Email</span>
            </div>
            <input type="email" class="form-control" v-model="formData.email" name="email" placeholder="Email"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-password">Password</span>
            </div>
            <input type="password" class="form-control" v-model="formData.password" name="password" placeholder="Password"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-gender">Gender</span>
            </div>
            <input type="text" class="form-control" v-model="formData.gender" name="gender" placeholder="Gender"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-height">Height</span>
            </div>
            <input type="number" class="form-control" v-model="formData.height" name="height" placeholder="Height in cm"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-start-weight">Starting Weight</span>
            </div>
            <input type="number" class="form-control" v-model="formData.startWeight" name="startWeight" placeholder="Starting Weight in kg"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-trainer-id">Trainer</span>
            </div>
<!--            <input type="number" class="form-control" v-model="formData.trainerId" name="trainerId" placeholder="Trainer ID"/>-->
            <select class="form-control" v-model="formData.trainerId" id="select-trainer-id">
              <option disabled value="">Please select one</option>
              <option :value=trainer.id v-for="trainer in trainers">{{trainer.name}}</option>
            </select>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-plan-id">Plan</span>
            </div>
<!--            <input type="number" class="form-control" v-model="formData.planId" name="planId" placeholder="Plan ID"/>-->
            <select class="form-control" v-model="formData.planId" id="select-plan-id">
              <option disabled value="">Please select one</option>
              <option :value=plan.id v-for="plan in plans">{{plan.name}}</option>
            </select>
          </div>
        </form>
        <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link" @click="addUser()">Add User</button>
      </div>
    </div>

    <div>
      <table class="table">
        <thead>
        <tr>
          <th scope="col">#</th>
          <th scope="col">Name</th>
          <th scope="col">Email</th>
          <th scope="col">Trainer</th>
          <th scope="col">Plan</th>
          <th scope="col">Update</th>
          <th scope="col">Delete</th>
        </tr>
        </thead>
        <tr v-for="(user,index) in users" v-bind:key="index">
          <td><a :href="`/users/${user.id}`">{{user.id}}</a></td>
          <td>{{user.name}}</td>
          <td>{{user.email}}</td>
          <td>{{getTrainer(user.trainerId)}}</td>
          <td>{{getPlan(user.planId)}}</td>
          <td>
            <a :href="`/users/${user.id}`">
              <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link">
                <i class="fa fa-pencil" aria-hidden="true"></i>
              </button>
            </a>
          </td>
          <td>
            <button rel="tooltip" title="Delete" class="btn btn-info btn-simple btn-link"
                    @click="deleteUser(user, index)">
              <i class="fas fa-trash" aria-hidden="true"></i>
            </button>
          </td>
        </tr>
      </table>
    </div>
  </app-layout>
</template>
<script>
app.component("user-overview", {
  template: "#user-overview",
  data: () => ({
    users: [],
    formData: [],
    hideForm: true,
    trainers: [],
    plans: [],
  }),
  created() {
    this.fetchUsers();
    this.fetchTrainers();
    this.fetchPlans();
  },
  methods: {
    fetchUsers: function () {
      axios.get("/api/users")
          .then(res =>
            this.users = res.data
          )
          .catch(() => alert("Error while fetching users"));

    },
    deleteUser: function (user, index) {
      if (confirm('Are you sure you want to delete this user? This action cannot be undone.', 'Warning')) {
        //user confirmed delete
        const userId = user.id;
        const url = `/api/users/${userId}`;
        axios.delete(url)
            .then(response =>
                //delete from the local state so Vue will reload list automatically
                this.users.splice(index, 1).push(response.data))
            .catch(function (error) {
              console.log(error)
            });

      }
    },
    fetchTrainers: function() {
      axios.get("/api/trainers")
          .then(res => this.trainers = res.data)
          .catch(() => alert("Error fetching trainers"));
    },
    fetchPlans: function() {
      axios.get("/api/plans")
          .then(res =>
            this.plans = res.data
          )
          .catch(() => alert("Error fetching plans"));
    },
    addUser: function (){
      const url = `/api/users`;
      axios.post(url,
          {
            name: this.formData.name,
            email: this.formData.email,
            password: this.formData.password,
            gender: this.formData.gender,
            height: this.formData.height,
            startWeight: this.formData.startWeight,
            trainerId: this.formData.trainerId,
            planId: this.formData.planId
          })
          .then(response => {
            this.users.push(response.data)
            this.hideForm= true;
          })
          .catch(error => {
            console.log(error)
          })
    },
    getTrainer: function(id) {
      return this.trainers.filter((trainer) => trainer.id === id)[0].name
    },
    getPlan: function(id) {
      return this.plans.filter((plan) => plan.id === id)[0].name
    }
  }
});
</script>