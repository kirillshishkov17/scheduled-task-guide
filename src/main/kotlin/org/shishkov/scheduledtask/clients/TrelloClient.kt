package org.shishkov.scheduledtask.clients

import org.shishkov.scheduledtask.config.TrelloProperties
import org.shishkov.scheduledtask.dto.TrelloBoardDto
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

/**
 * Документация Trello API
 * https://support.atlassian.com/trello/docs/getting-started-with-trello-rest-api/
 */

@Component
class TrelloClient(
    private val props: TrelloProperties,
) {
    private val client = RestClient.create()

    fun getBoardsIds(): List<TrelloBoardDto> {
        return client.get()
            .uri("https://api.trello.com/1/members/me/boards?key={key}&token={token}&fields=id,idMemberCreator,name", props.key, props.token)
            .retrieve()
            .body()!!
    }

    fun getBoardJson(boardId: String): String? {
        return client.get()
            .uri("https://api.trello.com/1/boards/{boardId}?key={key}&token={token}&fields=all&lists=all&list_fields=all&cards=all&card_fields=all&card_attachments=true&labels=all&members=all&member_fields=all&checklists=all&checklist_fields=all&actions=all&action_fields=all", boardId, props.key, props.token)
            .retrieve()
            .body()
    }
}