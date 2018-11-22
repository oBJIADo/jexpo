package ru.tsystems.divider.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.divider.dao.api.FeatureDao;
import ru.tsystems.divider.entity.Feature;
import ru.tsystems.divider.service.api.FeatureService;
import ru.tsystems.divider.service.api.NatureService;

@Service
public class FeatureServiceImpl implements FeatureService {

    private FeatureDao featureDao;
    private NatureService natureService;

    public FeatureServiceImpl(@Autowired FeatureDao fDao, @Autowired NatureService natureService){
        this.featureDao = fDao;
        this.natureService = natureService;
    }

    /**
     * Find One param entity by this param.
     *
     * @param param
     *            param for searching.
     * @param nature
     * @return Entity if it exist, either null.
     */
    @Override
    @Transactional
    public Feature findByParam(String param, String nature) {
        return  featureDao.getByParam(param, nature);
    }

    /**
     * Persist into db OneParamEntity.
     *
     * @param entity
     *            One param Entity.
     */
    @Override
    @Transactional //todo: rename or delete.
    public void persist(Feature entity) {
        featureDao.persist(entity);
    }

    /**
     * todo
     * @param title
     * @param nature
     */
    @Override
    @Transactional
    public Feature createFeatur(String title, String nature) {
        return new Feature(title, natureService.getOrAddNatureByTitle(nature));
    }
}
