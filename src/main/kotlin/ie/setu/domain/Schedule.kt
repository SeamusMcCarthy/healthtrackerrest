package ie.setu.domain

import org.jetbrains.exposed.sql.javatime.Time
import java.time.LocalTime

data class Schedule (var id: Int,
                     var className:String,
                     var dayOfWeek: String,
                     var time: LocalTime)