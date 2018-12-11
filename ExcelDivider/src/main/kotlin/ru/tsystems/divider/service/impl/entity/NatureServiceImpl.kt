package ru.tsystems.divider.service.impl.entity

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.tsystems.divider.dao.api.NatureDao
import ru.tsystems.divider.entity.Nature
import ru.tsystems.divider.service.api.entity.NatureService

@Service
class NatureServiceImpl(@Autowired private val natureDao: NatureDao) : NatureService {

    override fun getOrAddNatureByTitle(title: String): Nature {
        if (title.isEmpty()) {
            throw IllegalArgumentException("Nature cannot be null or empty!")
        }

        var nature: Nature? = natureDao.getByTitle(title)

        if (nature == null) {
            nature = Nature(title)
            natureDao.persist(nature)
        }

        return nature
    }

}