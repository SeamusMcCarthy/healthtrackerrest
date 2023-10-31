package ie.setu.utils

import com.fasterxml.jackson.annotation.JsonTypeInfo.As
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
    trainerID = it[Users.trainerId],
    planID = it[Users.planID]
)

fun mapToActivity(it: ResultRow) = Activity(
    id = it[Activities.id],
    description = it[Activities.description],
    duration = it[Activities.duration],
    started = it[Activities.started],
    calories = it[Activities.calories],
    userId = it[Activities.userId]
)

fun mapToPlan(it: ResultRow) = Plan(
    id = it[Plans.id],
    name = it[Plans.name],
    price = it[Plans.price],
)

fun mapToSchedule(it: ResultRow) = Schedule(
    id = it[Schedules.id],
    classname = it[Schedules.classname],
    dayofweek = it[Schedules.dayofweek]
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
    userID = it[Assessments.userID]
)

fun mapToSleep(it: ResultRow) = Sleep(
    id = it[Sleeps.id],
    started = it[Sleeps.started],
    duration = it[Sleeps.duration],
    userId = it[Sleeps.userId]
)