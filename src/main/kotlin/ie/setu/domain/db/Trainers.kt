package ie.setu.domain.db

import org.jetbrains.exposed.sql.Table

// SRP - Responsibility is to manage one activity.
//       Database wise, this is the table object.

object Trainers : Table("trainers") {
    val id = integer("id").autoIncrement().primaryKey()
    val name = varchar("name", 100)
    val email = varchar("email", 100)
    val password = varchar("password", 100)
}