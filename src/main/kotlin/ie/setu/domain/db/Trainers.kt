package ie.setu.domain.db

import org.jetbrains.exposed.sql.Table

object Trainers : Table("trainers") {
    val id = integer("id").autoIncrement().primaryKey()
    val name = varchar("name", 100)
    val email = varchar("email", 100)
    val password = varchar("password", 100)
}