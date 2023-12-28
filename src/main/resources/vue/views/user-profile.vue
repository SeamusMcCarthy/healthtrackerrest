<template id="user-profile">
  <app-layout>
    <div v-if="noUserFound">
      <p> We're sorry, we were not able to retrieve this user.</p>
      <p> View <a :href="'/users'">all users</a>.</p>
    </div>
    <div class="card bg-light mb-3" v-if="!noUserFound">
      <div class="card-header">
        <div class="row">
          <div class="col-6"> User Profile </div>
          <div class="col" align="right">
            <button rel="tooltip" title="Update"
                    class="btn btn-info btn-simple btn-link"
                    @click="updateUser()">
              <i class="far fa-save" aria-hidden="true"></i>
            </button>
            <button rel="tooltip" title="Delete"
                    class="btn btn-info btn-simple btn-link"
                    @click="deleteUser()">
              <i class="fas fa-trash" aria-hidden="true"></i>
            </button>
          </div>
        </div>
      </div>
      <div class="card-body">
        <form>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-id">User ID</span>
            </div>
            <input type="number" class="form-control" v-model="user.id" name="id" readonly placeholder="Id"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-name">Name</span>
            </div>
            <input type="text" class="form-control" v-model="user.name" name="name" placeholder="Name"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-email">Email</span>
            </div>
            <input type="email" class="form-control" v-model="user.email" name="email" placeholder="Email"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-id">Password</span>
            </div>
            <input type="password" class="form-control" v-model="user.password" name="password" placeholder="Password"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-id">Gender</span>
            </div>
            <input type="text" class="form-control" v-model="user.gender" name="gender" placeholder="Gender"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-id">Height</span>
            </div>
            <input type="number" class="form-control" v-model="user.height" name="height" placeholder="Height"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-id">Starting Weight</span>
            </div>
            <input type="number" class="form-control" v-model="user.startWeight" name="startWeight" placeholder="Starting Weight"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-id">Trainer ID</span>
            </div>
            <input type="number" class="form-control" v-model="user.trainerId" name="trainerId" placeholder="Trainer ID"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-id">Plan ID</span>
            </div>
            <input type="number" class="form-control" v-model="user.planId" name="planId" placeholder="Plan ID"/>
          </div>
        </form>
      </div>
