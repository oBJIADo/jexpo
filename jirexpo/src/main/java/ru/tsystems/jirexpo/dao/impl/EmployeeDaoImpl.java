package ru.tsystems.jirexpo.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import ru.tsystems.jirexpo.dao.api.EmployeeDao;
import ru.tsystems.jirexpo.entity.Employee;

@Repository
public class EmployeeDaoImpl extends GeneralDaoImpl<Employee> implements EmployeeDao {
    private static final Logger logger = Logger.getLogger(EmployeeDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Finding employee who has specified first and second names
     *
     * @param firstName
     *            first name
     * @param secondName
     *            second name
     * @return {@link Employee}
     */
    @Override
    public Employee getByNames(String firstName, String secondName) {
        logger.info("Get Employee by names: " + firstName + " " + secondName);
        try {
            return entityManager.createQuery("from Employee as emp where emp.firstname=:firstName AND emp.secondname=:secondName"
                    , Employee.class)
                    .setParameter("firstName", firstName)
                    .setParameter("secondName", secondName)
                    .getSingleResult();
        }
        catch (NoResultException noResExc){
            logger.error("No results founded. Names :"  + firstName + " " + secondName);
            return null;
        }
    }
}
