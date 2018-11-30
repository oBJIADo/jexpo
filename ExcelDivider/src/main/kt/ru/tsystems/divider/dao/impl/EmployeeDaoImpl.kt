package ru.tsystems.divider.dao.impl

import org.apache.log4j.Logger
import org.springframework.stereotype.Repository
import ru.tsystems.divider.dao.api.EmployeeDao
import ru.tsystems.divider.entity.Employee
import javax.persistence.EntityManager
import javax.persistence.NoResultException
import javax.persistence.PersistenceContext

@Repository
class EmployeeDaoImpl(@PersistenceContext override val entityManager: EntityManager) : GeneralDaoImpl<Employee>(), EmployeeDao {


    /**
     * Finding employee who has specified first and second names
     *
     * @param firstName
     * first name
     * @param secondName
     * second name
     * @return [Employee]
     */
    override fun getByNames(firstName: String, secondName: String): Employee? {
        logger.info("Get Employee by names: $firstName $secondName")
        try {
            return entityManager!!.createQuery("from Employee as emp where emp.firstname=:firstName AND emp.secondname=:secondName", Employee::class.java)
                    .setParameter("firstName", firstName)
                    .setParameter("secondName", secondName)
                    .singleResult
        } catch (noResExc: NoResultException) {
            logger.warn("No results founded. Names :$firstName $secondName")
            return null
        }

    }

    companion object {
        private val logger = Logger.getLogger(EmployeeDaoImpl::class.java)
    }
}