<!--      <div class="card-footer text-left" v-if="!noUserFound">-->
<!--        <div class="row">-->
<!--          <div class="col-6">-->
<!--            <p  v-if="activities.length === 0"> No activities yet...</p>-->
<!--            <p  v-if="activities.length > 0"> Activities so far...</p>-->
<!--          </div>-->
<!--          <div class="col" align="right">-->
<!--            <button rel="tooltip" title="Add"-->
<!--                    class="btn btn-info btn-simple btn-link"-->
<!--                    @click="hideActivityForm=!hideActivityForm">-->
<!--              <i class="fa fa-plus" aria-hidden="true"></i>-->
<!--            </button>-->
<!--          </div>-->
<!--        </div>-->
<!--        <div class="card-footer" :class="{ 'd-none': hideActivityForm }">-->
<!--          <form id="addActivity">-->
<!--            <div class="input-group mb-3">-->
<!--              <div class="input-group-prepend">-->
<!--                <span class="input-group-text" id="input-activity-type">Type</span>-->
<!--              </div>-->
<!--&lt;!&ndash;              <input type="number" class="form-control" v-model="formData.activityType" name="activityType" placeholder="Type"/>&ndash;&gt;-->
<!--              <select class="form-control" v-model="formData.activityType" id="select-activity-type">-->
<!--                <option disabled value="">Please select one</option>-->
<!--                <option :value=exercise.id v-for="exercise in exercises">{{exercise.name}}</option>-->
<!--              </select>-->
<!--            </div>-->
<!--            <div class="input-group mb-3">-->
<!--              <div class="input-group-prepend">-->
<!--                <span class="input-group-text" id="input-activity-duration">Duration</span>-->
<!--              </div>-->
<!--              <input type="number" class="form-control" v-model="formData.duration" name="duration" placeholder="Duration"/>-->
<!--            </div>-->
<!--            <div class="input-group mb-3">-->
<!--              <div class="input-group-prepend">-->
<!--                <span class="input-group-text" id="input-activity-calories">Duration</span>-->
<!--              </div>-->
<!--              <input type="number" class="form-control" v-model="formData.calories" name="calories" placeholder="Calories"/>-->
<!--            </div>-->
<!--            <div class="input-group mb-3">-->
<!--              <div class="input-group-prepend">-->
<!--                <span class="input-group-text" id="input-activity-started">Date</span>-->
<!--              </div>-->
<!--              <input type="date" class="form-control" v-model="formData.started" name="started" placeholder="Date"/>-->
<!--            </div>-->
<!--          </form>-->
<!--          <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link" @click="addActivity()">Add Activity</button>-->
<!--        </div>-->
<!--        <div v-if="activities.length > 0">-->
<!--          <table class="table">-->
<!--            <thead>-->
<!--            <tr>-->
<!--              <th scope="col">Date</th>-->
<!--              <th scope="col">Type</th>-->
<!--              <th scope="col">Duration</th>-->
<!--              <th scope="col">Calories</th>-->
<!--              <th scope="col">Delete</th>-->
<!--            </tr>-->
<!--            </thead>-->
<!--            <tr v-for="activity in activities">-->
<!--              <td>{{formatDate(activity.started)}}</td>-->
<!--              <td>{{getExercise(activity.activityType)}}</td>-->
<!--              <td>{{activity.duration}}</td>-->
<!--              <td>{{activity.calories}}</td>-->
<!--              <td>-->
<!--                <button rel="tooltip" title="Delete"-->
<!--                        class="btn btn-info btn-simple btn-link"-->
<!--                        @click="deleteActivity(activity, index)">-->
<!--                  <i class="fas fa-trash" aria-hidden="true"></i>-->
<!--                </button>-->
<!--              </td>-->
<!--            </tr>-->
<!--          </table>-->
<!--        </div>-->
<!--      </div>-->
<!--      <div class="card-footer text-left" v-if="!noUserFound">-->
<!--        <div class="row">-->
<!--          <div class="col-6">-->
<!--            <p  v-if="assessments.length === 0"> No assessments yet...</p>-->
<!--            <p  v-if="assessments.length > 0"> Assessments so far...</p>-->
<!--          </div>-->
<!--          <div class="col" align="right">-->
<!--            <button rel="tooltip" title="Add"-->
<!--                    class="btn btn-info btn-simple btn-link"-->
<!--                    @click="hideAssessmentForm=!hideAssessmentForm">-->
<!--              <i class="fa fa-plus" aria-hidden="true"></i>-->
<!--            </button>-->
<!--          </div>-->
<!--        </div>-->
<!--        <div class="card-footer" :class="{ 'd-none': hideAssessmentForm }">-->
<!--          <form id="addAssessment">-->
<!--            <div class="input-group mb-3">-->
<!--              <div class="input-group-prepend">-->
<!--                <span class="input-group-text" id="input-assessment-date">Date</span>-->
<!--              </div>-->
<!--              <input type="date" class="form-control" v-model="formData.assessmentDate" name="assessmentDate" placeholder="Date"/>-->
<!--            </div>-->
<!--            <div class="input-group mb-3">-->
<!--              <div class="input-group-prepend">-->
<!--                <span class="input-group-text" id="input-assessment-weight">Weight</span>-->
<!--              </div>-->
<!--              <input type="number" class="form-control" v-model="formData.weight" name="weight" placeholder="Weight"/>-->
<!--            </div>-->
<!--            <div class="input-group mb-3">-->
<!--              <div class="input-group-prepend">-->
<!--                <span class="input-group-text" id="input-assessment-chest">Chest</span>-->
<!--              </div>-->
<!--              <input type="number" class="form-control" v-model="formData.chest" name="chest" placeholder="Chest"/>-->
<!--            </div>-->
<!--            <div class="input-group mb-3">-->
<!--              <div class="input-group-prepend">-->
<!--                <span class="input-group-text" id="input-assessment-thigh">Thigh</span>-->
<!--              </div>-->
<!--              <input type="number" class="form-control" v-model="formData.thigh" name="thigh" placeholder="Thigh"/>-->
<!--            </div>-->
<!--            <div class="input-group mb-3">-->
<!--              <div class="input-group-prepend">-->
<!--                <span class="input-group-text" id="input-assessment-arm">Arm</span>-->
<!--              </div>-->
<!--              <input type="number" class="form-control" v-model="formData.arm" name="arm" placeholder="Arm"/>-->
<!--            </div>-->
<!--            <div class="input-group mb-3">-->
<!--              <div class="input-group-prepend">-->
<!--                <span class="input-group-text" id="input-assessment-waist">Waist</span>-->
<!--              </div>-->
<!--              <input type="number" class="form-control" v-model="formData.waist" name="waist" placeholder="Waist"/>-->
<!--            </div>-->
<!--            <div class="input-group mb-3">-->
<!--              <div class="input-group-prepend">-->
<!--                <span class="input-group-text" id="input-assessment-chest">Hips</span>-->
<!--              </div>-->
<!--              <input type="number" class="form-control" v-model="formData.hips" name="hips" placeholder="Hips"/>-->
<!--            </div>-->

