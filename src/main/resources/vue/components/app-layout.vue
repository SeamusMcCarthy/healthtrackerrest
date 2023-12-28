<template id="app-layout">
  <div class="app-layout">
    <div class="container">
      <!-- Start of navbar -->
      <nav class="navbar navbar-expand-lg navbar-light">
        <a class="navbar-brand" v-bind:href="'/trainer/' + userId" v-if="text.includes('trainer')">Home</a>
        <a class="navbar-brand" v-bind:href="'/users/' + userId" v-else-if="text.includes('member')">Home</a>
        <a class="navbar-brand" href="/" v-else>Home</a>

        <button class="navbar-toggler" type="button" data-toggle="collapse"
                data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon" ></span>
        </button>

        <div class="collapse navbar-collapse col" id="navbarSupportedContent">
          <ul class="navbar-nav mr-auto" v-if="text.includes('er')">
            <li class="nav-item active" v-if="text.includes('trainer')">
              <a class="nav-link" href="/users">
                Users <span class="sr-only">(current)</span>
              </a>
            </li>
            <li class="nav-item" v-if="text.includes('trainer')">
              <a class="nav-link" href="/exercises">Exercises</a>
            </li>
            <li class="nav-item" v-if="text.includes('trainer')">
              <a class="nav-link" href="/plans">Plans</a>
            </li>
            <li class="nav-item">
<!--              <div class="col" align="right">-->
                <button rel="tooltip" title="Logout"
                        class="btn btn-info btn-simple btn-link"
                        @click="logout()">
                  Logout
                </button>
<!--              </div>-->
            </li>
          </ul>
          <ul class="navbar-nav mr-auto" v-else>
            <li class="nav-item active">
              <a class="nav-link" href="/login">
                Login <span class="sr-only">(current)</span>
              </a>
            </li>
          </ul>
        </div>
      </nav>
      <!--End of nav bar-->
      <!--Start of main content area-->
      <div class="content mt-3">
        <div class="container-fluid">
          <slot></slot>
        </div>
      </div>
      <!--End of main content area-->
    </div>
  </div>
</template>

<script>
app.component("app-layout", {
  template: "#app-layout",
  data() {
    return {
      userId: [],
      text: [],
    }
  },
  created: function() {
    this.text = 'none'
    if (window.sessionStorage.getItem('accType') === 'member') {
      this.text = 'member'
    }
    if (window.sessionStorage.getItem('accType') === 'trainer') {
      this.text = 'trainer'
    }
    this.userId = window.sessionStorage.getItem('id')
  },
  methods: {
    logout: function() {
      window.sessionStorage.removeItem('accType')
      window.sessionStorage.removeItem('id')
      window.location.href = '/'
    }
  }});
</script>

<style>
.navbar{
  background-color: #e3f2fd;
}
</style>