package com.example


import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.server.response.*


fun main() {
    initDb()
    val repository = TodoRepository()
    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            json()

    }
      routing {
          route("/"){
              get{
                  call.respond(repository.getAll())
              }
              post{
                  val todo=call.receive<ToDo>()
                  call.respond(repository.add(todo))
              }
             put("/{id}")
              {
                  val id = call.parameters["id"]?.toIntOrNull()
                  if(id == null) {
                      call.respond(HttpStatusCode.BadRequest)
                      return@put
                  }
                  val updatedTodo= call.receive<ToDo>()
                  val success = repository.update(id,updatedTodo)
                  if(success) {
                      call.respond(HttpStatusCode.OK)
                  }else{
                      call.respond(HttpStatusCode.NotFound)
                  }
              }
              delete ("/{id}"){
                  val id = call.parameters["id"]?.toIntOrNull()
                  if(id == null) {
                      call.respond(HttpStatusCode.BadRequest)
                      return@delete
                  }
                  val success = repository.delete(id)
                  if(success) {
                      call.respond(HttpStatusCode.OK)
                  }else{
                      call.respond(HttpStatusCode.NotFound)
                  }
              }
          }
      }
  }.start(wait = true)
}



