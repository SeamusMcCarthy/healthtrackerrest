package ie.setu.repository

import ie.setu.domain.Sleep
import ie.setu.domain.db.Plans
import ie.setu.domain.db.Sleeps
import ie.setu.domain.db.Trainers
import ie.setu.domain.db.Users
import ie.setu.domain.repository.PlanDAO
import ie.setu.domain.repository.SleepDAO
import ie.setu.domain.repository.TrainerDAO
import ie.setu.domain.repository.UserDAO
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
val sleepUser1 = users[0]
val sleepTrainer1 = trainers[0]
val sleepPlan1 = plans[0]

val sleep1 = sleeps[0]
val sleep2 = sleeps[1]
val sleep3 = sleeps[2]
class SleepDAOTest {

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
        trainerDAO.save(sleepTrainer1)
        return trainerDAO
    }
    internal fun populatePlanTable(): PlanDAO{
        SchemaUtils.create(Plans)
        val planDAO = PlanDAO()
        planDAO.save(sleepPlan1)
        return planDAO
    }
    internal fun populateUserTable(): UserDAO{
        SchemaUtils.create(Users)
        val userDAO = UserDAO()
        userDAO.save(sleepUser1)
        return userDAO
    }

    internal fun populateSleepTable(): SleepDAO {
        SchemaUtils.create(Sleeps)
        val sleepDAO = SleepDAO()
        sleepDAO.save(sleep1)
        sleepDAO.save(sleep2)
        sleepDAO.save(sleep3)
        return sleepDAO
    }

    @Nested
    inner class CreateSleeps {

        @Test
        fun `multiple sleeps added to table can be retrieved successfully`() {
            transaction {

                //Arrange - create and populate table with three users

                populateTrainerTable()
                populatePlanTable()
                populateUserTable()
                val sleepDAO = populateSleepTable()

                //Act & Assert
                assertEquals(3, sleepDAO.getAll().size)
                assertEquals(sleep1, sleepDAO.findBySleepId(sleep1.id))
                assertEquals(sleep2, sleepDAO.findBySleepId(sleep2.id))
                assertEquals(sleep3, sleepDAO.findBySleepId(sleep3.id))
            }
        }
    }

    @Nested
    inner class ReadSleeps {
        @Test
        fun `get sleep by id that doesn't exist, results in no sleeps returned`() {
            transaction {

                //Arrange - create and populate table with three users
                populateTrainerTable()
                populatePlanTable()
                populateUserTable()
                val sleepDAO = populateSleepTable()

                //Act & Assert
                assertEquals(null, sleepDAO.findBySleepId(4))
            }
        }
        @Test
        fun `get sleep by id that exists, results in a correct sleep returned`() {
            transaction {
                populateTrainerTable()
                populatePlanTable()
                populateUserTable()
                //Arrange - create and populate table with three users
            val sleepDAO = populateSleepTable()

                //Act & Assert
                assertEquals(sleep2, sleepDAO.findBySleepId(2))
            }
        }

        @Test
        fun `getting all sleeps from a populated table returns all rows`() {
            transaction {

                //Arrange - create and populate table with three users
                populateTrainerTable()
                populatePlanTable()
                populateUserTable()
                val sleepDAO = populateSleepTable()

                //Act & Assert
                assertEquals(3, sleepDAO.getAll().size)
            }
        }

        @Test
        fun `get all sleeps over empty table returns none`() {
            transaction {

                //Arrange - create and setup userDAO object
                SchemaUtils.create(Sleeps)
                val sleepDAO = SleepDAO()

                //Act & Assert
                assertEquals(0, sleepDAO.getAll().size)
            }
        }
    }

    @Nested
    inner class DeleteSleeps {
        @Test
        fun `deleting a non-existant sleep in table results in no deletion`() {
            transaction {

                //Arrange - create and populate table with three users
                populateTrainerTable()
                populatePlanTable()
                populateUserTable()
                val sleepDAO = populateSleepTable()

                //Act & Assert
                assertEquals(3, sleepDAO.getAll().size)
                sleepDAO.delete(4)
                assertEquals(3, sleepDAO.getAll().size)
            }
        }

        @Test
        fun `deleting an existing sleep in table results in record being deleted`() {
            transaction {

                //Arrange - create and populate table with three users
                populateTrainerTable()
                populatePlanTable()
                populateUserTable()
                val sleepDAO = populateSleepTable()

                //Act & Assert
                assertEquals(3, sleepDAO.getAll().size)
                sleepDAO.delete(sleep3.id)
                assertEquals(2, sleepDAO.getAll().size)
            }
        }
    }

    @Nested
    inner class UpdateSleeps {

        @Test
        fun `updating existing sleep in table results in successful update`() {
            transaction {

                //Arrange - create and populate table with three users
                populateTrainerTable()
                populatePlanTable()
                populateUserTable()
                val sleepDAO = populateSleepTable()
                val dateTime = DateTime.parse("2022-01-06T21:30:10")


                //Act & Assert
                val sleep3Updated = Sleep(3, dateTime, 90.0, 1)
                sleepDAO.update(sleep3.id, sleep3Updated)
                assertEquals(sleep3Updated, sleepDAO.findBySleepId(3))
            }
        }

        @Test
        fun `updating non-existant sleep in table results in no updates`() {
            transaction {

                //Arrange - create and populate table with three users
                populateTrainerTable()
                populatePlanTable()
                populateUserTable()
                val sleepDAO = populateSleepTable()
                val dateTime = DateTime.parse("2022-01-06T21:30:10")


                //Act & Assert
                val sleep4Updated = Sleep(4, dateTime, 50.0, 1)
                sleepDAO.update(4, sleep4Updated)
                assertEquals(null, sleepDAO.findBySleepId(4))
                assertEquals(3, sleepDAO.getAll().size)
            }
        }
    }
}