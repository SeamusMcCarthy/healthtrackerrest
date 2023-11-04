package ie.setu.domain.repository


import ie.setu.domain.Exercise
import ie.setu.domain.db.Exercises
import ie.setu.utils.mapToExercise
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class ExerciseDAO {

    //Get all the plans in the database regardless of user id
    fun getAll(): ArrayList<Exercise> {
        val exercisesList: ArrayList<Exercise> = arrayListOf()
        transaction {
            Exercises.selectAll().map {
                exercisesList.add(mapToExercise(it)) }
        }
        return exercisesList
    }

    //Find a specific entry by exercise id
    fun findById(id: Int): Exercise?{
        return transaction {
            Exercises
                .select() { Exercises.id eq id}
                .map{mapToExercise(it)}
                .firstOrNull()
        }
    }

    fun save(exercise: Exercise){
        transaction {
            Exercises.insert {
                it[name] = exercise.name
                it[type] = exercise.type

            }
        }
    }

    fun delete(id: Int):Int {
        return transaction {
            Exercises.deleteWhere {
                Exercises.id eq id
            }
        }
    }

    fun update(id: Int, exercise: Exercise){
        transaction {
            Exercises.update ({
                Exercises.id eq id}) {
                it[name] = exercise.name
                it[type] = exercise.type
            }
        }
    }
}