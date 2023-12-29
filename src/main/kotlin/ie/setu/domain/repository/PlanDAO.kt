package ie.setu.domain.repository


import ie.setu.domain.Plan
import ie.setu.domain.db.Plans
import ie.setu.utils.mapToPlan
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class PlanDAO {

    /**
     * List all plans
     * @return a list of all plans
     */
    fun getAll(): ArrayList<Plan> {
        val plansList: ArrayList<Plan> = arrayListOf()
        transaction {
            Plans.selectAll().map {
                plansList.add(mapToPlan(it)) }
        }
        return plansList
    }

    /**
     * Find a [plan] by id
     * @return the found [plan]
     */
    fun findById(id: Int): Plan?{
        return transaction {
            Plans
                .select() { Plans.id eq id}
                .map{mapToPlan(it)}
                .firstOrNull()
        }
    }

    /**
     * Add a [plan] to the Plans table
     * @return the id of the [plan]
     */
    fun save(plan: Plan): Int?{
        return transaction {
            Plans.insert {
                it[name] = plan.name
                it[price] = plan.price
            }
        } get Plans.id
    }

    /**
     * Delete a [plan]
     */
    fun delete(id: Int):Int {
        return transaction {
            Plans.deleteWhere {
                Plans.id eq id
            }
        }
    }

    /**
     * Update a [plan]
     */
    fun update(id: Int, plan: Plan): Int?{
        return transaction {
            Plans.update ({
                Plans.id eq id}) {
                it[name] = plan.name
                it[price] = plan.price
            }
        }
    }
}