package com.example

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class TodoRepository {

    fun getAll(): List<ToDo> = transaction {

        Todos.selectAll().map {
            ToDo(
                id=it[Todos.id],
                title = it[Todos.title],
                description = it[Todos.description],
                isCompleted = it[Todos.isCompleted]
            )
        }
    }

    fun add(todo: ToDo): ToDo = transaction {
          val id= Todos.insert {
                it[title] = todo.title
                it[description] = todo.description
                it[isCompleted] = todo.isCompleted
            }get Todos.id
            todo.copy(
                id=id
            )
        }
    fun update(
        todoId:Int,
        updatedTodo:ToDo
    ): Boolean = transaction {
        val rowUpdated= Todos.update(where = {
          Todos.id eq todoId
        }){
          it[title]=updatedTodo.title
          it[description]=updatedTodo.description
          it[isCompleted]=updatedTodo.isCompleted
        }
        rowUpdated >0
    }
    fun delete(todoId:Int): Boolean = transaction {
       Todos.deleteWhere {
           Todos.id eq todoId
       } >0
    }

}