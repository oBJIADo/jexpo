package ru.tsystems.divider.context;

import ru.tsystems.divider.dao.api.NatureDao;
import ru.tsystems.divider.entity.Feature;
import ru.tsystems.divider.entity.Nature;

public class InMemoryNature extends InMemoryDaoGeneral<Nature> implements NatureDao, ContextSimmulator<Nature> {

    private static InMemoryNature dao;

    protected static InMemoryNature getInMemoryDao(){
        if(dao == null){
            dao = new InMemoryNature();
        }
        return dao;
    }

    protected InMemoryNature() {
        super();
        reset();
    }

    @Override
    public Nature getByTitle(String title) {
        for(Nature nature: database){
            if(title.equals(nature.getTitle())){
                return nature;
            }
        }
        return null;
    }
}
