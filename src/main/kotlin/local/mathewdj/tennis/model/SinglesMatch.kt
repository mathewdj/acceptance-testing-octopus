package local.mathewdj.tennis.model

data class SinglesMatch(
    private val player1Score: Score = Score.Love,
    private val player2Score: Score = Score.Love,
) {
    fun player1ScoresAPoint(): SinglesMatch {
        val score = score(player1Score, player2Score)
        val postMatch = this.copy(player1Score = score)

        if (postMatch.player1Score == Score.Deuce) {
            return SinglesMatch(Score.Deuce, Score.Deuce)
        }
        return postMatch
    }

    fun player2ScoresAPoint(): SinglesMatch {
        val score = score(player2Score, player1Score)

        val postMatch = this.copy(player2Score = score)

        if (postMatch.player1Score == Score.Deuce) {
            return SinglesMatch(Score.Deuce, Score.Deuce)
        }
        return postMatch
    }

    private val belowDeuceScores = setOf(Score.Love, Score.P15, Score.P30)

    private fun score(currentScore: Score, otherPlayerScore: Score) =
        when (currentScore) {
            Score.Love -> Score.P15
            Score.P15 -> Score.P30
            Score.P30 -> {
                when (otherPlayerScore) {
                    Score.P40 -> Score.Deuce
                    else -> Score.P40
                }
            }
            Score.P40 -> {
                when (otherPlayerScore) {
                    in belowDeuceScores -> Score.Win
                    else -> TODO()
                }
            }
            Score.Deuce -> TODO()
            Score.Adv -> TODO()
            Score.Win -> error("Match already won")
        }
}
