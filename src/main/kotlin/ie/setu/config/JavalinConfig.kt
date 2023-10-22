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
                }
                path("/email/{email}"){
                    get(HealthTrackerController::getUserByEmail)
                }
            }
            path("/api/activities") {
                get(HealthTrackerController::getAllActivities)
                post(HealthTrackerController::addActivity)
            }
            path("/api/plans") {
                get(HealthTrackerController::getAllPlans)
                path("{plan-id}"){
                    get(HealthTrackerController::getPlanByPlanId)
                }
            }
            path("/api/schedules") {
                get(HealthTrackerController::getAllSchedules)
                path("{schedule-id}"){
                    get(HealthTrackerController::getScheduleByScheduleId)
                }
            }
            path("/api/trainers") {
                get(HealthTrackerController::getAllTrainers)
                path("{trainer-id}"){
                    get(HealthTrackerController::getTrainerByTrainerId)
                }
            }
        }
    }
}