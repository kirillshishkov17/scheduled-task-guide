package org.shishkov.scheduledtask.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@Configuration
class ClientConfig {

    @Bean
    fun getRestClient(): RestClient {
        return RestClient.create()
    }
}