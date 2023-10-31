package ie.setu.controllers

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ie.setu.domain.Activity
import ie.setu.domain.User
import ie.setu.domain.repository.*
import io.javalin.http.Context

object HealthTrackerController {

    private val userDao = UserDAO()
    private val activityDao = ActivityDAO()
    private val planDao = PlanDAO()
    private val scheduleDao = ScheduleDAO()
    private val trainerDao = TrainerDAO()
    private val assessmentDao = AssessmentDAO()
    private val sleepDao = SleepDAO()

    fun getAllUsers(ctx: Context) {
        ctx.json(userDao.getAll())
    }

    fun getUserByUserId(ctx: Context) {
        val user = userDao.findById(ctx.pathParam("user-id").toInt())
        if (user != null) {
            ctx.json(user)
        }
    }

    fun addUser(ctx: Context) {
        val mapper = jacksonObjectMapper()
        val user = mapper.readValue<User>(ctx.body())
        userDao.save(user)
        ctx.json(user)
    }

    fun getUserByEmail(ctx: Context) {
        val user = userDao.findByEmail(ctx.pathParam("email"))
        if (user != null) {
            ctx.json(user)
        }
    }

    fun deleteUser(ctx: Context){
        userDao.delete(ctx.pathParam("user-id").toInt())
    }

    fun updateUser(ctx: Context){
        val mapper = jacksonObjectMapper()
        val userUpdates = mapper.readValue<User>(ctx.body())
        userDao.update(
            id = ctx.pathParam("user-id").toInt(),
            user=userUpdates)
    }

    //--------------------------------------------------------------
    // ActivityDAO specifics
    //-------------------------------------------------------------

    fun getAllActivities(ctx: Context) {
        //mapper handles the deserialization of Joda date into a String.
        val mapper = jacksonObjectMapper()
            .registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        ctx.json(mapper.writeValueAsString( activityDao.getAll() ))
    }

    fun getActivitiesByUserId(ctx: Context) {
        if (userDao.findById(ctx.pathParam("user-id").toInt()) != null) {
            val activities = activityDao.findByUserId(ctx.pathParam("user-id").toInt())
            if (activities.isNotEmpty()) {
                //mapper handles the deserialization of Joda date into a String.
                val mapper = jacksonObjectMapper()
                    .registerModule(JodaModule())
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                ctx.json(mapper.writeValueAsString(activities))
            }
        }
    }

    fun addActivity(ctx: Context) {
        //mapper handles the serialisation of Joda date into a String.
        val mapper = jacksonObjectMapper()
            .registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        val activity = mapper.readValue<Activity>(ctx.body())
        activityDao.save(activity)
        ctx.json(activity)
    }

    fun getAllPlans(ctx: Context) {
        ctx.json(planDao.getAll())
    }

    fun getPlanByPlanId(ctx: Context) {
        val plan = planDao.findById(ctx.pathParam("plan-id").toInt())
        if (plan != null) {
            ctx.json(plan)
        }
    }

    fun getAllSchedules(ctx: Context) {
        ctx.json(scheduleDao.getAll())
    }

    fun getScheduleByScheduleId(ctx: Context) {
        val schedule = scheduleDao.findById(ctx.pathParam("schedule-id").toInt())
        if (schedule != null) {
            ctx.json(schedule)
        }
    }

    fun getAllTrainers(ctx: Context) {
        ctx.json(trainerDao.getAll())
    }

    fun getTrainerByTrainerId(ctx: Context) {
        val trainer = trainerDao.findById(ctx.pathParam("trainer-id").toInt())
        if (trainer != null) {
            ctx.json(trainer)
        }
    }

    fun getAllAssessments(ctx: Context) {
        ctx.json(assessmentDao.getAll())
    }

    fun getAssessmentByAssessmentId(ctx: Context) {
        val assessment = assessmentDao.findById(ctx.pathParam("assessment-id").toInt())
        if (assessment != null) {
            ctx.json(assessment)
        }
    }

    fun getAllSleeps(ctx: Context) {
        ctx.json(sleepDao.getAll())
    }

    fun getSleepBySleepId(ctx: Context) {
        val sleep = sleepDao.findBySleepId(ctx.pathParam("sleep-id").toInt())
        if (sleep != null) {
            ctx.json(sleep)
        }
    }
}