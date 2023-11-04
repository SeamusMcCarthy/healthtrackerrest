package ie.setu.domain.repository

import ie.setu.domain.Activity
import ie.setu.domain.db.Activities
import ie.setu.utils.mapToActivity
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class ActivityDAO {

    //Get all the activities in the database regardless of user id
    fun getAll(): ArrayList<Activity> {
        val activitiesList: ArrayList<Activity> = arrayListOf()
        transaction {
            Activities.selectAll().map {
                activitiesList.add(mapToActivity(it)) }
        }
        return activitiesList
    }

    //Find a specific activity by activity id
    fun findByActivityId(id: Int): Activity?{
        return transaction {
            Activities
                .select() { Activities.id eq id}
                .map{mapToActivity(it)}
                .firstOrNull()
        }
    }

    fun findByActivityType(exerciseID: Int): List<Activity> {
        return transaction {
            Activities.select {
                Activities.activityType eq exerciseID
            }
                .map { mapToActivity(it) }

        }
    }

    //Find all activities for a specific user id
    fun findByUserId(userId: Int): List<Activity>{
        return transaction {
            Activities
                .select {Activities.userId eq userId}
                .map {mapToActivity(it)}
        }
    }

    //Save an activity to the database
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

    fun delete(id: Int):Int {
        return transaction {
            Activities.deleteWhere {
                Activities.id eq id
            }
        }
    }

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