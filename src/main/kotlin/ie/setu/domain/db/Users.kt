package ie.setu.domain.db

import ie.setu.domain.db.Activities.references
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

// SRP - Responsibility is to manage one user.
//       Database wise, this is the table object.

object Users : Table("users") {
    val id = integer("id").autoIncrement().primaryKey()
    val name = varchar("name", 100)
    val email = varchar("email", 255)
    val password = varchar("password", 100)
    val gender = char("gender")
    val height = integer("height")
    val startWeight = integer("start_weight")
    val trainerId = integer("trainer_id").references(Trainers.id, onDelete = ReferenceOption.CASCADE)
    val planID = integer("plan_id").references(Plans.id, onDelete = ReferenceOption.CASCADE)
}