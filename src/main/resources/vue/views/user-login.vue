<template id="user-login">
  <app-layout>
    <div class="card bg-light mb-3">
      <div class="card-header">
        <div class="row">
          <div class="col-6">
            User Login
          </div>
        </div>
      </div>
      <div class="card-body">
        <form id="login">
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
          <div class="field">
            <p>Please select your account type</p>
            <div class="form-check">
              <input class="form-check-input" type="radio" name="accType" value="member" id="acc-radio-button-1" v-model="formData.accType">
              <label class="form-check-label" for="acc-radio-button-1">
                Member
              </label>
            </div>
            <div class="form-check">
              <input class="form-check-input" type="radio" name="accType" value="trainer" id="acc-radio-button-2" v-model="formData.accType">
              <label class="form-check-label" for="acc-radio-button-2">
                Trainer
              </label>
            </div>
          </div>
        </form>
        <button rel="tooltip" title="Login" class="btn btn-info btn-simple btn-link" @click="login()">Login</button>
      </div>
    </div>

  </app-layout>
</template>

<script>
app.component("user-login", {
  template: "#user-login",
  data: () => ({
    formData: [],
  }),
  methods: {
    login: function (){
      const url = `/api/authenticate`;
      axios.post(url,
          {
            email: this.formData.email,
            password: this.formData.password,
            accType: this.formData.accType
          })
          .then(response => {
            window.sessionStorage.setItem('accType', this.formData.accType)
            if (this.formData.accType === 'member') {
              window.location.href = '/users/' + response.data.id;
              window.sessionStorage.setItem('id', response.data.id)
            } else {
              console.log(response.data)
              window.location.href = '/trainer/' + response.data.id
              window.sessionStorage.setItem('id', response.data.id)
            }
          })
          .catch(error => {
            if (error.response.status === 401 || error.response.status === 403) {
              alert('Invalid details, please try again!')
            }
          })
    },
  },
});
</script>