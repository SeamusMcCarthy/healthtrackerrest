package ie.setu.utils

import ie.setu.domain.*
import ie.setu.domain.db.*
import org.jetbrains.exposed.sql.ResultRow

fun mapToUser(it: ResultRow) = User(
    id = it[Users.id],
    name = it[Users.name],
    email = it[Users.email],
    password = it[Users.password],
    gender = it[Users.gender],
    height = it[Users.height],
    startWeight = it[Users.startWeight],
    trainerId = it[Users.trainerId],
    planId = it[Users.planId]
)

fun mapToActivity(it: ResultRow) = Activity(
    id = it[Activities.id],
    duration = it[Activities.duration],
    started = it[Activities.started],
    calories = it[Activities.calories],
    activityType = it[Activities.activityType],
    userId = it[Activities.userId]
)

fun mapToPlan(it: ResultRow) = Plan(
    id = it[Plans.id],
    name = it[Plans.name],
    price = it[Plans.price],
)

fun mapToExercise(it: ResultRow) = Exercise(
    id = it[Exercises.id],
    name = it[Exercises.name],
    type = it[Exercises.type]
)

fun mapToTrainer(it: ResultRow) = Trainer(
    id = it[Trainers.id],
    name = it[Trainers.name],
    email = it[Trainers.email],
    password = it[Trainers.password],
)

fun mapToAssessment(it: ResultRow) = Assessment(
    id = it[Assessments.id],
    weight = it[Assessments.weight],
    chest = it[Assessments.chest],
    thigh = it[Assessments.thigh],
    arm = it[Assessments.arm],
    waist = it[Assessments.waist],
    hips = it[Assessments.hips],
    assessmentDate = it[Assessments.assessmentDate],
    userId = it[Assessments.userId]
)

fun mapToSleep(it: ResultRow) = Sleep(
    id = it[Sleeps.id],
    started = it[Sleeps.started],
    duration = it[Sleeps.duration],
    userId = it[Sleeps.userId]
)