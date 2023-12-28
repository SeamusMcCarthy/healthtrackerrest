package ie.setu.domain.repository

import ie.setu.domain.User
import ie.setu.domain.db.Users
import ie.setu.utils.mapToUser
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class UserDAO {
    /**
     * Fina all users
     * @return a list of users
     */
    fun getAll(): ArrayList<User> {
        val userList: ArrayList<User> = arrayListOf()
        transaction {
            Users.selectAll().map {
                userList.add(mapToUser(it)) }
        }
        return userList
    }

    /**
     * Find a [user] by id
     * @return a [user] entry
     */
    fun findById(id: Int): User?{
        return transaction {
            Users.select() {
                Users.id eq id}
                .map{mapToUser(it)}
                .firstOrNull()
        }
    }

    /**
     * Add a [user] to the Users table
     * @return the id of the newly added user
     */
    fun save(user: User): Int? {
        return transaction {
            Users.insert {
                it[name] = user.name
                it[email] = user.email
                it[password] = user.password
                it[gender] = user.gender
                it[height] = user.height
                it[startWeight] = user.startWeight
                it[trainerId] = user.trainerId
                it[planId] = user.planId
            } get Users.id
        }
    }

    /**
     * Find a [user] by email
     * @return a [user] entry
     */
    fun findByEmail(email: String) :User? {
        return transaction {
            Users.select() {
                Users.email eq email
            }
                .map { mapToUser(it) }
                .firstOrNull()
        }
    }

    /**
     * Find a [user] list by [trainer] id
     * @return a list of users by [trainer] id
     */
    fun findByTrainerID(trainerID: Int): List<User> {
        return transaction {
            Users.select {
                Users.trainerId eq trainerID
            }
                .map { mapToUser(it) }
        }
    }

    /**
     * Find a [user] list by [plan] id
     * @return a list of users by [plan] id
     */
    fun findByPlanId(planId: Int): List<User> {
        return transaction {
            Users.select {
                Users.planId eq planId
            }
                .map { mapToUser(it) }

        }
    }

    /**
     * Delete a [user]
     */
    fun delete(id: Int):Int {
        return transaction {
            Users.deleteWhere {
                Users.id eq id
            }
        }
    }

    /**
     * Update a [user]
     */
    fun update(id: Int, user: User): Int?{
        return transaction {
            Users.update ({
                Users.id eq id}) {
                it[name] = user.name
                it[email] = user.email
                it[password] = user.password
                it[gender] = user.gender
                it[height] = user.height
                it[startWeight] = user.startWeight
                it[trainerId] = user.trainerId
                it[planId] = user.planId
            }
        }
    }
}