package local.mathewdj.tennis

import local.mathewdj.tennis.local.mathewdj.tennis.domain.createSinglesMatchEntity
import local.mathewdj.tennis.repository.SinglesMatchRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest(classes = [TennisApplication::class])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class DatabaseIntegrationTest {

    @Autowired
    private lateinit var singlesMatchRepository: SinglesMatchRepository

    @Nested
    inner class SinglesMatchPersistenceTest {
        @Test
        fun `should persist singles match`() {
            val match = createSinglesMatchEntity()

            val entity = singlesMatchRepository.save(match)

            val matchInDb = singlesMatchRepository.findById(entity.id ?: throw IllegalStateException("no id"))
            assertThat(matchInDb).usingRecursiveAssertion().ignoringFields("id").isEqualTo(match)
        }
    }

    @Nested
    inner class DatabaseConnectivitySmokeTest {
        @Autowired
        private lateinit var jdbcTemplate: JdbcTemplate

        @Test
        fun `when database is connected then it should be Postgres version 14`() {
            val actualDatabaseVersion = jdbcTemplate.queryForObject("SELECT version()", String::class.java)
            assertThat(actualDatabaseVersion).contains("PostgreSQL 14.5")
        }
    }

    companion object {
        @Container
        private val postgreSQLContainer = PostgreSQLContainer<Nothing>("postgres:latest")

        @DynamicPropertySource
        @JvmStatic
        fun registerDynamicProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl)
            registry.add("spring.datasource.username", postgreSQLContainer::getUsername)
            registry.add("spring.datasource.password", postgreSQLContainer::getPassword)
        }
    }
}
