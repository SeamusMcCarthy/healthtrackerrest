package ie.setu.repository

import ie.setu.domain.Trainer
import ie.setu.domain.db.Trainers
import ie.setu.domain.repository.TrainerDAO
import ie.setu.helpers.nonExistingEmail
import ie.setu.helpers.trainers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

//retrieving some test data from Fixtures
val trainer1 = trainers[0]
val trainer2 = trainers[1]
val trainer3 = trainers[2]

class TrainerDAOTest {

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
        trainerDAO.save(trainer1)
        trainerDAO.save(trainer2)
        trainerDAO.save(trainer3)
        return trainerDAO
    }

    @Nested
    inner class CreateTrainers {

        @Test
        fun `multiple trainers added to table can be retrieved successfully`() {
            transaction {

                //Arrange - create and populate table with three users
                val trainerDAO = populateTrainerTable()

                //Act & Assert
                assertEquals(3, trainerDAO.getAll().size)
                assertEquals(trainer1, trainerDAO.findById(trainer1.id))
                assertEquals(trainer2, trainerDAO.findById(trainer2.id))
                assertEquals(trainer3, trainerDAO.findById(trainer3.id))


            }
        }
    }

    @Nested
    inner class ReadTrainers {
        @Test
        fun `get user by id that doesn't exist, results in no user returned`() {
            transaction {

                //Arrange - create and populate table with three users
                val trainerDAO = populateTrainerTable()

                //Act & Assert
                assertEquals(null, trainerDAO.findById(4))
            }
        }
        @Test
        fun `get user by id that exists, results in a correct user returned`() {
            transaction {
                val trainerDAO = populateTrainerTable()
                trainerDAO.save(trainer1)
                trainerDAO.save(trainer2)
                trainerDAO.save(trainer3)

                //Act & Assert
                assertEquals(trainer2, trainerDAO.findById(2))
            }
        }

        @Test
        fun `getting all users from a populated table returns all rows`() {
            transaction {

                //Arrange - create and populate table with three users
                val trainerDAO = populateTrainerTable()

                //Act & Assert
                assertEquals(3, trainerDAO.getAll().size)
            }
        }

        @Test
        fun `get all users over empty table returns none`() {
            transaction {

                //Arrange - create and setup trainerDAO object
                SchemaUtils.create(Trainers)
                val trainerDAO = TrainerDAO()

                //Act & Assert
                assertEquals(0, trainerDAO.getAll().size)
            }
        }

        @Test
        fun `get user by email that doesn't exist, results in no user returned`() {
            transaction {

                //Arrange - create and populate table with three users
                val trainerDAO = populateTrainerTable()

                //Act & Assert
                assertEquals(null, trainerDAO.findByEmail(nonExistingEmail))
            }
        }

        @Test
        fun `get trainer by email that exists, results in correct trainer returned`() {
            transaction {

                //Arrange - create and populate table with three users
                val trainerDAO = populateTrainerTable()

                //Act & Assert
                assertEquals(trainer2, trainerDAO.findByEmail(trainer2.email))
            }
        }
    }

    @Nested
    inner class DeleteTrainers {
        @Test
        fun `deleting a non-existant user in table results in no deletion`() {
            transaction {

                //Arrange - create and populate table with three users
                val trainerDAO = populateTrainerTable()

                //Act & Assert
                assertEquals(3, trainerDAO.getAll().size)
                trainerDAO.delete(4)
                assertEquals(3, trainerDAO.getAll().size)
            }
        }

        @Test
        fun `deleting an existing user in table results in record being deleted`() {
            transaction {

                //Arrange - create and populate table with three users
                val trainerDAO = populateTrainerTable()

                //Act & Assert
                assertEquals(3, trainerDAO.getAll().size)
                trainerDAO.delete(trainer3.id)
                assertEquals(2, trainerDAO.getAll().size)
            }
        }
    }

    @Nested
    inner class UpdateTrainers {

        @Test
        fun `updating existing user in table results in successful update`() {
            transaction {

                //Arrange - create and populate table with three users
                val trainerDAO = populateTrainerTable()

                //Act & Assert
                val trainer3Updated = Trainer(3, "new trainername", "new@email.ie", "testtest")
                trainerDAO.update(trainer3.id, trainer3Updated)
                assertEquals(trainer3Updated, trainerDAO.findById(3))
            }
        }

        @Test
        fun `updating non-existant user in table results in no updates`() {
            transaction {

                //Arrange - create and populate table with three users
                val trainerDAO = populateTrainerTable()

                //Act & Assert
                val trainer4Updated = Trainer(4, "new username", "new@email.ie", "testtest")
                trainerDAO.update(4, trainer4Updated)
                assertEquals(null, trainerDAO.findById(4))
                assertEquals(3, trainerDAO.getAll().size)
            }
        }
    }
}