package local.mathewdj.tennis.model

import local.mathewdj.tennis.model.Score.Adv
import local.mathewdj.tennis.model.Score.Deuce
import local.mathewdj.tennis.model.Score.Love
import local.mathewdj.tennis.model.Score.P15
import local.mathewdj.tennis.model.Score.P30
import local.mathewdj.tennis.model.Score.P40
import local.mathewdj.tennis.model.Score.Win

data class SinglesMatch(
    private val player1Score: Score = Love,
    private val player2Score: Score = Love,
) {
    fun player1ScoresAPoint(): SinglesMatch {
        val score = score(player1Score, player2Score)
        val postMatch = this.copy(player1Score = score)

        return otherPlayersScoreAffectsThings(postMatch)
    }

    private fun otherPlayersScoreAffectsThings(postMatch: SinglesMatch) = when (postMatch.player1Score) {
        Deuce -> when (postMatch.player2Score) {
            P40 -> SinglesMatch(Deuce, Deuce)
            else -> postMatch
        }
        Adv -> when (postMatch.player2Score) {
            Deuce -> SinglesMatch(Adv, P40)
            else -> postMatch
        }
        else -> postMatch
    }

    fun player2ScoresAPoint(): SinglesMatch {
        val score = score(player2Score, player1Score)
        val postMatch = this.copy(player2Score = score)

        //TODO might be simpler to duplicate code
        val swapPlayerPositions = SinglesMatch(postMatch.player2Score, postMatch.player1Score)
        val inversed = otherPlayersScoreAffectsThings(swapPlayerPositions)
        return SinglesMatch(inversed.player2Score, inversed.player1Score)
    }

    private val belowDeuceScores = setOf(Love, P15, P30)

    private fun score(currentScore: Score, otherPlayerScore: Score) =
        when (currentScore) {
            Love -> P15
            P15 -> P30
            P30 -> {
                when (otherPlayerScore) {
                    P40 -> Deuce
                    else -> P40
                }
            }
            P40 -> {
                when (otherPlayerScore) {
                    in belowDeuceScores -> Win
                    else -> TODO()
                }
            }
            Deuce -> Adv
            Adv -> TODO()
            Win -> error("Match already won")
        }
}
