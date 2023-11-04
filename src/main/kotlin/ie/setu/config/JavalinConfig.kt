package ie.setu.config

import ie.setu.controllers.AccountController
import ie.setu.controllers.HealthTrackerController
import ie.setu.controllers.StaticDataController
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*

class JavalinConfig {

    fun startJavalinService(): Javalin {

        val app = Javalin.create().apply {
            exception(Exception::class.java) { e, _ -> e.printStackTrace() }
            error(404) { ctx -> ctx.json("404 - Not Found") }
        }.start(getRemoteAssignedPort())

        registerRoutes(app)
        return app
    }

    private fun getRemoteAssignedPort(): Int {
        val remotePort = System.getenv("PORT")
        return if (remotePort != null) {
            Integer.parseInt(remotePort)
        } else 7000
    }

    private fun registerRoutes(app: Javalin) {
        app.routes {
            path("/api/users") {
                get(AccountController::getAllUsers)
                post(AccountController::addUser)
                path("{user-id}"){
                    get(AccountController::getUserByUserId)
                    delete(AccountController::deleteUser)
                    patch(AccountController::updateUser)
                    path("activities"){
                        get(HealthTrackerController::getActivitiesByUserId)
                    }
                    path("assessments"){
                        get(HealthTrackerController::getAssessmentsByUserId)
                    }
                    path("sleeps"){
                        get(HealthTrackerController::getSleepsByUserId)
                    }
                }
                path("/email/{email}"){
                    get(AccountController::getUserByEmail)
                }
            }
            path("/api/activities") {
                get(HealthTrackerController::getAllActivities)
                post(HealthTrackerController::addActivity)
                path("{activity-id}"){
                    get(HealthTrackerController::getActivityByActivityId)
                    delete(HealthTrackerController::deleteActivity)
                    patch(HealthTrackerController::updateActivity)
                }
                path("/type/{exercise-id}") {
                    get(HealthTrackerController::getActivitiesByActivityType)
                }
            }
            path("/api/assessments") {
                get(HealthTrackerController::getAllAssessments)
                post(HealthTrackerController::addAssessment)
                path("{assessment-id}"){
                    get(HealthTrackerController::getAssessmentByAssessmentId)
                    delete(HealthTrackerController::deleteAssessment)
                    patch(HealthTrackerController::updateAssessment)
                }
            }
            path("/api/plans") {
                get(StaticDataController::getAllPlans)
                post(StaticDataController::addPlan)
                path("{plan-id}"){
                    get(StaticDataController::getPlanByPlanId)
                    delete(StaticDataController::deletePlan)
                    patch(StaticDataController::updatePlan)
                    path("users"){
                        get(AccountController::getUsersByPlanId)
                    }
                }
            }
            path("/api/trainers") {
                get(AccountController::getAllTrainers)
                post(AccountController::addTrainer)
                path("{trainer-id}"){
                    get(AccountController::getTrainerByTrainerId)
                    delete(AccountController::deleteTrainer)
                    patch(AccountController::updateTrainer)
                    path("users"){
                        get(AccountController::getUsersByTrainerId)
                    }
                }
                path("/email/{email}"){
                    get(AccountController::getTrainerByEmail)
                }

            }
            path("/api/sleeps") {
                get(HealthTrackerController::getAllSleeps)
                post(HealthTrackerController::addSleep)
                path("{sleep-id}"){
                    get(HealthTrackerController::getSleepBySleepId)
                    delete(HealthTrackerController::deleteSleep)
                    patch(HealthTrackerController::updateSleep)
                }
            }

            path("/api/exercises") {
                get(StaticDataController::getAllExercises)
                post(StaticDataController::addExercise)
                path("{exercise-id}"){
                    get(StaticDataController::getExerciseByExerciseId)
                    delete(StaticDataController::deleteExercise)
                    patch(StaticDataController::updateExercise)
                }
            }
        }
    }
}