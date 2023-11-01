package ie.setu.domain

data class User (var id: Int,
                 var name:String,
                 var email:String,
                 var password: String,
                 var gender: Char,
                 var height: Int,
                 var startWeight: Int,
                 var trainerId: Int,
                 var planId: Int)