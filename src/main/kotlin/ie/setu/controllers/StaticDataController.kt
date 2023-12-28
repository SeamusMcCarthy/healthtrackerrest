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
        ctx.json(planDao.getAll())
    }

    fun getPlanByPlanId(ctx: Context) {
        val plan = planDao.findById(ctx.pathParam("plan-id").toInt())
        if (plan != null) {
            ctx.json(plan)
        }
    }
    fun addPlan(ctx: Context) {
        val mapper = jacksonObjectMapper()
        val plan = mapper.readValue<Plan>(ctx.body())
        val planId = planDao.save(plan)
        if (planId != null) {
            plan.id = planId
            ctx.json(plan)
        }

    }

    fun deletePlan(ctx: Context){
        planDao.delete(ctx.pathParam("plan-id").toInt())
    }

    fun updatePlan(ctx: Context){
        val mapper = jacksonObjectMapper()
        val planUpdates = mapper.readValue<Plan>(ctx.body())
        planDao.update(
            id = ctx.pathParam("plan-id").toInt(),
            plan=planUpdates)
    }


    // Functions for exercise static data

    fun getAllExercises(ctx: Context) {
        ctx.json(exerciseDao.getAll())
    }

    fun getExerciseByExerciseId(ctx: Context) {
        val exercise = exerciseDao.findById(ctx.pathParam("exercise-id").toInt())
        if (exercise != null) {
            ctx.json(exercise)
        }
    }

    fun addExercise(ctx: Context) {
        val mapper = jacksonObjectMapper()
        val exercise = mapper.readValue<Exercise>(ctx.body())
        exerciseDao.save(exercise)
        ctx.json(exercise)
    }

    fun deleteExercise(ctx: Context){
        exerciseDao.delete(ctx.pathParam("exercise-id").toInt())
    }

    fun updateExercise(ctx: Context){
        val mapper = jacksonObjectMapper()
        val exerciseUpdates = mapper.readValue<Exercise>(ctx.body())
        exerciseDao.update(
            id = ctx.pathParam("exercise-id").toInt(),
            exercise=exerciseUpdates)
    }
}