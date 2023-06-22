package local.mathewdj.tennis.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import local.mathewdj.tennis.domain.Score
import java.time.LocalDateTime

@Entity(name = "singles_match")
data class SinglesMatchEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val player1Score: Score,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val player2Score: Score,

    @Column(nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()
)
