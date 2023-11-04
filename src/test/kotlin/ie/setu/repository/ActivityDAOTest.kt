package ie.setu.repository

import ie.setu.domain.Activity
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
val activityUser1 = users[0]
val activityTrainer1 = trainers[0]
val activityPlan1 = plans[0]
val activityExercise1 = exercises[0]

val activity1 = activities[0]
val activity2 = activities[1]
val activity3 = activities[2]

class ActivityDAOTest {

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
        trainerDAO.save(activityTrainer1)
        return trainerDAO
    }
    internal fun populatePlanTable(): PlanDAO{
        SchemaUtils.create(Plans)
        val planDAO = PlanDAO()
        planDAO.save(activityPlan1)
        return planDAO
    }
    internal fun populateUserTable(): UserDAO{
        SchemaUtils.create(Users)
        val userDAO = UserDAO()
        userDAO.save(activityUser1)
        return userDAO
    }

    internal fun populateExerciseTable(): ExerciseDAO{
        SchemaUtils.create(Exercises)
        val exerciseDAO = ExerciseDAO()
        exerciseDAO.save(activityExercise1)
        return exerciseDAO
    }

    internal fun populateActivityTable(): ActivityDAO {
        SchemaUtils.create(Activities)
        val activityDAO = ActivityDAO()
        activityDAO.save(activity1)
        activityDAO.save(activity2)
        activityDAO.save(activity3)
        return activityDAO
    }

    @Nested
    inner class Createactivitys {

        @Test
        fun `multiple activities added to table can be retrieved successfully`() {
            transaction {

                //Arrange - create and populate table with three users

                populateTrainerTable()
                populatePlanTable()
                populateUserTable()
                populateExerciseTable()
                val activityDAO = populateActivityTable()

                //Act & Assert
                assertEquals(3, activityDAO.getAll().size)
                assertEquals(activity1, activityDAO.findByActivityId(activity1.id))
                assertEquals(activity2, activityDAO.findByActivityId(activity2.id))
                assertEquals(activity3, activityDAO.findByActivityId(activity3.id))
            }
        }
    }

    @Nested
    inner class ReadActivities {
        @Test
        fun `get activity by id that doesn't exist, results in no activities returned`() {
            transaction {

                //Arrange - create and populate table with three users
                populateTrainerTable()
                populatePlanTable()
                populateUserTable()
                populateExerciseTable()
                val activityDAO = populateActivityTable()

                //Act & Assert
                assertEquals(null, activityDAO.findByActivityId(4))
            }
        }
        @Test
        fun `get activity by id that exists, results in a correct activity returned`() {
            transaction {
                populateTrainerTable()
                populatePlanTable()
                populateUserTable()
                populateExerciseTable()
                //Arrange - create and populate table with three users
            val activityDAO = populateActivityTable()

                //Act & Assert
                assertEquals(activity2, activityDAO.findByActivityId(2))
            }
        }

        @Test
        fun `getting all activities from a populated table returns all rows`() {
            transaction {

                //Arrange - create and populate table with three users
                populateTrainerTable()
                populatePlanTable()
                populateUserTable()
                populateExerciseTable()
                val activityDAO = populateActivityTable()

                //Act & Assert
                assertEquals(3, activityDAO.getAll().size)
            }
        }

        @Test
        fun `get all activities over empty table returns none`() {
            transaction {

                //Arrange - create and setup userDAO object
                SchemaUtils.create(Activities)
                val activityDAO = ActivityDAO()

                //Act & Assert
                assertEquals(0, activityDAO.getAll().size)
            }
        }
    }

    @Nested
    inner class DeleteActivitys {
        @Test
        fun `deleting a non-existent activity in table results in no deletion`() {
            transaction {

                //Arrange - create and populate table with three users
                populateTrainerTable()
                populatePlanTable()
                populateUserTable()
                populateExerciseTable()
                val activityDAO = populateActivityTable()

                //Act & Assert
                assertEquals(3, activityDAO.getAll().size)
                activityDAO.delete(4)
                assertEquals(3, activityDAO.getAll().size)
            }
        }

        @Test
        fun `deleting an existing activity in table results in record being deleted`() {
            transaction {

                //Arrange - create and populate table with three users
                populateTrainerTable()
                populatePlanTable()
                populateUserTable()
                populateExerciseTable()
                val activityDAO = populateActivityTable()

                //Act & Assert
                assertEquals(3, activityDAO.getAll().size)
                activityDAO.delete(activity3.id)
                assertEquals(2, activityDAO.getAll().size)
            }
        }
    }

    @Nested
    inner class UpdateActivities {

        @Test
        fun `updating existing activity in table results in successful update`() {
            transaction {

                //Arrange - create and populate table with three users
                populateTrainerTable()
                populatePlanTable()
                populateUserTable()
                populateExerciseTable()
                val activityDAO = populateActivityTable()
                val dateTime = DateTime.parse("2022-01-06T21:30:10")


                //Act & Assert
                val activity3Updated = Activity(3, 60.0, 500, dateTime, 1, 1)
                activityDAO.update(activity3.id, activity3Updated)
                assertEquals(activity3Updated, activityDAO.findByActivityId(3))
            }
        }

        @Test
        fun `updating non-existent activity in table results in no updates`() {
            transaction {

                //Arrange - create and populate table with three users
                populateTrainerTable()
                populatePlanTable()
                populateUserTable()
                populateExerciseTable()
                val activityDAO = populateActivityTable()
                val dateTime = DateTime.parse("2022-01-06T21:30:10")


                //Act & Assert
                val activity4Updated = Activity(3, 60.0, 500, dateTime, 1, 31)
                activityDAO.update(4, activity4Updated)
                assertEquals(null, activityDAO.findByActivityId(4))
                assertEquals(3, activityDAO.getAll().size)
            }
        }
    }
}