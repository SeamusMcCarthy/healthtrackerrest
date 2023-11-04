package ie.setu.helpers

import ie.setu.domain.Plan
import ie.setu.domain.Trainer
import ie.setu.domain.User

val nonExistingEmail = "112233445566778testUser@xxxxx.xx"
val validName = "Test User 1"
val validEmail = "testuser1@test.com"

val users = arrayListOf<User>(
    User(name = "Alice Wonderland", email = "alice@wonderland.com", id = 1, password = "TestTest", gender = 'M', height = 180, startWeight = 150, trainerId = 1, planId = 1)  ,
    User(name = "Bob Cat", email = "bob@cat.ie", id = 2, password = "TestTest", gender = 'M', height = 180, startWeight = 150, trainerId = 1, planId = 1),
    User(name = "Mary Contrary", email = "mary@contrary.com", id = 3, password = "TestTest", gender = 'M', height = 180, startWeight = 150, trainerId = 1, planId = 1),
    User(name = "Carol Singer", email = "carol@singer.com", id = 4, password = "TestTest", gender = 'M', height = 180, startWeight = 150, trainerId = 1, planId = 1)
)

val trainers = arrayListOf<Trainer>(
    Trainer(name = "Alice Trainerland", email = "trainer@wonderland.com", id = 1, password = "TestTest"),
)

val plans = arrayListOf<Plan>(
    Plan(name = "Basic", price = 5.99, id = 1)
)