<!--          </form>-->
<!--          <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link" @click="addAssessment()">Add Assessment</button>-->
<!--        </div>-->
<!--        <div v-if="assessments.length > 0">-->
<!--          <table class="table">-->
<!--            <thead>-->
<!--              <tr>-->
<!--                <th scope="col">Date</th>-->
<!--                <th scope="col">Weight</th>-->
<!--                <th scope="col">Chest</th>-->
<!--                <th scope="col">Thigh</th>-->
<!--                <th scope="col">Arm</th>-->
<!--                <th scope="col">Waist</th>-->
<!--                <th scope="col">Hips</th>-->
<!--                <th scope="col">BMI</th>-->
<!--                <th scope="col">Delete</th>-->

<!--              </tr>-->
<!--            </thead>-->
<!--            <tr v-for="assessment in assessments">-->
<!--              <td>{{formatDate(assessment.assessmentDate)}}</td>-->
<!--              <td>{{assessment.weight}}</td>-->
<!--              <td>{{assessment.chest}}</td>-->
<!--              <td>{{assessment.thigh}}</td>-->
<!--              <td>{{assessment.arm}}</td>-->
<!--              <td>{{assessment.waist}}</td>-->
<!--              <td>{{assessment.hips}}</td>-->
<!--              <td>{{calcBMI(assessment.weight, user.height)}}</td>-->
<!--              <td>-->
<!--                <button rel="tooltip" title="Delete"-->
<!--                        class="btn btn-info btn-simple btn-link"-->
<!--                        @click="deleteAssessment(assessment, index)">-->
<!--                  <i class="fas fa-trash" aria-hidden="true"></i>-->
<!--                </button>-->
<!--              </td>-->
<!--            </tr>-->
<!--          </table>-->
<!--        </div>-->
<!--      </div>-->
<!--      <div class="card-footer text-left" v-if="!noUserFound">-->
<!--        <div class="row">-->
<!--          <div class="col-6">-->
<!--            <p  v-if="sleeps.length === 0"> No sleep history yet...</p>-->
<!--            <p  v-if="sleeps.length > 0"> Sleep history so far...</p>-->
<!--          </div>-->
<!--          <div class="col" align="right">-->
<!--            <button rel="tooltip" title="Add"-->
<!--                    class="btn btn-info btn-simple btn-link"-->
<!--                    @click="hideSleepForm=!hideSleepForm">-->
<!--              <i class="fa fa-plus" aria-hidden="true"></i>-->
<!--            </button>-->
<!--          </div>-->
<!--        </div>-->
<!--        <div class="card-footer" :class="{ 'd-none': hideSleepForm }">-->
<!--          <form id="addSleep">-->
<!--            <div class="input-group mb-3">-->
<!--              <div class="input-group-prepend">-->
<!--                <span class="input-group-text" id="input-sleep-started">Date</span>-->
<!--              </div>-->
<!--              <input type="date" class="form-control" v-model="formData.started" name="started" placeholder="Date"/>-->
<!--            </div>-->
<!--            <div class="input-group mb-3">-->
<!--              <div class="input-group-prepend">-->
<!--                <span class="input-group-text" id="input-activity-duration">Duration</span>-->
<!--              </div>-->
<!--              <input type="number" class="form-control" v-model="formData.duration" name="duration" placeholder="Duration"/>-->
<!--            </div>-->
<!--          </form>-->
<!--          <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link" @click="addSleep()">Add Sleep Entry</button>-->
<!--        </div>-->
<!--        <div v-if="sleeps.length > 0">-->
<!--          <table class="table">-->
<!--            <thead>-->
<!--            <tr>-->
<!--              <th scope="col">Date</th>-->
<!--              <th scope="col">Duration</th>-->
<!--              <th scope="col">Delete</th>-->
<!--            </tr>-->
<!--            </thead>-->
<!--            <tr v-for="sleep in sleeps">-->
<!--              <td>{{formatDate(sleep.started)}}</td>-->
<!--              <td>{{sleep.duration}}</td>-->
<!--              <td>-->
<!--                <button rel="tooltip" title="Delete"-->
<!--                        class="btn btn-info btn-simple btn-link"-->
<!--                        @click="deleteSleep(sleep, index)">-->
<!--                  <i class="fas fa-trash" aria-hidden="true"></i>-->
<!--                </button>-->
<!--              </td>-->
<!--            </tr>-->
<!--          </table>-->
<!--        </div>-->
<!--      </div>-->
    </div>

    <card-content-layout>
      <card-header-layout>
          <div class="col-6">
            <p  v-if="assessments.length === 0"> No assessments yet...</p>
            <p  v-if="assessments.length > 0"> Assessments so far...</p>
          </div>
          <div class="col" align="right">
            <button rel="tooltip" title="Add"
                    class="btn btn-info btn-simple btn-link"
                    @click="hideAssessmentForm=!hideAssessmentForm">
              <i class="fa fa-plus" aria-hidden="true"></i>
            </button>
          </div>
      </card-header-layout>
      <card-body-layout>
        <div :class="{ 'd-none': hideAssessmentForm }">
          <form id="addAssessment">
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <span class="input-group-text" id="input-assessment-date">Date</span>
              </div>
              <input type="date" class="form-control" v-model="formData.assessmentDate" name="assessmentDate" placeholder="Date"/>
            </div>
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <span class="input-group-text" id="input-assessment-weight">Weight</span>
              </div>
              <input type="number" class="form-control" v-model="formData.weight" name="weight" placeholder="Weight"/>
            </div>
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <span class="input-group-text" id="input-assessment-chest">Chest</span>
              </div>
              <input type="number" class="form-control" v-model="formData.chest" name="chest" placeholder="Chest"/>
            </div>
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <span class="input-group-text" id="input-assessment-thigh">Thigh</span>
              </div>
              <input type="number" class="form-control" v-model="formData.thigh" name="thigh" placeholder="Thigh"/>
            </div>
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <span class="input-group-text" id="input-assessment-arm">Arm</span>
              </div>
              <input type="number" class="form-control" v-model="formData.arm" name="arm" placeholder="Arm"/>
            </div>
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <span class="input-group-text" id="input-assessment-waist">Waist</span>
              </div>
              <input type="number" class="form-control" v-model="formData.waist" name="waist" placeholder="Waist"/>
            </div>
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <span class="input-group-text" id="input-assessment-chest">Hips</span>
              </div>
              <input type="number" class="form-control" v-model="formData.hips" name="hips" placeholder="Hips"/>
            </div>

          </form>
          <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link" @click="addAssessment()">Add Assessment</button>
        </div>
        <div v-if="assessments.length > 0">
          <table class="table">
            <thead>
            <tr>
              <th scope="col">Date</th>
              <th scope="col">Weight</th>
              <th scope="col">Chest</th>
              <th scope="col">Thigh</th>
              <th scope="col">Arm</th>
              <th scope="col">Waist</th>
              <th scope="col">Hips</th>
              <th scope="col">BMI</th>
              <th scope="col">Delete</th>

            </tr>
            </thead>
            <tr v-for="assessment in assessments">
              <td>{{formatDate(assessment.assessmentDate)}}</td>
              <td>{{assessment.weight}}</td>
              <td>{{assessment.chest}}</td>
              <td>{{assessment.thigh}}</td>
              <td>{{assessment.arm}}</td>
              <td>{{assessment.waist}}</td>
              <td>{{assessment.hips}}</td>
              <td>{{calcBMI(assessment.weight, user.height)}}</td>
              <td>
                <button rel="tooltip" title="Delete"
                        class="btn btn-info btn-simple btn-link"
                        @click="deleteAssessment(assessment, index)">
                  <i class="fas fa-trash" aria-hidden="true"></i>
                </button>
              </td>
            </tr>
          </table>
        </div>
      </card-body-layout>
    </card-content-layout>
    <card-content-layout>
      <card-header-layout>
          <div class="col-6">
            <p  v-if="activities.length === 0"> No activities yet...</p>
            <p  v-if="activities.length > 0"> Activities so far...</p>
          </div>
          <div class="col" align="right">
            <button rel="tooltip" title="Add"
                    class="btn btn-info btn-simple btn-link"
                    @click="hideActivityForm=!hideActivityForm">
              <i class="fa fa-plus" aria-hidden="true"></i>
            </button>
          </div>
      </card-header-layout>
      <card-body-layout>
        <div :class="{ 'd-none': hideActivityForm }">
          <form id="addActivity">
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <span class="input-group-text" id="input-activity-type">Type</span>
              </div>
              <!--              <input type="number" class="form-control" v-model="formData.activityType" name="activityType" placeholder="Type"/>-->
              <select class="form-control" v-model="formData.activityType" id="select-activity-type">
                <option disabled value="">Please select one</option>
                <option :value=exercise.id v-for="exercise in exercises">{{exercise.name}}</option>
              </select>
            </div>
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <span class="input-group-text" id="input-activity-duration">Duration</span>
              </div>
              <input type="number" class="form-control" v-model="formData.duration" name="duration" placeholder="Duration"/>
            </div>
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <span class="input-group-text" id="input-activity-calories">Duration</span>
              </div>
              <input type="number" class="form-control" v-model="formData.calories" name="calories" placeholder="Calories"/>
            </div>
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <span class="input-group-text" id="input-activity-started">Date</span>
              </div>
              <input type="date" class="form-control" v-model="formData.started" name="started" placeholder="Date"/>
            </div>
          </form>
          <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link" @click="addActivity()">Add Activity</button>
        </div>
        <div v-if="activities.length > 0">
          <table class="table">
            <thead>
            <tr>
              <th scope="col">Date</th>
              <th scope="col">Type</th>
              <th scope="col">Duration</th>
              <th scope="col">Calories</th>
              <th scope="col">Delete</th>
            </tr>
            </thead>
            <tr v-for="activity in activities">
              <td>{{formatDate(activity.started)}}</td>
              <td>{{getExercise(activity.activityType)}}</td>
              <td>{{activity.duration}}</td>
              <td>{{activity.calories}}</td>
              <td>
                <button rel="tooltip" title="Delete"
                        class="btn btn-info btn-simple btn-link"
                        @click="deleteActivity(activity, index)">
                  <i class="fas fa-trash" aria-hidden="true"></i>
                </button>
              </td>
            </tr>
          </table>
        </div>
      </card-body-layout>
    </card-content-layout>
    <card-content-layout>
      <card-header-layout>
        <div class="col-6">
          <p  v-if="sleeps.length === 0"> No sleep history yet...</p>
          <p  v-if="sleeps.length > 0"> Sleep history so far...</p>
        </div>
        <div class="col" align="right">
          <button rel="tooltip" title="Add"
                  class="btn btn-info btn-simple btn-link"
                  @click="hideSleepForm=!hideSleepForm">
            <i class="fa fa-plus" aria-hidden="true"></i>
          </button>
        </div>
      </card-header-layout>
      <card-body-layout>
        <div :class="{ 'd-none': hideSleepForm }">
          <form id="addSleep">
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <span class="input-group-text" id="input-sleep-started">Date</span>
              </div>
              <input type="date" class="form-control" v-model="formData.started" name="started" placeholder="Date"/>
            </div>
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <span class="input-group-text" id="input-activity-duration">Duration</span>
              </div>
              <input type="number" class="form-control" v-model="formData.duration" name="duration" placeholder="Duration"/>
            </div>
          </form>
          <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link" @click="addSleep()">Add Sleep Entry</button>
        </div>
        <div v-if="sleeps.length > 0">
          <table class="table">
            <thead>
            <tr>
              <th scope="col">Date</th>
              <th scope="col">Duration</th>
              <th scope="col">Delete</th>
            </tr>
            </thead>
            <tr v-for="sleep in sleeps">
              <td>{{formatDate(sleep.started)}}</td>
              <td>{{sleep.duration}}</td>
              <td>
                <button rel="tooltip" title="Delete"
                        class="btn btn-info btn-simple btn-link"
                        @click="deleteSleep(sleep, index)">
                  <i class="fas fa-trash" aria-hidden="true"></i>
                </button>
              </td>
            </tr>
          </table>
        </div>
      </card-body-layout>
    </card-content-layout>

  </app-layout>
