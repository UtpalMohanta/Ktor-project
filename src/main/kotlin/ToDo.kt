package com.example

import kotlinx.serialization.Serializable


@Serializable
data class ToDo(
    val id:Int?=null,
    val title:String,
    val description:String,
    val isCompleted:Boolean
)
