package local.mathewdj.tennis.domain

import local.mathewdj.tennis.domain.Score.Adv
import local.mathewdj.tennis.domain.Score.Deuce
import local.mathewdj.tennis.domain.Score.Love
import local.mathewdj.tennis.domain.Score.P15
import local.mathewdj.tennis.domain.Score.P30
import local.mathewdj.tennis.domain.Score.P40
import local.mathewdj.tennis.domain.Score.Win
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import java.util.UUID

class SinglesMatchTest {

    @Nested
    @DisplayName("Given 40-?")
    inner class FourPoints {

        @ParameterizedTest(name = "Given player2 score is {0}, player1 scores, then player1 wins")
        @EnumSource(Score::class, names = ["Love", "P15", "P30"])
        fun `player1 wins the match if score is 40 and player2 is below 40`(player2Score: Score) {
            val match = SinglesMatch(id, P40, player2Score)
            assertThat(match.player1ScoresAPoint()).isEqualTo(SinglesMatch(id, Win, player2Score))
        }

        @ParameterizedTest(name = "Given player1 score is {0}, player2 scores, then player2 wins")
        @EnumSource(Score::class, names = ["Love", "P15", "P30"])
        fun `player2 wins the match if score is 40 and player1 is below 40`(player1Score: Score) {
            val match = SinglesMatch(id, player1Score, P40)
            assertThat(match.player2ScoresAPoint()).isEqualTo(SinglesMatch(id, player1Score, Win))
        }

        @Test
        fun `throw exception if player1 has already won`() {
            val e = assertThrows<IllegalStateException> {
                val match = SinglesMatch(id, Win)
                match.player1ScoresAPoint()
            }
            assertThat(e.message).isEqualTo("Match already won")
        }

        @Test
        fun `throw exception if player2 has already won`() {
            val e = assertThrows<IllegalStateException> {
                val match = SinglesMatch(id, Love, Win)
                match.player2ScoresAPoint()
            }
            assertThat(e.message).isEqualTo("Match already won")
        }
    }

    @Nested
    @DisplayName("Given love-all")
    inner class LoveAll {
        @Test
        fun `when player1 wins a point, then score love-15`() {
            val match = SinglesMatch(id, Love, Love)
            assertThat(match.player1ScoresAPoint()).isEqualTo(SinglesMatch(id, P15, Love))
        }

        @Test
        fun `when player2 wins a point, then score love-15`() {
            val match = SinglesMatch(id, Love, Love)
            assertThat(match.player2ScoresAPoint()).isEqualTo(SinglesMatch(id, Love, P15))
        }
    }

    @Nested
    @DisplayName("Given Love-15")
    inner class Love15 {
        @Test
        fun `when player2 wins a point, then score love-30`() {
            val match = SinglesMatch(id, Love, P15)
            assertThat(match.player2ScoresAPoint()).isEqualTo(SinglesMatch(id, Love, P30))
        }
    }


    @Nested
    @DisplayName("Given 30-Love")
    inner class ThreePoints {
        @Test
        fun `when player1 scores a point, then score 40-love`() {
            val match = SinglesMatch(id, P30, Love)
            assertThat(match.player1ScoresAPoint()).isEqualTo(SinglesMatch(id, P40, Love))
        }

        @Test
        fun `when player2 scores a point, then score love-40`() {
            val match = SinglesMatch(id, Love, P30)
            assertThat(match.player2ScoresAPoint()).isEqualTo(SinglesMatch(id, Love, P40))
        }
    }

    @Nested
    @DisplayName("Given Deuce")
    inner class Deuce {
        @Test
        fun `Reach a deuce when score is 40-40`() {
            val match = SinglesMatch(id, P30, P40)
            assertThat(match.player1ScoresAPoint()).isEqualTo(SinglesMatch(id, Deuce, Deuce))
        }

        @Test
        fun `when in deuce player1 scores a point, then in adv`() {
            assertThat(SinglesMatch(id, Deuce, Deuce).player1ScoresAPoint()).isEqualTo(SinglesMatch(id, Adv, P40))
        }

        @Test
        fun `when in deuce player2 scores a point, then in adv`() {
            assertThat(SinglesMatch(id, Deuce, Deuce).player2ScoresAPoint()).isEqualTo(SinglesMatch(id, P40, Adv))
        }

        @Test
        fun `when player1 scores a point, Adv-40`() {
            assertThat(SinglesMatch(id, Deuce, Deuce).player1ScoresAPoint()).isEqualTo(SinglesMatch(id, Adv, P40))
        }

        @Test
        fun `when player2 scores a point, 40-Adv`() {
            assertThat(SinglesMatch(id, Deuce, Deuce).player2ScoresAPoint()).isEqualTo(SinglesMatch(id, P40, Adv))
        }
    }

    @Nested
    @DisplayName("Given adv-40")
    inner class AdvantageScenarios {

        @Test
        fun `when player1 scores a point, then wins the game`() {
            val match = SinglesMatch(id, Adv, P40)
            assertThat(match.player1ScoresAPoint()).isEqualTo(SinglesMatch(id, Win, P40))
        }

        @Test
        fun `when player2 scores a point, then wins the game`() {
            val match = SinglesMatch(id, P40, Adv)
            assertThat(match.player2ScoresAPoint()).isEqualTo(SinglesMatch(id, P40, Win))
        }

        @Test
        fun `when player2 on 40 scores a point, then player1 loses adv`() {
            val match = SinglesMatch(id, Adv, P40)
            assertThat(match.player2ScoresAPoint()).isEqualTo(SinglesMatch(id, Deuce, Deuce))
        }

        @Test
        fun `when player1 on 40 scores a point, then player2 loses adv`() {
            val match = SinglesMatch(id, P40, Adv)
            assertThat(match.player1ScoresAPoint()).isEqualTo(SinglesMatch(id, Deuce, Deuce))
        }
    }

    companion object {
        private val id = UUID.randomUUID()
    }
}
