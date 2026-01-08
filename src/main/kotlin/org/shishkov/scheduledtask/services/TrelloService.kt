package org.shishkov.scheduledtask.services

import org.shishkov.scheduledtask.clients.TrelloClient
import org.shishkov.scheduledtask.config.TrelloProperties
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.StandardOpenOption
import java.util.concurrent.TimeUnit
import kotlin.io.path.Path

@Service
class TrelloService(
    private val client: TrelloClient,
    private val props: TrelloProperties,
) {
    private var boardIds = listOf<String>()

    @Scheduled(fixedRate = 1, initialDelay = 0, timeUnit = TimeUnit.DAYS)
    fun updateBoardIds() {
        println(boardIds)

        boardIds = client.getBoardsIds()
            .filter { it.idMemberCreator == props.myMemberId }
            .map { it.id }

        println(boardIds)
    }

    //todo сохранять файлы json в постоянную память
    fun createJsonFiles() {
        for (boardId in boardIds) {
            val json = client.getBoardJson(boardId)

            if (json.isNullOrEmpty()) {
                //todo обработать ошибку, если вернулся пустой json
                throw Exception("Board dump is empty")
            }

            val pathToJsonFile = Path("M:\\Development\\education\\ScheduledTask\\src\\main\\resources\\dump_$boardId.json")
            Files.writeString(pathToJsonFile, json, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE)
        }
    }
}