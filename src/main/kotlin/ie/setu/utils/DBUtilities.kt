package ie.setu.utils

import ie.setu.domain.*
import ie.setu.domain.db.*
import org.jetbrains.exposed.sql.ResultRow

fun mapToUser(it: ResultRow) = User(
    id = it[Users.id],
    name = it[Users.name],
    email = it[Users.email]
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