package org.shishkov.scheduledtask

import org.shishkov.scheduledtask.config.TrelloProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@EnableConfigurationProperties(TrelloProperties::class)
@SpringBootApplication
class ScheduledTaskApplication

fun main(args: Array<String>) {
    runApplication<ScheduledTaskApplication>(*args)
}
