package ie.setu.domain.repository


import ie.setu.domain.Assessment
import ie.setu.domain.db.Assessments
import ie.setu.utils.mapToAssessment
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class AssessmentDAO {
    /**
     * Find a list of all assessments
     * @return a list of assessments
     */
    fun getAll(): ArrayList<Assessment> {
        val assessmentList: ArrayList<Assessment> = arrayListOf()
        transaction {
            Assessments.selectAll().map {
                assessmentList.add(mapToAssessment(it)) }
        }
        return assessmentList
    }

    /**
     * Finds an assessment
     * @return the assessment matched by id
     */
    fun findById(id: Int): Assessment?{
        return transaction {
            Assessments.select() {
                Assessments.id eq id}
                .map{ mapToAssessment(it) }
                .firstOrNull()
        }
    }

    /**
     * Find a list of assessments by user
     * @return a list of assessments
     */
    fun findByUserId(userId: Int): List<Assessment>{
        return transaction {
            Assessments
                .select { Assessments.userId eq userId}
                .map { mapToAssessment(it) }
        }
    }

    /**
     * Adds an [assessment] to the Assessments table
     */
    fun save(assessment: Assessment){
        transaction {
            Assessments.insert {
                it[weight] = assessment.weight
                it[chest] = assessment.chest
                it[thigh] = assessment.thigh
                it[arm] = assessment.arm
                it[waist] = assessment.waist
                it[hips] = assessment.hips
                it[assessmentDate] = assessment.assessmentDate
                it[userId] = assessment.userId
            }
        }
    }

    /**
     * Deletes an assessment
     */
    fun delete(id: Int):Int {
        return transaction {
            Assessments.deleteWhere {
                Assessments.id eq id
            }
        }
    }

    /**
     * Updates an assessment by id
     */
    fun update(id: Int, assessment: Assessment){
        transaction {
            Assessments.update ({
                Assessments.id eq id}) {
                it[weight] = assessment.weight
                it[chest] = assessment.chest
                it[thigh] = assessment.thigh
                it[arm] = assessment.arm
                it[waist] = assessment.waist
                it[hips] = assessment.hips
                it[assessmentDate] = assessment.assessmentDate
                it[userId] = assessment.userId
            }
        }
    }
}