import local.mathewdj.tennis.model.Score
import local.mathewdj.tennis.model.SinglesMatch
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class SinglesMatchTest {
    @Test
    fun `player 1 wins a point`() {
        val match = SinglesMatch()
        assertThat(match.player1ScoresAPoint()).isEqualTo(SinglesMatch(Score.P15, Score.Love))
    }

    @Test
    fun `player 1 wins 2 points`() {
        val match = SinglesMatch(Score.P15, Score.Love)
        assertThat(match.player1ScoresAPoint()).isEqualTo(SinglesMatch(Score.P30, Score.Love))
    }

    @Test
    fun `player 1 wins 3 points`() {
        val match = SinglesMatch(Score.P30, Score.Love)
        assertThat(match.player1ScoresAPoint()).isEqualTo(SinglesMatch(Score.P40, Score.Love))
    }

    @ParameterizedTest(name = "player 2 score is {0}")
    @EnumSource(Score::class, names = ["Love", "P15", "P30"])
    fun `player 1 wins the match if score is 40 and player 2 is below 40`(player2Score: Score) {
        val match = SinglesMatch(Score.P40, player2Score)
        assertThat(match.player1ScoresAPoint()).isEqualTo(SinglesMatch(Score.Win, player2Score))
    }

    @ParameterizedTest(name = "player 1 score is {0}")
    @EnumSource(Score::class, names = ["Love", "P15", "P30"])
    fun `player 2 wins the match if score is 40 and player 1 is below 40`(player1Score: Score) {
        val match = SinglesMatch(player1Score, Score.P40)
        assertThat(match.player2ScoresAPoint()).isEqualTo(SinglesMatch(player1Score, Score.Win))
    }

    @Test
    fun `player 1 in deuce`() {
        val match = SinglesMatch(Score.P30, Score.P40)
        assertThat(match.player1ScoresAPoint()).isEqualTo(SinglesMatch(Score.Deuce, Score.Deuce))
    }

    @Test
    fun `player 2 wins a point`() {
        val match = SinglesMatch()
        assertThat(match.player2ScoresAPoint()).isEqualTo(SinglesMatch(Score.Love, Score.P15))
    }

    @Test
    fun `player 2 wins 2 points`() {
        val match = SinglesMatch(Score.Love, Score.P15)
        assertThat(match.player2ScoresAPoint()).isEqualTo(SinglesMatch(Score.Love, Score.P30))
    }

    @Test
    fun `player 2 wins 3 points`() {
        val match = SinglesMatch(Score.Love, Score.P30)
        assertThat(match.player2ScoresAPoint()).isEqualTo(SinglesMatch(Score.Love, Score.P40))
    }

    @Test
    fun `throw exception if player 1 has already won`() {
        val e = assertThrows<IllegalStateException> {
            val match = SinglesMatch(Score.Win)
            match.player1ScoresAPoint()
        }
        assertThat(e.message).isEqualTo("Match already won")
    }
}
