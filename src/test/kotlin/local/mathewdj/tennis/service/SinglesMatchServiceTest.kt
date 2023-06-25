package local.mathewdj.tennis.service

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import local.mathewdj.tennis.createSinglesMatchEntity
import local.mathewdj.tennis.repository.SinglesMatchRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.Optional

@ExtendWith(MockKExtension::class)
class SinglesMatchServiceTest {

    private lateinit var singlesMatchService: SinglesMatchService

    @MockK
    private lateinit var singlesMatchRepository: SinglesMatchRepository

    @BeforeEach
    fun setup() {
        singlesMatchService = SinglesMatchService(singlesMatchRepository)
    }

    private val id = 99
    private val match = createSinglesMatchEntity(id = id)

    @Test
    fun `Then find single match by id`() {
        every {  singlesMatchRepository.findById(id) } returns Optional.of(match)
        assertThat(singlesMatchRepository.findById(id).get()).isEqualTo(match)
        verify { singlesMatchRepository.findById(id) }
    }

}
