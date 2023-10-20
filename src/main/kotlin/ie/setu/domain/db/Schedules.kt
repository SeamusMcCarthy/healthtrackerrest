package ie.setu.domain.db

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.time
import java.time.LocalTime

// SRP - Responsibility is to manage one activity.
//       Database wise, this is the table object.

object Schedules : Table("schedules") {
    val id = integer("id").autoIncrement().primaryKey()
    val className = varchar("className", 100)
    val dayOfWeek = varchar("dayOfWeek", 9)
    val time = time("time")
}