package com.example


import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction


object Todos: Table() {
    val id = integer("id").autoIncrement()
    val title= varchar("title", 255)
    val description= varchar("description", 255)
    val isCompleted= bool("isCompleted")

    override val primaryKey = PrimaryKey(id)
}
fun initDb()
{
    Database.connect("jdbc:sqlite:todo.db", "org.sqlite.JDBC")
    transaction {
        SchemaUtils.create(Todos)
    }
}