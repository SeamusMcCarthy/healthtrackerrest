package ie.setu.helpers

import ie.setu.domain.*
import org.joda.time.DateTime

val nonExistingEmail = "112233445566778testUser@xxxxx.xx"
val validName = "Test User 1"
val validEmail = "testuser1@test.com"
val validPassword = "testytest"
val validGender = 'F'
val validHeight = 170
val validWeight = 100
val validTrainerID = 1
val validPlanID = 1
val validPlanName = "Dummy Plan"
val validPlanPrice = 13.99
val updatedName = "Updated Name"
val updatedEmail = "Updated Email"
val updatedPlanName = "Updated Plan Name"
val updatedPlanPrice = 14.99
val validExName = "Running Around"
val validExType = "General Fitness"
val updatedExName = "Running Around Some More"
val updatedExType = "Endurance"

val users = arrayListOf<User>(
    User(name = "Alice Wonderland", email = "alice@wonderland.com", id = 1, password = "TestTest", gender = 'M', height = 180, startWeight = 150, trainerId = 1, planId = 1),
    User(name = "Bob Cat", email = "bob@cat.ie", id = 2, password = "TestTest", gender = 'M', height = 180, startWeight = 150, trainerId = 1, planId = 1),
    User(name = "Mary Contrary", email = "mary@contrary.com", id = 3, password = "TestTest", gender = 'M', height = 180, startWeight = 150, trainerId = 1, planId = 1),
    User(name = "Carol Singer", email = "carol@singer.com", id = 4, password = "TestTest", gender = 'M', height = 180, startWeight = 150, trainerId = 1, planId = 1)
)

val trainers = arrayListOf<Trainer>(
    Trainer(name = "Alice Trainerland", email = "trainer@wonderland.com", id = 1, password = "TestTest1"),
    Trainer(name = "Rabbit Trainerland", email = "rabbit@wonderland.com", id = 2, password = "TestTest2"),
    Trainer(name = "Hatter Trainerland", email = "hatter@wonderland.com", id = 3, password = "TestTest3"),
)

val plans = arrayListOf<Plan>(
    Plan(name = "Basic", price = 0.00, id = 1),
    Plan(name = "Middle", price = 5.99, id = 2),
    Plan(name = "Top", price = 9.99, id = 3)
)

val exercises = arrayListOf<Exercise>(
    Exercise(name = "Aerobics", type = "Fitness", id = 1),
    Exercise(name = "Biking", type = "Endurance", id = 2),
    Exercise(name = "Weights", type = "Strength", id = 3),
)

// Select date to be used in Sleep, Activity, Assessment tests
val dateTime = DateTime.parse("2022-01-06T21:30:10")

val sleeps = arrayListOf<Sleep>(
    Sleep(started = dateTime, duration = 150.0, userId = 1, id = 1),
    Sleep(started = dateTime, duration = 130.0, userId = 1, id = 2),
    Sleep(started = dateTime, duration = 110.0, userId = 1, id = 3)
)

val activities = arrayListOf<Activity>(
    Activity(duration = 60.0, calories = 900, started = dateTime, activityType = 1, userId = 1, id = 1),
    Activity(duration = 50.0, calories = 800, started = dateTime, activityType = 1, userId = 1, id = 2),
    Activity(duration = 40.0, calories = 700, started = dateTime, activityType = 1, userId = 1, id = 3),
)

val assessments = arrayListOf<Assessment>(
    Assessment(weight = 180, chest = 32, thigh = 30, arm = 24, waist = 32, hips = 30, assessmentDate = dateTime, userId = 1, id = 1),
    Assessment(weight = 170, chest = 31, thigh = 31, arm = 23, waist = 31, hips = 29, assessmentDate = dateTime, userId = 1, id = 2),
    Assessment(weight = 160, chest = 30, thigh = 32, arm = 25, waist = 30, hips = 31, assessmentDate = dateTime, userId = 1, id = 3)
)