package ie.setu.domain.repository


import ie.setu.domain.Schedule
import ie.setu.domain.db.Schedules
import ie.setu.utils.mapToSchedule
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class ScheduleDAO {

    //Get all the activities in the database regardless of user id
    fun getAll(): ArrayList<Schedule> {
        val activitiesList: ArrayList<Schedule> = arrayListOf()
        transaction {
            Schedules.selectAll().map {
                activitiesList.add(mapToSchedule(it)) }
        }
        return activitiesList
    }

    //Find a specific schedule by schedule id
    fun findById(id: Int): Schedule?{
        return transaction {
            Schedules
                .select() { Schedules.id eq id}
                .map{mapToSchedule(it)}
                .firstOrNull()
        }
    }

//    //Find all activities for a specific user id
//    fun findByUserId(userId: Int): List<Schedule>{
//        return transaction {
//            Schedules
//                .select {Schedules.userId eq userId}
//                .map {mapToActivity(it)}
//        }
//    }

    //Save an activity to the database
    fun save(schedule: Schedule){
        transaction {
            Schedules.insert {
                it[className] = schedule.className
                it[dayOfWeek] = schedule.dayOfWeek
                it[time] = schedule.time

            }
        }
    }

}