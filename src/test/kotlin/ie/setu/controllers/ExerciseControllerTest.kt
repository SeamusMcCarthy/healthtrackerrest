package ie.setu.controllers

import ie.setu.config.DbConfig
import ie.setu.domain.Exercise
import ie.setu.utils.jsonToObject
import kong.unirest.HttpResponse
import kong.unirest.JsonNode
import kong.unirest.Unirest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertNotEquals
import ie.setu.helpers.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ExerciseControllerTest {

    private val db = DbConfig().getDbConnection()
    private val app = ServerContainer.instance
    private val origin = "http://localhost:" + app.port()

    //helper function to add a test exercise to the database
    private fun addExercise (name: String, type: String): HttpResponse<JsonNode> {
        return Unirest.post(origin + "/api/exercises")
            .body("{\"name\":\"$name\", \"type\":\"$type\"}")
            .asJson()
    }

    private fun deleteExercise (id: Int): HttpResponse<String> {
        return Unirest.delete(origin + "/api/exercises/$id").asString()
    }

    //helper function to update a test exercise to the database
    private fun updateExercise (id: Int, name: String, type: String): HttpResponse<JsonNode> {
        return Unirest.patch(origin + "/api/exercises/$id")
            .body("{\"name\":\"$name\", \"type\":\"$type\"}")
            .asJson()
    }

    private fun retrieveExerciseById(id: Int) : HttpResponse<String> {
        return Unirest.get(origin + "/api/exercises/${id}").asString()
    }

    @Nested
        inner class ReadExercises {
            @Test
            fun `get all exercises from the database returns 200 or 404 response`() {
                val response = Unirest.get(origin + "/api/exercises/").asString()
                if (response.status == 200) {
                    val retrievedExercises: ArrayList<Exercise> = jsonToObject(response.body.toString())
                    assertNotEquals(0, retrievedExercises.size)
                } else {
                    assertEquals(404, response.status)
                }
            }

            @Test
            fun `get exercise by id when exercise does not exist returns 404 response`() {

                //Arrange - test data for user id
                val id = Integer.MIN_VALUE

                // Act - attempt to retrieve the non-existent user from the database
                val retrieveResponse = Unirest.get(origin + "/api/exercises/${id}").asString()

                // Assert -  verify return code
                assertEquals(404, retrieveResponse.status)
            }

            @Test
            fun `getting an exercise by id when id exists, returns a 200 response`() {

                //Arrange - add the exercise
                val addResponse = addExercise(validExName, validExType)
                val addedExercise : Exercise = jsonToObject(addResponse.body.toString())

                //Assert - retrieve the added user from the database and verify return code
                val retrieveResponse = retrieveExerciseById(addedExercise.id)
                assertEquals(200, retrieveResponse.status)

                //After - restore the db to previous state by deleting the added user
                deleteExercise(addedExercise.id)
            }
        }

    @Nested
        inner class CreateExercises {
            @Test
            fun `add an exercise with correct details returns a 201 response`() {

                //Arrange & Act & Assert
                //    add the exercise and verify return code (using fixture data)
                val addResponse = addExercise(validExName, validExType)
                val addedExercise : Exercise = jsonToObject(addResponse.body.toString())
                assertEquals(201, addResponse.status)

                //Assert - retrieve the added plan from the database and verify return code
                val retrieveResponse= retrieveExerciseById(addedExercise.id)
                assertEquals(200, retrieveResponse.status)

                //Assert - verify the contents of the retrieved user

                assertEquals(validExType, addedExercise.type)
                assertEquals(validExName, addedExercise.name)

                //After - restore the db to previous state by deleting the added exercise
                val deleteResponse = deleteExercise(addedExercise.id)
                assertEquals(204, deleteResponse.status)
            }
        }

    @Nested
        inner class UpdateExercises {
            @Test
            fun `updating an exercise when it exists, returns a 204 response`() {

                //Arrange - add the exercise that we plan to do an update on

                val addedResponse = addExercise(validExName, validExType)
                val addedExercise : Exercise = jsonToObject(addedResponse.body.toString())

                //Act & Assert - update the type and name of the retrieved exercise and assert 204 is returned
                assertEquals(204, updateExercise(addedExercise.id, updatedExName, updatedExType).status)

                //Act & Assert - retrieve updated exercise and assert details are correct
                val updatedExerciseResponse = retrieveExerciseById(addedExercise.id)
                val updatedExercise : Exercise = jsonToObject(updatedExerciseResponse.body.toString())
                assertEquals(updatedExName, updatedExercise.name)
                assertEquals(updatedExType, updatedExercise.type)

                //After - restore the db to previous state by deleting the added exercise
                deleteExercise(addedExercise.id)
            }

            @Test
            fun `updating an exercise when it doesn't exist, returns a 404 response`() {

                //Act & Assert - attempt to update the price and name of plan that doesn't exist
                assertEquals(404, updateExercise(-1, updatedExName, updatedExType).status)
            }
        }

    @Nested
        inner class DeleteExercises {
        @Test
        fun `deleting an exercise when it doesn't exist, returns a 404 response`() {
            //Act & Assert - attempt to delete a plan that doesn't exist
            assertEquals(404, deleteExercise(-1).status)
        }

        @Test
        fun `deleting a plan when it exists, returns a 204 response`() {

            //Arrange - add the exercise that we plan to do a delete on
            val addedResponse = addExercise(validExName, validExType)
            val addedExercise : Exercise = jsonToObject(addedResponse.body.toString())

            //Act & Assert - delete the added plan and assert a 204 is returned
            assertEquals(204, deleteExercise(addedExercise.id).status)

            //Act & Assert - attempt to retrieve the deleted plan --> 404 response
            assertEquals(404, retrieveExerciseById(addedExercise.id).status)
        }
    }
}