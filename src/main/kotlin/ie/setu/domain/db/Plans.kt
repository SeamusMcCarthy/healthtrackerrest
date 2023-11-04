package ie.setu.domain.db

import org.jetbrains.exposed.sql.Table

object Plans : Table("plans") {
    val id = integer("id").autoIncrement().primaryKey()
    val name = varchar("name", 100)
    val price = double("price")
}