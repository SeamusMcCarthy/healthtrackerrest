package ie.setu.repository

import ie.setu.domain.Plan
import ie.setu.domain.db.Plans
import ie.setu.domain.repository.PlanDAO
import ie.setu.helpers.plans
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

val plan1 = plans[0]
val plan2 = plans[1]
val plan3 = plans[2]

class PlanDAOTest {

    companion object {

        //Make a connection to a local, in memory H2 database.
        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }
    }

    internal fun populatePlanTable(): PlanDAO{
        SchemaUtils.create(Plans)
        val planDAO = PlanDAO()
        planDAO.save(plan1)
        planDAO.save(plan2)
        planDAO.save(plan3)
        return planDAO
    }

    @Nested
    inner class CreatePlans {

        @Test
        fun `multiple plans added to table can be retrieved successfully`() {
            transaction {

                //Arrange - create and populate table with three plans
                val planDAO = populatePlanTable()

                //Act & Assert
                assertEquals(3, planDAO.getAll().size)
                assertEquals(plan1, planDAO.findById(plan1.id))
                assertEquals(plan2, planDAO.findById(plan2.id))
                assertEquals(plan3, planDAO.findById(plan3.id))


            }
        }
    }

    @Nested
    inner class ReadPlans {
        @Test
        fun `get plan by id that doesn't exist, results in no plan returned`() {
            transaction {

                //Arrange - create and populate table with three plans
                val planDAO = populatePlanTable()

                //Act & Assert
                assertEquals(null, planDAO.findById(4))
            }
        }
        @Test
        fun `get plan by id that exists, results in a correct plan returned`() {
            transaction {

                val planDAO = populatePlanTable()

                //Act & Assert
                assertEquals(plan2, planDAO.findById(2))
            }
        }

        @Test
        fun `getting all plans from a populated table returns all rows`() {
            transaction {

                //Arrange - create and populate table with three users

                val planDAO = populatePlanTable()

                //Act & Assert
                assertEquals(3, planDAO.getAll().size)
            }
        }

        @Test
        fun `get all plans over empty table returns none`() {
            transaction {

                //Arrange - create and setup userDAO object
                SchemaUtils.create(Plans)
                val planDAO = PlanDAO()

                //Act & Assert
                assertEquals(0, planDAO.getAll().size)
            }
        }
    }

    @Nested
    inner class DeletePlans {
        @Test
        fun `deleting a non-existant plan in table results in no deletion`() {
            transaction {

                //Arrange - create and populate table with three plans
                val planDAO = populatePlanTable()

                //Act & Assert
                assertEquals(3, planDAO.getAll().size)
                planDAO.delete(4)
                assertEquals(3, planDAO.getAll().size)
            }
        }

        @Test
        fun `deleting an existing plan in table results in record being deleted`() {
            transaction {

                //Arrange - create and populate table with three plans
                val planDAO = populatePlanTable()

                //Act & Assert
                assertEquals(3, planDAO.getAll().size)
                planDAO.delete(plan3.id)
                assertEquals(2, planDAO.getAll().size)
            }
        }
    }

    @Nested
    inner class UpdatePlans {

        @Test
        fun `updating existing plan in table results in successful update`() {
            transaction {

                //Arrange - create and populate table with three plans
                val planDAO = populatePlanTable()

                //Act & Assert
                val plan3Updated = Plan(3, "new planname", 1.23)
                planDAO.update(plan3.id, plan3Updated)
                assertEquals(plan3Updated, planDAO.findById(3))
            }
        }

        @Test
        fun `updating non-existent plan in table results in no updates`() {
            transaction {

                //Arrange - create and populate table with three plans
                val planDAO = populatePlanTable()

                //Act & Assert
                val plan4Updated = Plan(4, "new planname", 9.87)
                planDAO.update(4, plan4Updated)
                assertEquals(null, planDAO.findById(4))
                assertEquals(3, planDAO.getAll().size)
            }
        }
    }
}