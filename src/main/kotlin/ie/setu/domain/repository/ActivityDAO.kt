package ie.setu.domain.repository

import ie.setu.domain.Activity
import ie.setu.domain.db.Activities
import ie.setu.utils.mapToActivity
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class ActivityDAO {

    /**
     * Find all activities
     * @return a list of activities
     */
    fun getAll(): ArrayList<Activity> {
        val activitiesList: ArrayList<Activity> = arrayListOf()
        transaction {
            Activities.selectAll().map {
                activitiesList.add(mapToActivity(it)) }
        }
        return activitiesList
    }

    /**
     * Find an activity by id
     * @return an [activity]
     */
    fun findByActivityId(id: Int): Activity?{
        return transaction {
            Activities
                .select() { Activities.id eq id}
                .map{mapToActivity(it)}
                .firstOrNull()
        }
    }

    /**
     * Find a list of activities by type
     * @return a list of activities
     */
    fun findByActivityType(exerciseID: Int): List<Activity> {
        return transaction {
            Activities.select {
                Activities.activityType eq exerciseID
            }
                .map { mapToActivity(it) }

        }
    }

    /**
     * Find a list of activities for a user
     * @return a list of activities for a [user]
     */
    fun findByUserId(userId: Int): List<Activity>{
        return transaction {
            Activities
                .select {Activities.userId eq userId}
                .map {mapToActivity(it)}
        }
    }

    /**
     * Adds an [activity] to the Activities table
     */
    fun save(activity: Activity){
        transaction {
            Activities.insert {
                it[duration] = activity.duration
                it[started] = activity.started
                it[calories] = activity.calories
                it[activityType] = activity.activityType
                it[userId] = activity.userId
            }
        }
    }

    /**
     * Deletes an [activity]
     */
    fun delete(id: Int):Int {
        return transaction {
            Activities.deleteWhere {
                Activities.id eq id
            }
        }
    }

    /**
     * Updates an [activity]
     */
    fun update(id: Int, activity: Activity){
        transaction {
            Activities.update ({
                Activities.id eq id}) {
                it[duration] = activity.duration
                it[started] = activity.started
                it[calories] = activity.calories
                it[activityType] = activity.activityType
                it[userId] = activity.userId
            }
        }
    }
}