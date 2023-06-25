package local.mathewdj.tennis.service

import local.mathewdj.tennis.repository.SinglesMatchRepository
import org.springframework.stereotype.Service

@Service
class SinglesMatchService(
    private val singlesMatchRepository: SinglesMatchRepository
) {

    fun singlesMatchById(id: Int) {
        singlesMatchRepository.findById(id)
    }
}
