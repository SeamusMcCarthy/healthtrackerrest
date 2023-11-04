# healthtrackerrest

Healthtrackerrest is a Restful Health Tracker app that in its current form allows for several endpoints to be hit and responsed to.

Features included are
- Users
- Trainers
- Plans
- Exercises
- Activities
- Assessments
- Sleeps (for sleep history)

There are several dependencies between the features leading to foreign key constraints being created - such as each user needing to have a trainer and plan, each assessment/activity/sleep entry must be associated with a user.
JUnit tests have been built out for each feature and Jacoco has been installed to generate code coverage reports.
Full Swagger docs have also been built and can be found in health-tracker-api.yaml within the project.
