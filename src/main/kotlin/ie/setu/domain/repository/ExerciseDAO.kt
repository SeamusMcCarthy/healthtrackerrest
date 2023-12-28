package ie.setu.domain.repository


import ie.setu.domain.Exercise
import ie.setu.domain.db.Exercises
import ie.setu.utils.mapToExercise
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class ExerciseDAO {

    /**
     * Find a list of all exercises
     * @return a list of all exercises
     */
    fun getAll(): ArrayList<Exercise> {
        val exercisesList: ArrayList<Exercise> = arrayListOf()
        transaction {
            Exercises.selectAll().map {
                exercisesList.add(mapToExercise(it)) }
        }
        return exercisesList
    }

    /**
     * Find an [exercise] by id
     */
    fun findById(id: Int): Exercise?{
        return transaction {
            Exercises
                .select() { Exercises.id eq id}
                .map{mapToExercise(it)}
                .firstOrNull()
        }
    }

    /**
     * Add an [exercise] to the Exercises table
     * @return the id of the added entry
     */
    fun save(exercise: Exercise): Int?{
        return transaction {
            Exercises.insert {
                it[name] = exercise.name
                it[type] = exercise.type

            }
        } get Exercises.id
    }

    /**
     * Delete an [exercise]
     */
    fun delete(id: Int):Int {
        return transaction {
            Exercises.deleteWhere {
                Exercises.id eq id
            }
        }
    }

    /**
     * Update an [exercise]
     */
    fun update(id: Int, exercise: Exercise): Int?{
        return transaction {
            Exercises.update ({
                Exercises.id eq id}) {
                it[name] = exercise.name
                it[type] = exercise.type
            }
        }
    }
}