package local.mathewdj.tennis.local.mathewdj.tennis.domain

import local.mathewdj.tennis.domain.Score
import local.mathewdj.tennis.domain.SinglesMatch
import local.mathewdj.tennis.entity.SinglesMatchEntity
import java.time.LocalDateTime
import java.util.UUID


val timestamp: LocalDateTime = LocalDateTime.of(2023, 6, 14, 13, 0, 0, 0)

fun createSinglesMatch(
    id: Long? = null,
    player1Score: Score = Score.P15,
    player2score: Score = Score.P40
) = SinglesMatch(
    id = id,
    player1Score = player1Score,
    player2Score = player2score
)

fun createSinglesMatchEntity(
    id: Long? = null,
    player1Score: Score = Score.P15,
    player2score: Score = Score.P40
) = SinglesMatchEntity(
    id = id,
    player1Score = player1Score,
    player2Score = player2score,
    createdAt = timestamp
)
