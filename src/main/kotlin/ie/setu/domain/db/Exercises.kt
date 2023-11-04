package ie.setu.domain.db

import org.jetbrains.exposed.sql.Table

object Exercises : Table("exercises") {
    val id = integer("id").autoIncrement().primaryKey()
    val name = varchar("name", 50)
    val type = varchar("type", 50)
}