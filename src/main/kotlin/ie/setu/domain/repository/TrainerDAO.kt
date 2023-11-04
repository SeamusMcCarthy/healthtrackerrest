package ie.setu.domain.repository

import ie.setu.domain.Trainer
import ie.setu.domain.db.Trainers
import ie.setu.utils.mapToTrainer
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class TrainerDAO {
    fun getAll(): ArrayList<Trainer> {
        val trainerList: ArrayList<Trainer> = arrayListOf()
        transaction {
            Trainers.selectAll().map {
                trainerList.add(mapToTrainer(it)) }
        }
        return trainerList
    }

    fun findById(id: Int): Trainer?{
        return transaction {
            Trainers.select() {
                Trainers.id eq id}
                .map{mapToTrainer(it)}
                .firstOrNull()
        }
    }

    fun save(trainer: Trainer){
        transaction {
            Trainers.insert {
                it[name] = trainer.name
                it[email] = trainer.email
                it[password] = trainer.password
            }
        }
    }

    fun findByEmail(email: String) :Trainer? {
        return transaction {
            Trainers.select() {
                Trainers.email eq email
            }
                .map { mapToTrainer(it) }
                .firstOrNull()
        }
    }

    fun delete(id: Int):Int {
        return transaction {
            Trainers.deleteWhere {
                Trainers.id eq id
            }
        }
    }

    fun update(id: Int, trainer: Trainer){
        transaction {
            Trainers.update ({
                Trainers.id eq id}) {
                it[name] = trainer.name
                it[email] = trainer.email
            }
        }
    }
}