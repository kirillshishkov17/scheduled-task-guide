package org.shishkov.scheduledtask.service

import org.shishkov.scheduledtask.client.TrelloClient
import org.shishkov.scheduledtask.config.TrelloProperties
import org.shishkov.scheduledtask.dto.TrelloBoardDto
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
    private var boards = listOf<TrelloBoardDto>()

    @Scheduled(fixedRate = 1, initialDelay = 0, timeUnit = TimeUnit.DAYS)
    fun updateBoardIds() {
        boards = client.getBoardsIds().filter { it.idMemberCreator == props.myMemberId }
    }

    @Scheduled(fixedRate = 1, initialDelay = 0, timeUnit = TimeUnit.DAYS)
    fun createJsonDumpFiles() {
        for (board in boards) {
            val json = client.getBoardJson(board.id)

            if (json.isNullOrEmpty()) {
                //при доработке клиента такой ситуации не должно возникать так как будет обработка 400 и 500 кодов
                continue
            }

            val boardName = sanitizeBoardName(board.name)
            val pathToJsonFile = Path(props.dumpDirPath).resolve("$boardName.json")

            Files.writeString(pathToJsonFile, json, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE
            )
        }
    }

    /**
     * Убирает все специальные символы из строки, чтобы Windows не ругался
     */
    private fun sanitizeBoardName(boardName: String): String {
        return boardName.replace(Regex("[^a-яА-ЯёЁa-zA-Z\\s]"), "")
            .replace(Regex("\\s+"), " ")
            .trim()
    }
}