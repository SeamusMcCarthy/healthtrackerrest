package ie.setu.domain.repository

import ie.setu.domain.Assessment
import ie.setu.domain.User
import ie.setu.domain.db.Assessments
import ie.setu.domain.db.Users
import ie.setu.utils.mapToAssessment
import ie.setu.utils.mapToUser
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class AssessmentDAO {
    fun getAll(): ArrayList<Assessment> {
        val assessmentList: ArrayList<Assessment> = arrayListOf()
        transaction {
            Assessments.selectAll().map {
                assessmentList.add(mapToAssessment(it)) }
        }
        return assessmentList
    }

    fun findById(id: Int): Assessment?{
        return transaction {
            Assessments.select() {
                Assessments.id eq id}
                .map{ mapToAssessment(it) }
                .firstOrNull()
        }
    }

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
                it[userID] = assessment.userID
            }
        }
    }



    fun delete(id: Int):Int {
        return transaction {
            Assessments.deleteWhere {
                Assessments.id eq id
            }
        }
    }

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
                it[userID] = assessment.userID
            }
        }
    }
}