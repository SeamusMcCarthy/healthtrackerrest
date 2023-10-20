package ie.setu.utils

import ie.setu.domain.Activity
import ie.setu.domain.Plan
import ie.setu.domain.Schedule
import ie.setu.domain.User
import ie.setu.domain.db.Activities
import ie.setu.domain.db.Plans
import ie.setu.domain.db.Schedules
import ie.setu.domain.db.Users
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
    className = it[Schedules.className],
    dayOfWeek = it[Schedules.dayOfWeek],
    time = it[Schedules.time]
)