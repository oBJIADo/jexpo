package ru.tsystems.divider.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.tsystems.divider.dao.api.NatureDao;
import ru.tsystems.divider.entity.Nature;
import ru.tsystems.divider.service.api.NatureService;

public class NatureServiceImpl implements NatureService {

    private NatureDao natureDao;

    public NatureServiceImpl( @Autowired NatureDao natureDao) {
        this.natureDao = natureDao;
    }

    public Nature getOrAddNatureByTitle(String title){

        Nature nature = natureDao.getByTitle(title);
        if(nature == null){
            nature = new Nature(title);
            natureDao.persist(nature);
        }
        return nature;
    }

}