</template>

<script>
app.component("user-profile", {
  template: "#user-profile",
  data: () => ({
    user: null,
    noUserFound: false,
    activities: [],
    exercises: [],
    exercise: null,
    assessments: [],
    sleeps: [],
    hideActivityForm: true,
    hideAssessmentForm: true,
    hideSleepForm: true,
    formData: [],
  }),
  created: function () {
    const userId = this.$javalin.pathParams["user-id"];
    if (window.sessionStorage.getItem('accType') === 'member' && userId !== window.sessionStorage.getItem('id')) {
      window.location.href = '/'
      window.sessionStorage.removeItem('accType')
      window.sessionStorage.removeItem('id')
      alert('Only the logged in profile can be viewed. Logging you out!')
    }
    const url = `/api/users/${userId}`
    const url2 = '/api/exercises'
    axios.get(url)
        .then(res => this.user = res.data)
        .catch(error => {
          console.log("No user found for id passed in the path parameter: " + error)
          this.noUserFound = true
        });
    axios.get(url + `/activities`)
        .then(res => {
          if (Array.isArray(res.data))
            this.activities = res.data
        })
        .catch(error => {
          console.log("No activities added yet (this is ok): " + error)
        })
    axios.get(url + `/assessments`)
        .then(res => {
          if (Array.isArray(res.data))
            this.assessments = res.data
        })
        .catch(error => {
          console.log("No assessments added yet (this is ok): " + error)
        })
    axios.get(url + `/sleeps`)
        .then(res => {
          if (Array.isArray(res.data))
            this.sleeps = res.data
        })
        .catch(error => {
          console.log("No sleeps added yet (this is ok): " + error)
        })
    axios.get(url2)
        .then(res => this.exercises = res.data)
        .catch(error => {
          console.log("No exercises listed: " + error)
        })
  },
  methods: {
    updateUser: function () {
      const userId = this.$javalin.pathParams["user-id"];
      const url = `/api/users/${userId}`
      axios.patch(url,
          {
            name: this.user.name,
            email: this.user.email,
            password: this.user.password,
            gender: this.user.gender,
            height: this.user.height,
            startWeight: this.user.startWeight,
            trainerId: this.user.trainerId,
            planId: this.user.planId
          })
          .then(response =>
              this.user.push(response.data))
          .catch(error => {
            console.log(error)
          })
      alert("User updated!")
    },
    deleteUser: function () {
      if (confirm("Do you really want to delete?")) {
        const userId = this.$javalin.pathParams["user-id"];
        const url = `/api/users/${userId}`
        axios.delete(url)
            .then(response => {
              alert("User deleted")
              //display the /users endpoint
              window.location.href = '/users';
            })
            .catch(function (error) {
              console.log(error)
            });
      }
    },
    deleteAssessment: function (assessment, index) {
      if (confirm('Are you sure you want to delete this assessment? This action cannot be undone.', 'Warning')) {
        const assessmentId = assessment.id;
        const url = `/api/assessments/${assessmentId}`;
        axios.delete(url)
            .then(response =>
                //delete from the local state so Vue will reload list automatically
                this.assessments.splice(index, 1).push(response.data))
            .catch(function (error) {
              console.log(error)
            });
      }
    },
    deleteActivity: function (activity, index) {
      if (confirm('Are you sure you want to delete this activity? This action cannot be undone.', 'Warning')) {
        const activityId = activity.id;
        const url = `/api/activities/${activityId}`;
        axios.delete(url)
            .then(response =>
                //delete from the local state so Vue will reload list automatically
                this.activities.splice(index, 1).push(response.data))
            .catch(function (error) {
              console.log(error)
            });
      }
    },
    deleteSleep: function (sleep, index) {
      if (confirm('Are you sure you want to delete this sleep entry? This action cannot be undone.', 'Warning')) {
        const sleepId = sleep.id;
        const url = `/api/sleeps/${sleepId}`;
        axios.delete(url)
            .then(response =>
                //delete from the local state so Vue will reload list automatically
                this.sleeps.splice(index, 1).push(response.data))
            .catch(function (error) {
              console.log(error)
            });
      }
    },
    getExercise: function(id) {
      return this.exercises.filter((exercise) => exercise.id === id)[0].name
    },
    formatDate: function(date) {
      newDate = date.substring(0, date.indexOf('T'))
      return newDate
    },
    calcBMI: function(weight, height) {
      return ( 10000 * weight / (height * height)).toFixed(2)
    },
    addActivity: function (){
      const url = `/api/activities`;
      axios.post(url,
          {
            activityType: this.formData.activityType,
            duration: this.formData.duration,
            calories: this.formData.calories,
            started: this.formData.started,
            userId: this.$javalin.pathParams["user-id"]
          })
          .then(response => {
            this.activities.push(response.data)
            this.hideActivityForm= true;
          })
          .catch(error => {
            console.log(error)
          })
    },
    addAssessment: function (){
      const url = `/api/assessments`;
      axios.post(url,
          {
            assessmentDate: this.formData.assessmentDate,
            weight: this.formData.weight,
            chest: this.formData.chest,
            thigh: this.formData.thigh,
            arm: this.formData.arm,
            waist: this.formData.waist,
            hips: this.formData.hips,
            userId: this.$javalin.pathParams["user-id"]
          })
          .then(response => {
            this.assessments.push(response.data)
            this.hideAssessmentForm= true;
          })
          .catch(error => {
            console.log(error)
          })
    },
    addSleep: function (){
      const url = `/api/sleeps`;
      axios.post(url,
          {
            duration: this.formData.duration,
            started: this.formData.started,
            userId: this.$javalin.pathParams["user-id"]
          })
          .then(response => {
            this.sleeps.push(response.data)
            this.hideActivityForm= true;
          })
          .catch(error => {
            console.log(error)
          })
    },
  },

});
</script>