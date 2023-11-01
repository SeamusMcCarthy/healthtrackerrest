package ie.setu.controllers

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ie.setu.domain.*
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

    fun getUsersByTrainerId(ctx: Context) {
        if (trainerDao.findById(ctx.pathParam("trainer-id").toInt()) != null) {
            val users = userDao.findByTrainerID(ctx.pathParam("trainer-id").toInt())
            if (users.isNotEmpty()) {
                ctx.json(users)
            }
        }
    }

    fun getUsersByPlanId(ctx: Context) {
        if (planDao.findById(ctx.pathParam("plan-id").toInt()) != null) {
            val users = userDao.findByPlanId(ctx.pathParam("plan-id").toInt())
            if (users.isNotEmpty()) {
                ctx.json(users)
            }
        }
    }

    fun addUser(ctx: Context) {
        val mapper = jacksonObjectMapper()
        val user = mapper.readValue<User>(ctx.body())
        userDao.save(user)
        ctx.json(user)
    }

    fun addTrainer(ctx: Context) {
        val mapper = jacksonObjectMapper()
        val trainer = mapper.readValue<Trainer>(ctx.body())
        trainerDao.save(trainer)
        ctx.json(trainer)
    }

    fun addPlan(ctx: Context) {
        val mapper = jacksonObjectMapper()
        val plan = mapper.readValue<Plan>(ctx.body())
        planDao.save(plan)
        ctx.json(plan)
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

    fun deleteTrainer(ctx: Context){
        trainerDao.delete(ctx.pathParam("trainer-id").toInt())
    }

    fun deleteSleep(ctx: Context){
        sleepDao.delete(ctx.pathParam("sleep-id").toInt())
    }

    fun deleteActivity(ctx: Context){
        activityDao.delete(ctx.pathParam("activity-id").toInt())
    }

    fun deleteAssessment(ctx: Context){
        assessmentDao.delete(ctx.pathParam("assessment-id").toInt())
    }

    fun deleteSchedule(ctx: Context){
        scheduleDao.delete(ctx.pathParam("schedule-id").toInt())
    }

    fun deletePlan(ctx: Context){
        planDao.delete(ctx.pathParam("plan-id").toInt())
    }

    fun updateUser(ctx: Context){
        val mapper = jacksonObjectMapper()
        val userUpdates = mapper.readValue<User>(ctx.body())
        userDao.update(
            id = ctx.pathParam("user-id").toInt(),
            user=userUpdates)
    }

    fun updateTrainer(ctx: Context){
        val mapper = jacksonObjectMapper()
        val trainerUpdates = mapper.readValue<Trainer>(ctx.body())
        trainerDao.update(
            id = ctx.pathParam("trainer-id").toInt(),
            trainer=trainerUpdates)
    }

    fun updatePlan(ctx: Context){
        val mapper = jacksonObjectMapper()
        val planUpdates = mapper.readValue<Plan>(ctx.body())
        planDao.update(
            id = ctx.pathParam("plan-id").toInt(),
            plan=planUpdates)
    }

    fun updateSchedule(ctx: Context){
        val mapper = jacksonObjectMapper()
        val scheduleUpdates = mapper.readValue<Schedule>(ctx.body())
        scheduleDao.update(
            id = ctx.pathParam("schedule-id").toInt(),
            schedule=scheduleUpdates)
    }

    fun updateSleep(ctx: Context){
        val mapper = jacksonObjectMapper()
        val sleepUpdates = mapper.readValue<Sleep>(ctx.body())
        sleepDao.update(
            id = ctx.pathParam("sleep-id").toInt(),
            sleep=sleepUpdates)
    }

    fun updateActivity(ctx: Context){
        val mapper = jacksonObjectMapper()
        val activityUpdates = mapper.readValue<Activity>(ctx.body())
        activityDao.update(
            id = ctx.pathParam("activity-id").toInt(),
            activity=activityUpdates)
    }

    fun updateAssessment(ctx: Context){
        val mapper = jacksonObjectMapper()
        val assessmentUpdates = mapper.readValue<Assessment>(ctx.body())
        assessmentDao.update(
            id = ctx.pathParam("assessment-id").toInt(),
            assessment=assessmentUpdates)
    }

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

    fun getAssessmentsByUserId(ctx: Context) {
        if (userDao.findById(ctx.pathParam("user-id").toInt()) != null) {
            val assessments = assessmentDao.findByUserId(ctx.pathParam("user-id").toInt())
            if (assessments.isNotEmpty()) {
                //mapper handles the deserialization of Joda date into a String.
                val mapper = jacksonObjectMapper()
                    .registerModule(JodaModule())
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                ctx.json(mapper.writeValueAsString(assessments))
            }
        }
    }

    fun getActivityByActivityId(ctx: Context) {
        val activity = activityDao.findByActivityId(ctx.pathParam("activity-id").toInt())
        if (activity != null) {
            ctx.json(activity)
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

    fun addAssessment(ctx: Context) {
        //mapper handles the serialisation of Joda date into a String.
        val mapper = jacksonObjectMapper()
            .registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        val assessment = mapper.readValue<Assessment>(ctx.body())
        assessmentDao.save(assessment)
        ctx.json(assessment)
    }

    fun addSleep(ctx: Context) {
        //mapper handles the serialisation of Joda date into a String.
        val mapper = jacksonObjectMapper()
            .registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        val sleep = mapper.readValue<Sleep>(ctx.body())
        sleepDao.save(sleep)
        ctx.json(sleep)
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

    fun getSleepsByUserId(ctx: Context) {
        if (userDao.findById(ctx.pathParam("user-id").toInt()) != null) {
            val sleeps = sleepDao.findByUserId(ctx.pathParam("user-id").toInt())
            if (sleeps.isNotEmpty()) {
                //mapper handles the deserialization of Joda date into a String.
                val mapper = jacksonObjectMapper()
                    .registerModule(JodaModule())
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                ctx.json(mapper.writeValueAsString(sleeps))
            }
        }
    }

    fun addSchedule(ctx: Context) {
        val mapper = jacksonObjectMapper()
        val schedule = mapper.readValue<Schedule>(ctx.body())
        scheduleDao.save(schedule)
        ctx.json(schedule)
    }
}