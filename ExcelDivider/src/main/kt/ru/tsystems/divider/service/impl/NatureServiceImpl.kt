package ru.tsystems.divider.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.tsystems.divider.dao.api.NatureDao
import ru.tsystems.divider.entity.Nature
import ru.tsystems.divider.service.api.NatureService

@Service
class NatureServiceImpl(@Autowired private val natureDao: NatureDao) : NatureService {

    override fun getOrAddNatureByTitle(title: String): Nature {

        var nature: Nature? = natureDao.getByTitle(title)
        if (nature == null) {
            nature = Nature(title)
            natureDao.persist(nature)
        }
        return nature
    }

}