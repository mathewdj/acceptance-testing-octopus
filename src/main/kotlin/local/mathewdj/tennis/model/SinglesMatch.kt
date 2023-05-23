package local.mathewdj.tennis.model

data class SinglesMatch(
    private val player1Score: Score = Score.Love,
    private val player2Score: Score = Score.Love,
) {
    fun player1ScoresAPoint(): SinglesMatch {
        val score = score(player1Score)
        return this.copy(player1Score = score)
    }

    fun player2ScoresAPoint(): SinglesMatch {
        val score = score(player2Score)
        return this.copy(player2Score = score)
    }

    private fun score(currentScore: Score) = when (currentScore) {
        Score.Love -> Score.P15
        Score.P15 -> Score.P30
        Score.P30 -> Score.P40
        Score.P40 -> TODO()
        Score.All -> TODO()
        Score.Deuce -> TODO()
        Score.AdvIn -> TODO()
        Score.AdvOut -> TODO()
    }
}
