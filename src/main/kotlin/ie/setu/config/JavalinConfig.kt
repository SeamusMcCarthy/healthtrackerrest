package ie.setu.config

import ie.setu.controllers.HealthTrackerController
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
                get(HealthTrackerController::getAllUsers)
                post(HealthTrackerController::addUser)
                path("{user-id}"){
                    get(HealthTrackerController::getUserByUserId)
                    delete(HealthTrackerController::deleteUser)
                    patch(HealthTrackerController::updateUser)
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
                path("/plans/{plan_id}"){
                    get(HealthTrackerController::getUsersByPlanId)
                }
                path("/email/{email}"){
                    get(HealthTrackerController::getUserByEmail)
                }
                path("/trainer/{trainer_id}"){
                    get(HealthTrackerController::getUsersByTrainerId)
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
            }
            path("/api/assessments") {
                get(HealthTrackerController::getAllAssessments)
                post(HealthTrackerController::addAssessment)
                path("{activity-id}"){
                    get(HealthTrackerController::getAssessmentByAssessmentId)
                    delete(HealthTrackerController::deleteAssessment)
                    patch(HealthTrackerController::updateAssessment)

                }
            }
            path("/api/plans") {
                get(HealthTrackerController::getAllPlans)
                post(HealthTrackerController::addPlan)
                path("{plan-id}"){
                    get(HealthTrackerController::getPlanByPlanId)
                    delete(HealthTrackerController::deletePlan)
                    patch(HealthTrackerController::updatePlan)
                }
            }
            path("/api/schedules") {
                get(HealthTrackerController::getAllSchedules)
                post(HealthTrackerController::addSchedule)
                path("{schedule-id}"){
                    get(HealthTrackerController::getScheduleByScheduleId)
                    delete(HealthTrackerController::deleteSchedule)
                    patch(HealthTrackerController::updateSchedule)
                }
            }
            path("/api/trainers") {
                get(HealthTrackerController::getAllTrainers)
                post(HealthTrackerController::addTrainer)
                path("{trainer-id}"){
                    get(HealthTrackerController::getTrainerByTrainerId)
                    delete(HealthTrackerController::deleteTrainer)
                    patch(HealthTrackerController::updateTrainer)
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
        }
    }
}