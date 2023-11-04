package ie.setu.domain.db

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object Sleeps : Table("sleeps") {
    val id = integer("id").autoIncrement().primaryKey()
    val started = datetime("started")
    val duration = double("duration")
    val userId = integer("user_id").references(Users.id, onDelete = ReferenceOption.CASCADE)
}