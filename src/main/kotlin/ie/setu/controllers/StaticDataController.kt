package ie.setu.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ie.setu.domain.Exercise
import ie.setu.domain.Plan
import ie.setu.domain.repository.ExerciseDAO
import ie.setu.domain.repository.PlanDAO
import io.javalin.http.Context

object StaticDataController {

    private val planDao = PlanDAO()
    private val exerciseDao = ExerciseDAO()


    // Functions for plan static data
    fun getAllPlans(ctx: Context) {
        val plans = planDao.getAll()
        if (plans.size != 0) {
            ctx.status(200)
            ctx.json(plans)
        } else {
            ctx.status(404)
        }
    }

    fun getPlanByPlanId(ctx: Context) {
        val plan = planDao.findById(ctx.pathParam("plan-id").toInt())
        if (plan != null) {
            ctx.json(plan)
            ctx.status(200)
        } else {
            ctx.status(404)
        }
    }
    fun addPlan(ctx: Context) {
        val mapper = jacksonObjectMapper()
        val plan = mapper.readValue<Plan>(ctx.body())
        val planId = planDao.save(plan)
        if (planId != null) {
            plan.id = planId
            ctx.json(plan)
            ctx.status(201)
        }
    }

    fun deletePlan(ctx: Context){
        if (planDao.delete(ctx.pathParam("plan-id").toInt()) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }

    fun updatePlan(ctx: Context){
        val mapper = jacksonObjectMapper()
        val planUpdates = mapper.readValue<Plan>(ctx.body())
//        val planUpdates : Plan = jsonToObject(ctx.body())
        if ((planDao.update(id = ctx.pathParam("plan-id").toInt(), plan=planUpdates)) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }


    // Functions for exercise static data

    fun getAllExercises(ctx: Context) {
        val exercises = exerciseDao.getAll()
        if (exercises.size != 0) {
            ctx.status(200)
            ctx.json(exercises)
        } else {
            ctx.status(404)
        }
    }

    fun getExerciseByExerciseId(ctx: Context) {
        val exercise = exerciseDao.findById(ctx.pathParam("exercise-id").toInt())
        if (exercise != null) {
            ctx.json(exercise)
            ctx.status(200)
        } else {
            ctx.status(404)
        }
    }

    fun addExercise(ctx: Context) {
        val mapper = jacksonObjectMapper()
        val exercise = mapper.readValue<Exercise>(ctx.body())
        val exerciseId = exerciseDao.save(exercise)
        if (exerciseId != null) {
            exercise.id = exerciseId
            ctx.json(exercise)
            ctx.status(201)
        }
    }

    fun deleteExercise(ctx: Context){
        if (exerciseDao.delete(ctx.pathParam("exercise-id").toInt()) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }

    fun updateExercise(ctx: Context){
        val mapper = jacksonObjectMapper()
        val exerciseUpdates = mapper.readValue<Exercise>(ctx.body())

        if ((exerciseDao.update(id = ctx.pathParam("exercise-id").toInt(), exercise=exerciseUpdates)) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }
}