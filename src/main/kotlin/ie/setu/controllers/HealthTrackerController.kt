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
    private val assessmentDao = AssessmentDAO()
    private val sleepDao = SleepDAO()
    private val exerciseDao = ExerciseDAO()

    fun getActivitiesByActivityType(ctx: Context) {
        if (exerciseDao.findById(ctx.pathParam("exercise-id").toInt()) != null) {
            val activities = activityDao.findByActivityType(ctx.pathParam("exercise-id").toInt())
            val mapper = jacksonObjectMapper()
                .registerModule(JodaModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            if (activities.isNotEmpty()) {
                ctx.json(mapper.writeValueAsString(activities))
            }
        }
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
        val mapper = jacksonObjectMapper()
            .registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        if (activity != null) {
            ctx.json(mapper.writeValueAsString(activity))
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





    fun getAllAssessments(ctx: Context) {
        //mapper handles the deserialization of Joda date into a String.
        val mapper = jacksonObjectMapper()
            .registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        ctx.json(mapper.writeValueAsString( assessmentDao.getAll() ))
    }

    fun getAssessmentByAssessmentId(ctx: Context) {
        val assessment = assessmentDao.findById(ctx.pathParam("assessment-id").toInt())
        val mapper = jacksonObjectMapper()
            .registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        if (assessment != null) {
            ctx.json(mapper.writeValueAsString(assessment))
        }
    }

    fun getAllSleeps(ctx: Context) {
        //mapper handles the deserialization of Joda date into a String.
        val mapper = jacksonObjectMapper()
            .registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        ctx.json(mapper.writeValueAsString( sleepDao.getAll() ))
    }

    fun getSleepBySleepId(ctx: Context) {
        val sleep = sleepDao.findBySleepId(ctx.pathParam("sleep-id").toInt())
        val mapper = jacksonObjectMapper()
            .registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        if (sleep != null) {
            ctx.json(mapper.writeValueAsString(sleep))
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


}