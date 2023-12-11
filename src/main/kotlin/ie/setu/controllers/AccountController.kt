package ie.setu.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ie.setu.domain.*
import ie.setu.domain.repository.*
import ie.setu.utils.jsonToObject
import io.javalin.http.Context

object AccountController {
    private val userDao = UserDAO()
    private val trainerDao = TrainerDAO()
    private val planDao = PlanDAO()


    // Functions for handling user account data
    fun getAllUsers(ctx: Context) {
        val users = userDao.getAll()
        if (users.size != 0) {
            ctx.status(200)
            ctx.json(users)
        } else {
            ctx.status(404)
        }
    }

    fun getUserByUserId(ctx: Context) {
        val user = userDao.findById(ctx.pathParam("user-id").toInt())
        if (user != null) {
            ctx.json(user)
            ctx.status(200)
        } else {
            ctx.status(404)
        }
    }
    fun getUsersByTrainerId(ctx: Context) {
        if (trainerDao.findById(ctx.pathParam("trainer-id").toInt()) != null) {
            val users = userDao.findByTrainerID(ctx.pathParam("trainer-id").toInt())
            if (users.isNotEmpty()) {
                ctx.json(users)
                ctx.status(200)
            } else {
                ctx.status(404)
            }
        }
    }

    fun getUsersByPlanId(ctx: Context) {
        if (planDao.findById(ctx.pathParam("plan-id").toInt()) != null) {
            val users = userDao.findByPlanId(ctx.pathParam("plan-id").toInt())
            if (users.isNotEmpty()) {
                ctx.json(users)
                ctx.status(200)
            } else {
                ctx.status(404)
            }
        }
    }

    fun getUserByEmail(ctx: Context) {
        val user = userDao.findByEmail(ctx.pathParam("email"))
        if (user != null) {
            ctx.json(user)
            ctx.status(200)
        } else {
            ctx.status(404)
        }
    }

    fun addUser(ctx: Context) {
        val user : User = jsonToObject(ctx.body())
        val userId = userDao.save(user)
        if (userId != null) {
            user.id = userId
            ctx.json(user)
            ctx.status(201)
        }
    }

    fun deleteUser(ctx: Context){
        if (userDao.delete(ctx.pathParam("user-id").toInt()) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }

    fun updateUser(ctx: Context){
        val foundUser : User = jsonToObject(ctx.body())
        if ((userDao.update(id = ctx.pathParam("user-id").toInt(), user=foundUser)) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }

    // Functions for handling trainer account data
    fun getAllTrainers(ctx: Context) {
        ctx.json(trainerDao.getAll())
    }

    fun getTrainerByTrainerId(ctx: Context) {
        val trainer = trainerDao.findById(ctx.pathParam("trainer-id").toInt())
        if (trainer != null) {
            ctx.json(trainer)
        }
    }

    fun getTrainerByEmail(ctx: Context) {
        val trainer = trainerDao.findByEmail(ctx.pathParam("email"))
        if (trainer != null) {
            ctx.json(trainer)
        }
    }

    fun addTrainer(ctx: Context) {
        val mapper = jacksonObjectMapper()
        val trainer = mapper.readValue<Trainer>(ctx.body())
        trainerDao.save(trainer)
        ctx.json(trainer)
    }

    fun deleteTrainer(ctx: Context){
        trainerDao.delete(ctx.pathParam("trainer-id").toInt())
    }

    fun updateTrainer(ctx: Context){
        val mapper = jacksonObjectMapper()
        val trainerUpdates = mapper.readValue<Trainer>(ctx.body())
        trainerDao.update(
            id = ctx.pathParam("trainer-id").toInt(),
            trainer=trainerUpdates)
    }
}