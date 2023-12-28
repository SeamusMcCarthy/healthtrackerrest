<template id="plan-overview">
  <app-layout>
    <div class="card bg-light mb-3">
      <div class="card-header">
        <div class="row">
          <div class="col-6">
            Plans
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
        <form id="addPlan">
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-plan-name">Name</span>
            </div>
            <input type="text" class="form-control" v-model="formData.name" name="name" placeholder="Name"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-exercise-type">Price</span>
            </div>
            <input type="number" class="form-control" v-model="formData.price" name="price" placeholder="Price"/>
          </div>
        </form>
        <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link" @click="addPlan()">Add Plan</button>
      </div>
    </div>

    <div>
      <table class="table">
        <thead>
        <tr>
          <th scope="col">#</th>
          <th scope="col">Name</th>
          <th scope="col">Price</th>
          <th scope="col">Update</th>
          <th scope="col">Delete</th>
        </tr>
        </thead>
        <tr v-for="(plan,index) in plans" v-bind:key="index">
          <td>{{plan.id}}</td>
          <td>{{plan.name}}</td>
          <td>{{plan.price}}</td>
          <td>
            <a :href="`/plans/${plan.id}`">
              <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link">
                <i class="fa fa-pencil" aria-hidden="true"></i>
              </button>
            </a>
          </td>
          <td>
            <button rel="tooltip" title="Delete" class="btn btn-info btn-simple btn-link"
                    @click="deletePlan(plan, index)">
              <i class="fas fa-trash" aria-hidden="true"></i>
            </button>
          </td>
        </tr>
      </table>
    </div>
  </app-layout>
</template>
<script>
app.component("plan-overview", {
  template: "#plan-overview",
  data: () => ({
    formData: [],
    hideForm: true,
    plans: [],
  }),
  created() {
    this.fetchPlans();
  },
  methods: {
    fetchPlans: function () {
      axios.get("/api/plans")
          .then(res => this.plans = res.data)
          .catch(() => alert("Error while fetching plans"));
    },
    addPlan: function (){
      const url = `/api/plans`;
      axios.post(url,
          {
            name: this.formData.name,
            price: this.formData.price,
          })
          .then(response => {
            this.plans.push(response.data)
            this.hideForm= true;
          })
          .catch(error => {
            console.log(error)
          })
    },
    deletePlan: function (plan, index) {
      if (confirm('Are you sure you want to delete this plan? This action cannot be undone.', 'Warning')) {
        //user confirmed delete
        const planId = plan.id;
        const url = `/api/plans/${planId}`;
        axios.delete(url)
            .then(response =>
                //delete from the local state so Vue will reload list automatically
                this.plans.splice(index, 1).push(response.data))
            .catch(function (error) {
              console.log(error)
            });
      }
    },
  }
});
</script>