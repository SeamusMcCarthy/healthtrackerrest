package ie.setu.domain.repository

import ie.setu.domain.Activity
import ie.setu.domain.Plan
import ie.setu.domain.db.Activities
import ie.setu.domain.db.Plans
import ie.setu.utils.mapToActivity
import ie.setu.utils.mapToPlan
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class PlanDAO {

    //Get all the plans in the database regardless of user id
    fun getAll(): ArrayList<Plan> {
        val plansList: ArrayList<Plan> = arrayListOf()
        transaction {
            Plans.selectAll().map {
                plansList.add(mapToPlan(it)) }
        }
        return plansList
    }

    //Find a specific plan by plan id
    fun findById(id: Int): Plan?{
        return transaction {
            Plans
                .select() { Plans.id eq id}
                .map{mapToPlan(it)}
                .firstOrNull()
        }
    }
}