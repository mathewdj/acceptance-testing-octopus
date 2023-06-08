package local.mathewdj.tennis.domain

import local.mathewdj.tennis.domain.Score.Adv
import local.mathewdj.tennis.domain.Score.Deuce
import local.mathewdj.tennis.domain.Score.Love
import local.mathewdj.tennis.domain.Score.P15
import local.mathewdj.tennis.domain.Score.P30
import local.mathewdj.tennis.domain.Score.P40
import local.mathewdj.tennis.domain.Score.Win

data class SinglesMatch(
    private val player1Score: Score = Love,
    private val player2Score: Score = Love,
) {
    fun player1ScoresAPoint(): SinglesMatch {
        return score(player1Score, player2Score)
    }

    fun player2ScoresAPoint(): SinglesMatch {
        val swapPlayerPositions = score(player2Score, player1Score)
        return SinglesMatch(swapPlayerPositions.player2Score, swapPlayerPositions.player1Score)
    }

    private val belowDeuceScores = setOf(Love, P15, P30)

    private fun score(currentScore: Score, otherPlayerScore: Score) =
        when (currentScore) {
            Love -> SinglesMatch(P15, otherPlayerScore)
            P15 -> SinglesMatch(P30, otherPlayerScore)
            P30 -> thirtyScore(otherPlayerScore)
            P40 -> fortyScore(otherPlayerScore)
            Deuce -> SinglesMatch(Adv, P40)
            Adv -> advScore(otherPlayerScore, currentScore)
            Win -> error("Match already won")
        }

    private fun thirtyScore(otherPlayerScore: Score) = when (otherPlayerScore) {
        P40 -> deuceMatch
        else -> SinglesMatch(P40, otherPlayerScore)
    }

    private fun fortyScore(otherPlayerScore: Score) = when (otherPlayerScore) {
        in belowDeuceScores -> SinglesMatch(Win, otherPlayerScore)
        P40, Adv -> deuceMatch
        else -> error("score 40-$otherPlayerScore is not supported")
    }

    private fun advScore(
        otherPlayerScore: Score,
        currentScore: Score
    ) = when (otherPlayerScore) {
        P40 -> SinglesMatch(Win, otherPlayerScore)
        Deuce -> SinglesMatch(Adv, P40)
        else -> error("Score $currentScore-$otherPlayerScore is not supported")
    }

    companion object {
        val deuceMatch = SinglesMatch(Deuce, Deuce)
    }
}
