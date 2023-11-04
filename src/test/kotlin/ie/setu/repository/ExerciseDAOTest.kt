package ie.setu.repository

import ie.setu.domain.Exercise
import ie.setu.domain.db.Exercises
import ie.setu.domain.repository.ExerciseDAO
import ie.setu.helpers.exercises
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

//retrieving some test data from Fixtures
val exercise1 = exercises[0]
val exercise2 = exercises[1]
val exercise3 = exercises[2]
class ExerciseDAOTest {

    companion object {

        //Make a connection to a local, in memory H2 database.
        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }
    }

    internal fun populateExerciseTable(): ExerciseDAO {
        SchemaUtils.create(Exercises)
        val exerciseDAO = ExerciseDAO()
        exerciseDAO.save(exercise1)
        exerciseDAO.save(exercise2)
        exerciseDAO.save(exercise3)
        return exerciseDAO
    }

    @Nested
    inner class CreateExercises {

        @Test
        fun `multiple exercises added to table can be retrieved successfully`() {
            transaction {

                //Arrange - create and populate table with three exercises
                val exerciseDAO = populateExerciseTable()

                //Act & Assert
                assertEquals(3, exerciseDAO.getAll().size)
                assertEquals(exercise1, exerciseDAO.findById(exercise1.id))
                assertEquals(exercise2, exerciseDAO.findById(exercise2.id))
                assertEquals(exercise3, exerciseDAO.findById(exercise3.id))


            }
        }
    }

    @Nested
    inner class ReadExercises {
        @Test
        fun `get exercise by id that doesn't exist, results in no exercise returned`() {
            transaction {

                //Arrange - create and populate table with three exercises
                val exerciseDAO = populateExerciseTable()

                //Act & Assert
                assertEquals(null, exerciseDAO.findById(4))
            }
        }
        @Test
        fun `get exercise by id that exists, results in a correct exercise returned`() {
            transaction {
                val exerciseDAO = populateExerciseTable()
                exerciseDAO.save(exercise1)
                exerciseDAO.save(exercise2)
                exerciseDAO.save(exercise3)

                //Act & Assert
                assertEquals(exercise2, exerciseDAO.findById(2))
            }
        }

        @Test
        fun `getting all exercises from a populated table returns all rows`() {
            transaction {

                //Arrange - create and populate table with three exercises
                val exerciseDAO = populateExerciseTable()

                //Act & Assert
                assertEquals(3, exerciseDAO.getAll().size)
            }
        }

        @Test
        fun `get all exercises over empty table returns none`() {
            transaction {

                //Arrange - create and setup exerciseDAO object
                SchemaUtils.create(Exercises)
                val exerciseDAO = ExerciseDAO()

                //Act & Assert
                assertEquals(0, exerciseDAO.getAll().size)
            }
        }
    }

    @Nested
    inner class DeleteExercises {
        @Test
        fun `deleting a non-existent exercise in table results in no deletion`() {
            transaction {

                //Arrange - create and populate table with three exercises
                val exerciseDAO = populateExerciseTable()

                //Act & Assert
                assertEquals(3, exerciseDAO.getAll().size)
                exerciseDAO.delete(4)
                assertEquals(3, exerciseDAO.getAll().size)
            }
        }

        @Test
        fun `deleting an existing exercise in table results in record being deleted`() {
            transaction {

                //Arrange - create and populate table with three exercises
                val exerciseDAO = populateExerciseTable()

                //Act & Assert
                assertEquals(3, exerciseDAO.getAll().size)
                exerciseDAO.delete(exercise3.id)
                assertEquals(2, exerciseDAO.getAll().size)
            }
        }
    }

    @Nested
    inner class UpdateExercises {

        @Test
        fun `updating existing exercise in table results in successful update`() {
            transaction {

                //Arrange - create and populate table with three exercises
                val exerciseDAO = populateExerciseTable()

                //Act & Assert
                val exercise3Updated = Exercise(3, "new exercise", "Cardio")
                exerciseDAO.update(exercise3.id, exercise3Updated)
                assertEquals(exercise3Updated, exerciseDAO.findById(3))
            }
        }

        @Test
        fun `updating non-existent exercise in table results in no updates`() {
            transaction {

                //Arrange - create and populate table with three exercises
                val exerciseDAO = populateExerciseTable()

                //Act & Assert
                val exercise4Updated = Exercise(4, "new exercise", "Cardio")
                exerciseDAO.update(4, exercise4Updated)
                assertEquals(null, exerciseDAO.findById(4))
                assertEquals(3, exerciseDAO.getAll().size)
            }
        }
    }
}