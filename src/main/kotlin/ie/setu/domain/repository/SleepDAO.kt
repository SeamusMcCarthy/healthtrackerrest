package ie.setu.domain.repository

import ie.setu.domain.Sleep
import ie.setu.domain.db.Sleeps
import ie.setu.utils.mapToSleep
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class SleepDAO {

    /**
     * Find all sleep entries
     * @return a list of all [sleep] entries
     */
    fun getAll(): ArrayList<Sleep> {
        val sleepsList: ArrayList<Sleep> = arrayListOf()
        transaction {
            Sleeps.selectAll().map {
                sleepsList.add(mapToSleep(it)) }
        }
        return sleepsList
    }

    /**
     * Find a [sleep] entry by id
     */
    fun findBySleepId(id: Int): Sleep?{
        return transaction {
            Sleeps
                .select() { Sleeps.id eq id}
                .map{mapToSleep(it)}
                .firstOrNull()
        }
    }

    /**
     * Find all [sleep] entries for a user
     * @return a list of [sleep] entries
     */
    fun findByUserId(userId: Int): List<Sleep>{
        return transaction {
            Sleeps
                .select {Sleeps.userId eq userId}
                .map {mapToSleep(it)}
        }
    }

    /**
     * Add a [sleep] entry to the Sleeps table
     */
    fun save(sleep: Sleep){
        transaction {
            Sleeps.insert {
                it[started] = sleep.started
                it[duration] = sleep.duration
                it[userId] = sleep.userId
            }
        }
    }

    /**
     * Delete a [sleep] entry
     */
    fun delete(id: Int):Int {
        return transaction {
            Sleeps.deleteWhere {
                Sleeps.id eq id
            }
        }
    }

    /**
     * Update a [sleep] entry
     */
    fun update(id: Int, sleep: Sleep){
        transaction {
            Sleeps.update ({
                Sleeps.id eq id}) {
                it[started] = sleep.started
                it[duration] = sleep.duration
                it[userId] = sleep.userId
            }
        }
    }
}