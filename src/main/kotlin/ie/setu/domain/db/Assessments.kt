package ie.setu.domain.db


import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object Assessments : Table("assessments") {
    val id = integer("id").autoIncrement().primaryKey()
    val weight = integer("weight")
    val chest = integer("chest")
    val thigh = integer("thigh")
    val arm = integer("arm")
    val waist = integer("waist")
    val hips = integer("hips")
    val assessmentDate = datetime("assessment_date")
    val userId = integer("user_id").references(Users.id, onDelete = ReferenceOption.CASCADE)
}