import local.mathewdj.tennis.model.Score
import local.mathewdj.tennis.model.Score.*
import local.mathewdj.tennis.model.SinglesMatch
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class SinglesMatchTest {

    @Nested
    @DisplayName("Winning scenarios")
    inner class Winning {

        @ParameterizedTest(name = "When player 2 score is {0}, then player 1 wins")
        @EnumSource(Score::class, names = ["Love", "P15", "P30"])
        fun `player 1 wins the match if score is 40 and player 2 is below 40`(player2Score: Score) {
            val match = SinglesMatch(P40, player2Score)
            assertThat(match.player1ScoresAPoint()).isEqualTo(SinglesMatch(Win, player2Score))
        }

        @ParameterizedTest(name = "When player 1 score is {0}, then player 2 wins")
        @EnumSource(Score::class, names = ["Love", "P15", "P30"])
        fun `player 2 wins the match if score is 40 and player 1 is below 40`(player1Score: Score) {
            val match = SinglesMatch(player1Score, P40)
            assertThat(match.player2ScoresAPoint()).isEqualTo(SinglesMatch(player1Score, Win))
        }

        @Test
        fun `throw exception if player 1 has already won`() {
            val e = assertThrows<IllegalStateException> {
                val match = SinglesMatch(Win)
                match.player1ScoresAPoint()
            }
            assertThat(e.message).isEqualTo("Match already won")
        }

        @Test
        fun `throw exception if player 2 has already won`() {
            val e = assertThrows<IllegalStateException> {
                val match = SinglesMatch(Love, Win)
                match.player2ScoresAPoint()
            }
            assertThat(e.message).isEqualTo("Match already won")
        }
    }

    @Nested
    @DisplayName("Given love-all")
    inner class OnePoint {
        @Test
        fun `when player 1 wins a point, then score 15`() {
            val match = SinglesMatch(Love, Love)
            assertThat(match.player1ScoresAPoint()).isEqualTo(SinglesMatch(P15, Love))
        }

        @Test
        fun `when player 2 wins a point, then score love-15`() {
            val match = SinglesMatch(Love, Love)
            assertThat(match.player2ScoresAPoint()).isEqualTo(SinglesMatch(Love, P15))
        }
    }

    @Nested
    @DisplayName("Given 15-Love")
    inner class TwoPoints {
        @Test
        fun `when player 2 wins 2 points, then score love-30`() {
            val match = SinglesMatch(Love, P15)
            assertThat(match.player2ScoresAPoint()).isEqualTo(SinglesMatch(Love, P30))
        }
    }


    @Nested
    @DisplayName("Given 30-Love")
    inner class ThreePoints {
        @Test
        fun `player 1 wins 3 points`() {
            val match = SinglesMatch(P30, Love)
            assertThat(match.player1ScoresAPoint()).isEqualTo(SinglesMatch(P40, Love))
        }

        @Test
        fun `player 2 wins 3 points`() {
            val match = SinglesMatch(Love, P30)
            assertThat(match.player2ScoresAPoint()).isEqualTo(SinglesMatch(Love, P40))
        }
    }

    @Nested
    @DisplayName("Deuce scenarios")
    inner class Deuce {
        private val match = SinglesMatch(Deuce, Deuce)

        @Test
        fun `Reach a deuce when score is 40-40`() {
            val match = SinglesMatch(P30, P40)
            assertThat(match.player1ScoresAPoint()).isEqualTo(SinglesMatch(Deuce, Deuce))
        }

        @Test
        fun `when in deuce player1 scores a point, then in adv`() {
            assertThat(match.player1ScoresAPoint()).isEqualTo(SinglesMatch(Adv, Deuce))
        }

        @Test
        fun `when in deuce player2 scores a point, then in adv`() {
            assertThat(match.player2ScoresAPoint()).isEqualTo(SinglesMatch(Deuce, Adv))
        }
    }
}
