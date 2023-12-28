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

The app is being hosted on Railway and when accessed, the user is presented with the below homepage
![image](https://github.com/SeamusMcCarthy/healthtrackerrest/assets/47305550/5235f3bc-ffae-4ec7-9722-d0adebeb38fb)

The app allows for members and trainers to log in and each will have a different experience. 

Users will see their profile details as well as their recorded assessments, activities and sleep history
![image](https://github.com/SeamusMcCarthy/healthtrackerrest/assets/47305550/fbb162b0-c26b-4e97-b6e0-d8073bd40fef)

Users will have the option to add entries to each of the sections
![image](https://github.com/SeamusMcCarthy/healthtrackerrest/assets/47305550/f9fa7d76-e495-4628-aa7a-f7b0bd09961d)

Trainers will have a different experience where logging in brings them to a different dashboard which shows them all members, members they train and also access to curate the list of exercises and plans that the app offers.
![image](https://github.com/SeamusMcCarthy/healthtrackerrest/assets/47305550/30046a01-5bf0-4788-8d02-0ba0ad719534)

![image](https://github.com/SeamusMcCarthy/healthtrackerrest/assets/47305550/b035a187-501d-4499-9169-788c739b9f39)

![image](https://github.com/SeamusMcCarthy/healthtrackerrest/assets/47305550/dde25f63-e6c0-4215-a740-760250e8cae0)





