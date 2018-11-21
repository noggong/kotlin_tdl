package com.dazziman.example;

import java.io.Serializable

data class Task(var description: String, var completed: Boolean = false, var checked: Boolean = false) : Serializable