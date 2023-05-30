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
        if (player1Score == P40 && player2Score == Adv) {
            return deuceMatch
        }

        val postMatch = score(player1Score, player2Score)

        return otherPlayersScoreAffectsThings(postMatch)
    }

    private fun otherPlayersScoreAffectsThings(postMatch: SinglesMatch) = when (postMatch.player1Score) {
        Deuce -> when (postMatch.player2Score) {
            P40 -> deuceMatch
            else -> postMatch
        }
        Adv -> when (postMatch.player2Score) {
            Deuce -> SinglesMatch(Adv, P40)
            else -> postMatch
        }
        else -> postMatch
    }

    fun player2ScoresAPoint(): SinglesMatch {
        if (player2Score == P40 && player1Score == Adv) {
            return deuceMatch
        }

        //TODO might be simpler to duplicate code
        val swapPlayerPositions = score(player2Score, player1Score)
        val inversed = otherPlayersScoreAffectsThings(swapPlayerPositions)
        return SinglesMatch(inversed.player2Score, inversed.player1Score)
    }

    private val belowDeuceScores = setOf(Love, P15, P30)

    private fun score(currentScore: Score, otherPlayerScore: Score) =
        when (currentScore) {
            Love -> SinglesMatch(P15, otherPlayerScore)
            P15 -> SinglesMatch(P30, otherPlayerScore)
            P30 -> {
                when (otherPlayerScore) {
                    P40 -> SinglesMatch(Deuce, otherPlayerScore)
                    else -> SinglesMatch(P40, otherPlayerScore)
                }
            }
            P40 -> {
                when (otherPlayerScore) {
                    in belowDeuceScores -> SinglesMatch(Win, otherPlayerScore)
                    else -> TODO()
                }
            }
            Deuce -> SinglesMatch(Adv, otherPlayerScore)
            Adv -> when (otherPlayerScore) {
                P40 -> SinglesMatch(Win, otherPlayerScore)
                else -> TODO()
            }
            Win -> error("Match already won")
        }

    companion object {
        val deuceMatch = SinglesMatch(Deuce, Deuce)
    }
}
