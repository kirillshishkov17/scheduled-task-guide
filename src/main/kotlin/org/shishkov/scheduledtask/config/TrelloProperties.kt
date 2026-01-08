package org.shishkov.scheduledtask.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "trello")
data class TrelloProperties(
    val key: String,
    val token: String,
    val myMemberId: String,
)