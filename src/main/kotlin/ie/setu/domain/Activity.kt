package ie.setu.domain

import org.joda.time.DateTime

data class Activity (var id: Int,
                     var duration: Double,
                     var calories: Int,
                     var started: DateTime,
                     var activityType: Int,
                     var userId: Int)