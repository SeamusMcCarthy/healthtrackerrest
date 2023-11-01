package ie.setu.domain

import org.joda.time.DateTime

data class Assessment (var id: Int,
                       var weight:Int,
                       var chest:Int,
                       var thigh:Int,
                       var arm:Int,
                       var waist:Int,
                       var hips:Int,
                       var assessmentDate: DateTime,
                       var userId: Int)