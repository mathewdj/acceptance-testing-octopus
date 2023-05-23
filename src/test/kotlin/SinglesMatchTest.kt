import local.mathewdj.tennis.model.Score
import local.mathewdj.tennis.model.SinglesMatch
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

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
}
