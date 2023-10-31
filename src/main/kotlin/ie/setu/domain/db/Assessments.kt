package ie.setu.domain.db

import ie.setu.domain.db.Activities.references
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

// SRP - Responsibility is to manage one user.
//       Database wise, this is the table object.

object Assessments : Table("assessments") {
    val id = integer("id").autoIncrement().primaryKey()
    val weight = integer("weight")
    val chest = integer("chest")
    val thigh = integer("thigh")
    val arm = integer("arm")
    val waist = integer("waist")
    val hips = integer("hips")
    val assessmentDate = datetime("assessment_date")
    val userID = integer("user_id").references(Users.id, onDelete = ReferenceOption.CASCADE)
}