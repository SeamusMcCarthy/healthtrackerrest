package ie.setu.domain.db

import org.jetbrains.exposed.sql.Table

object Schedules : Table("schedules") {
    val id = integer("id").autoIncrement().primaryKey()
    val classname = varchar("classname", 100)
    val dayofweek = varchar("dayofweek", 9)
}