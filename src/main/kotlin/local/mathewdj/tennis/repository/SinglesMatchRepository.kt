package local.mathewdj.tennis.repository

import local.mathewdj.tennis.entity.SinglesMatchEntity
import org.springframework.data.repository.CrudRepository

interface SinglesMatchRepository : CrudRepository<SinglesMatchEntity, Long>
