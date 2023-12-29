package ie.setu.controllers

import ie.setu.config.DbConfig
import ie.setu.domain.Plan
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
class PlanControllerTest {

    private val db = DbConfig().getDbConnection()
    private val app = ServerContainer.instance
    private val origin = "http://localhost:" + app.port()

    //helper function to add a test plan to the database
    private fun addPlan (name: String, price: Number): HttpResponse<JsonNode> {
        return Unirest.post(origin + "/api/plans")
            .body("{\"name\":\"$name\", \"price\":\"$price\"}")
            .asJson()
    }

    private fun deletePlan (id: Int): HttpResponse<String> {
        return Unirest.delete(origin + "/api/plans/$id").asString()
    }

    //helper function to update a test plan to the database
    private fun updatePlan (id: Int, name: String, price: Number): HttpResponse<JsonNode> {
        return Unirest.patch(origin + "/api/plans/$id")
            .body("{\"name\":\"$name\", \"price\":\"$price\"}")
            .asJson()
    }

    private fun retrievePlanById(id: Int) : HttpResponse<String> {
        return Unirest.get(origin + "/api/plans/${id}").asString()
    }

    @Nested
        inner class ReadPlans {
            @Test
            fun `get all plans from the database returns 200 or 404 response`() {
                val response = Unirest.get(origin + "/api/plans/").asString()
                if (response.status == 200) {
                    val retrievedPlans: ArrayList<Plan> = jsonToObject(response.body.toString())
                    assertNotEquals(0, retrievedPlans.size)
                } else {
                    assertEquals(404, response.status)
                }
            }

            @Test
            fun `get plan by id when plan does not exist returns 404 response`() {

                //Arrange - test data for user id
                val id = Integer.MIN_VALUE

                // Act - attempt to retrieve the non-existent user from the database
                val retrieveResponse = Unirest.get(origin + "/api/plans/${id}").asString()

                // Assert -  verify return code
                assertEquals(404, retrieveResponse.status)
            }

            @Test
            fun `getting a user by id when id exists, returns a 200 response`() {

                //Arrange - add the user
                val addResponse = addPlan(validPlanName, validPlanPrice)
                val addedPlan : Plan = jsonToObject(addResponse.body.toString())

                //Assert - retrieve the added user from the database and verify return code
                val retrieveResponse = retrievePlanById(addedPlan.id)
                assertEquals(200, retrieveResponse.status)

                //After - restore the db to previous state by deleting the added user
                deletePlan(addedPlan.id)
            }
        }

    @Nested
        inner class CreatePlans {
            @Test
            fun `add a plan with correct details returns a 201 response`() {

                //Arrange & Act & Assert
                //    add the plan and verify return code (using fixture data)
                val addResponse = addPlan(validPlanName, validPlanPrice)
                val addedPlan : Plan = jsonToObject(addResponse.body.toString())
                assertEquals(201, addResponse.status)

                //Assert - retrieve the added plan from the database and verify return code
                val retrieveResponse= retrievePlanById(addedPlan.id)
                assertEquals(200, retrieveResponse.status)

                //Assert - verify the contents of the retrieved user

                assertEquals(validPlanPrice, addedPlan.price)
                assertEquals(validPlanName, addedPlan.name)

                //After - restore the db to previous state by deleting the added plan
                val deleteResponse = deletePlan(addedPlan.id)
                assertEquals(204, deleteResponse.status)
            }
        }

    @Nested
        inner class UpdatePlans {
            @Test
            fun `updating a plan when it exists, returns a 204 response`() {

                //Arrange - add the plan that we plan to do an update on
                val updatedPlanName = "Updated Plan Name"
                val updatedPlanPrice = 14.99
                val addedResponse = addPlan(validPlanName, validPlanPrice)
                val addedPlan : Plan = jsonToObject(addedResponse.body.toString())

                //Act & Assert - update the price and name of the retrieved plan and assert 204 is returned
                assertEquals(204, updatePlan(addedPlan.id, updatedPlanName, updatedPlanPrice).status)

                //Act & Assert - retrieve updated user and assert details are correct
                val updatedPlanResponse = retrievePlanById(addedPlan.id)
                val updatedPlan : Plan = jsonToObject(updatedPlanResponse.body.toString())
                assertEquals(updatedPlanName, updatedPlan.name)
                assertEquals(updatedPlanPrice, updatedPlan.price)

                //After - restore the db to previous state by deleting the added user
                deletePlan(addedPlan.id)
            }

            @Test
            fun `updating a plan when it doesn't exist, returns a 404 response`() {

                //Act & Assert - attempt to update the price and name of plan that doesn't exist
                assertEquals(404, updatePlan(-1, updatedPlanName, updatedPlanPrice).status)
            }
        }

    @Nested
        inner class DeletePlans {
        @Test
        fun `deleting a plan when it doesn't exist, returns a 404 response`() {
            //Act & Assert - attempt to delete a plan that doesn't exist
            assertEquals(404, deletePlan(-1).status)
        }

        @Test
        fun `deleting a plan when it exists, returns a 204 response`() {

            //Arrange - add the plan that we plan to do a delete on
            val addedResponse = addPlan(validPlanName, validPlanPrice)
            val addedPlan : Plan = jsonToObject(addedResponse.body.toString())

            //Act & Assert - delete the added plan and assert a 204 is returned
            assertEquals(204, deletePlan(addedPlan.id).status)

            //Act & Assert - attempt to retrieve the deleted plan --> 404 response
            assertEquals(404, retrievePlanById(addedPlan.id).status)
        }
    }
}