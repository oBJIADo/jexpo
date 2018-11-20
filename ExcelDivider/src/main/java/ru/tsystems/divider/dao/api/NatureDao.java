package ru.tsystems.divider.dao.api;

import ru.tsystems.divider.entity.Nature;

public interface NatureDao extends GeneralDao<Nature> {

    Nature getByTitle(String title);

}
