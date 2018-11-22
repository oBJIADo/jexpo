package ru.tsystems.divider.context;

import ru.tsystems.divider.dao.api.GeneralDao;
import ru.tsystems.divider.entity.GeneralEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class InMemoryDaoGeneral<Entity extends GeneralEntity> implements GeneralDao<Entity> {
    protected List<Entity> database;

    InMemoryDaoGeneral(){
        database = new ArrayList<Entity>();
    }

    /**
     * Add new record into DB.
     *
     * @param entity Entity which should be added.
     */
    @Override
    public void persist(Entity entity) {
        if(entity == null){
            throw new IllegalArgumentException("Entity is null");
        }
        if(entity.getId() == -1){
            entity.setId(database.size());
        }

        for(Entity cur: database){
            if(cur.getId() == entity.getId()){
                throw new IllegalArgumentException("Entity with cloned id");
            }
        }

        database.add(entity);
    }

    /**
     * Get a record from DB.
     *
     * @param id        record id.
     * @param className Entity class.
     * @return Entity.
     */
    @Override
    public Entity find(int id, Class<Entity> className) {
        for (Entity entity: database){
            if(entity.getId() == id){
                return entity;
            }
        }

        return null;
    }

    /**
     * Delete record from DB.
     *
     * @param entity Entity which should be deleted
     */
    @Override
    public void remove(Entity entity) {
        int id = entity.getId();

        if(id == -1) {
            throw new IllegalArgumentException("Delete entity with index -1");
        }

        for(int i = 0; i<database.size(); i++){
            if(id == database.get(i).getId()){
                database.remove(i);
            }
        }

    }

    /**
     * Merge entity.
     *
     * @param entity Entity
     */
    @Override
    public void merge(Entity entity) {
        for(int i=0; i<database.size(); i++){
            if(database.get(i).getId() == entity.getId()){
                database.set(i, entity);
            }
        }
    }

    /**
     * Get all records from db.
     *
     * @param className Entity class.
     * @return Entities.
     */
    @Override
    public List<Entity> getAll(Class<Entity> className) {
        return database;
    }
}
