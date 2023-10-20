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

    //Save an schedule to the database
    fun save(schedule: Schedule){
        transaction {
            Schedules.insert {
                it[classname] = schedule.classname
                it[dayofweek] = schedule.dayofweek

            }
        }
    }

}