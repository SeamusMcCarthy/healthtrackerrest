package ie.setu.domain

import org.joda.time.DateTime

data class Sleep (var id: Int,
                  var started: DateTime,
                  var duration: Double,
                  var userId: Int)