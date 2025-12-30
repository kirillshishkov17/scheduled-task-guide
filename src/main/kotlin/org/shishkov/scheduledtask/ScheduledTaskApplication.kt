package org.shishkov.scheduledtask

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ScheduledTaskApplication

fun main(args: Array<String>) {
    runApplication<ScheduledTaskApplication>(*args)
}
