package ie.setu.repository

import ie.setu.domain.Assessment
import ie.setu.domain.db.*
import ie.setu.domain.repository.*
import ie.setu.helpers.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

//retrieving some test data from Fixtures
val assessmentUser1 = users[0]
val assessmentTrainer1 = trainers[0]
val assessmentPlan1 = plans[0]

val assessment1 = assessments[0]
val assessment2 = assessments[1]
val assessment3 = assessments[2]
class AssessmentDAOTest {

    companion object {

        //Make a connection to a local, in memory H2 database.
        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }
    }

    internal fun populateTrainerTable(): TrainerDAO{
        SchemaUtils.create(Trainers)
        val trainerDAO = TrainerDAO()
        trainerDAO.save(assessmentTrainer1)
        return trainerDAO
    }
    internal fun populatePlanTable(): PlanDAO{
        SchemaUtils.create(Plans)
        val planDAO = PlanDAO()
        planDAO.save(assessmentPlan1)
        return planDAO
    }
    internal fun populateUserTable(): UserDAO{
        SchemaUtils.create(Users)
        val userDAO = UserDAO()
        userDAO.save(assessmentUser1)
        return userDAO
    }

    internal fun populateAssessmentTable(): AssessmentDAO {
        SchemaUtils.create(Assessments)
        val assessmentDAO = AssessmentDAO()
        assessmentDAO.save(assessment1)
        assessmentDAO.save(assessment2)
        assessmentDAO.save(assessment3)
        return assessmentDAO
    }

    @Nested
    inner class CreateAssessments {

        @Test
        fun `multiple assessments added to table can be retrieved successfully`() {
            transaction {

                //Arrange - create and populate table with three users

                populateTrainerTable()
                populatePlanTable()
                populateUserTable()
                val assessmentDAO = populateAssessmentTable()

                //Act & Assert
                assertEquals(3, assessmentDAO.getAll().size)
                assertEquals(assessment1, assessmentDAO.findById(assessment1.id))
                assertEquals(assessment2, assessmentDAO.findById(assessment2.id))
                assertEquals(assessment3, assessmentDAO.findById(assessment3.id))
            }
        }
    }

    @Nested
    inner class ReadAssessments {
        @Test
        fun `get assessment by id that doesn't exist, results in no assessments returned`() {
            transaction {

                //Arrange - create and populate table with three users
                populateTrainerTable()
                populatePlanTable()
                populateUserTable()
                val assessmentDAO = populateAssessmentTable()

                //Act & Assert
                assertEquals(null, assessmentDAO.findById(4))
            }
        }
        @Test
        fun `get assessment by id that exists, results in a correct assessment returned`() {
            transaction {
                populateTrainerTable()
                populatePlanTable()
                populateUserTable()
                //Arrange - create and populate table with three users
            val assessmentDAO = populateAssessmentTable()

                //Act & Assert
                assertEquals(assessment2, assessmentDAO.findById(2))
            }
        }

        @Test
        fun `getting all assessments from a populated table returns all rows`() {
            transaction {

                //Arrange - create and populate table with three users
                populateTrainerTable()
                populatePlanTable()
                populateUserTable()
                val assessmentDAO = populateAssessmentTable()

                //Act & Assert
                assertEquals(3, assessmentDAO.getAll().size)
            }
        }

        @Test
        fun `get all assessments over empty table returns none`() {
            transaction {

                //Arrange - create and setup userDAO object
                SchemaUtils.create(Assessments)
                val assessmentDAO = AssessmentDAO()

                //Act & Assert
                assertEquals(0, assessmentDAO.getAll().size)
            }
        }
    }

    @Nested
    inner class DeleteAssessments {
        @Test
        fun `deleting a non-existant assessment in table results in no deletion`() {
            transaction {

                //Arrange - create and populate table with three users
                populateTrainerTable()
                populatePlanTable()
                populateUserTable()
                val assessmentDAO = populateAssessmentTable()

                //Act & Assert
                assertEquals(3, assessmentDAO.getAll().size)
                assessmentDAO.delete(4)
                assertEquals(3, assessmentDAO.getAll().size)
            }
        }

        @Test
        fun `deleting an existing assessment in table results in record being deleted`() {
            transaction {

                //Arrange - create and populate table with three users
                populateTrainerTable()
                populatePlanTable()
                populateUserTable()
                val assessmentDAO = populateAssessmentTable()

                //Act & Assert
                assertEquals(3, assessmentDAO.getAll().size)
                assessmentDAO.delete(assessment3.id)
                assertEquals(2, assessmentDAO.getAll().size)
            }
        }
    }

    @Nested
    inner class UpdateAssessments {

        @Test
        fun `updating existing assessment in table results in successful update`() {
            transaction {

                //Arrange - create and populate table with three users
                populateTrainerTable()
                populatePlanTable()
                populateUserTable()
                val assessmentDAO = populateAssessmentTable()
                val dateTime = DateTime.parse("2022-01-06T21:30:10")


                //Act & Assert
                val assessment3Updated = Assessment(3, 110, 32, 24, 32, 32, 32, dateTime, 1)
                assessmentDAO.update(assessment3.id, assessment3Updated)
                assertEquals(assessment3Updated, assessmentDAO.findById(3))
            }
        }

        @Test
        fun `updating non-existent assessment in table results in no updates`() {
            transaction {

                //Arrange - create and populate table with three users
                populateTrainerTable()
                populatePlanTable()
                populateUserTable()
                val assessmentDAO = populateAssessmentTable()
                val dateTime = DateTime.parse("2022-01-06T21:30:10")


                //Act & Assert
                val assessment4Updated = Assessment(3, 110, 32, 24, 32, 32, 32, dateTime, 1)
                assessmentDAO.update(4, assessment4Updated)
                assertEquals(null, assessmentDAO.findById(4))
                assertEquals(3, assessmentDAO.getAll().size)
            }
        }
    }
}