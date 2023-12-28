<template id="plan-details">
  <app-layout>
    <div v-if="noPlanFound">
      <p> We're sorry, we were not able to retrieve this plan.</p>
      <p> View <a :href="'/plans'">all plans</a>.</p>
    </div>
    <div class="card bg-light mb-3" v-if="!noPlanFound">
      <div class="card-header">
        <div class="row">
          <div class="col-6"> Plan Details </div>
          <div class="col" align="right">
            <button rel="tooltip" title="Update"
                    class="btn btn-info btn-simple btn-link"
                    @click="updatePlan()">
              <i class="far fa-save" aria-hidden="true"></i>
            </button>
            <button rel="tooltip" title="Delete"
                    class="btn btn-info btn-simple btn-link"
                    @click="deletePlan()">
              <i class="fas fa-trash" aria-hidden="true"></i>
            </button>
          </div>
        </div>
      </div>
      <div class="card-body">
        <form>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-plan-id">Plan ID</span>
            </div>
            <input type="number" class="form-control" v-model="plan.id" name="id" readonly placeholder="Id"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-plan-name">Name</span>
            </div>
            <input type="text" class="form-control" v-model="plan.name" name="name" placeholder="Name"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-plan-price">Price</span>
            </div>
            <input type="number" class="form-control" v-model="plan.price" name="price" placeholder="Price"/>
          </div>
        </form>
      </div>
    </div>
  </app-layout>
</template>

<script>
app.component("plan-details", {
  template: "#plan-details",
  data: () => ({
    plan: null,
    noPlanFound: false,
    formData: [],
  }),
  created: function () {
    const planId = this.$javalin.pathParams["plan-id"];

    const url = `/api/plans/${planId}`
    axios.get(url)
        .then(res => this.plan = res.data)
        .catch(error => {
          console.log("No plan found for id passed in the path parameter: " + error)
          this.noPlanFound = true
        });
  },
  methods: {
    updatePlan: function () {
      const planId = this.$javalin.pathParams["plan-id"];
      const url = `/api/plans/${planId}`
      axios.patch(url,
          {
            name: this.plan.name,
            price: this.plan.price,
          })
          .then(response =>
              this.plan.push(response.data))
          .catch(error => {
            console.log(error)
          })
      alert("Plan updated!")
    },
    deletePlan: function () {
      if (confirm("Do you really want to delete?")) {
        const planId = this.$javalin.pathParams["plan-id"];
        const url = `/api/plans/${planId}`
        axios.delete(url)
            .then(response => {
              alert("Plan deleted")
              //display the /plans endpoint
              window.location.href = '/plans';
            })
            .catch(function (error) {
              console.log(error)
            });
      }
    },
  },
});
</script>