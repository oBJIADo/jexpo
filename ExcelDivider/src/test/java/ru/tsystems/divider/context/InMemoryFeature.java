package ru.tsystems.divider.context;

import ru.tsystems.divider.dao.api.FeatureDao;
import ru.tsystems.divider.entity.Feature;

public class InMemoryFeature extends InMemoryDaoGeneral<Feature> implements FeatureDao, ContextSimmulator<Feature> {

    private static InMemoryFeature dao;

    protected static InMemoryFeature getInMemoryDao(){
        if(dao == null){
            dao = new InMemoryFeature();
        }
        return dao;
    }

    private InMemoryFeature() {
        super();
        reset();
    }

    /**
     * Get entity by param if it exist, return null if not.
     *
     * @param param  String with param.
     * @param nature
     * @return Entity if it exist either null.
     */
    @Override
    public Feature getByParam(String param, String nature) {
        for(Feature feature: database){
            if(param.equals(feature.getTitle()) && nature.equals(feature.getNature().getTitle())){
                return feature;
            }
        }
        return null;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setValue(String key, Feature value) {

    }

    @Override
    public Feature getValue(String key) {
        return null;
    }
}
