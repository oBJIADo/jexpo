package ru.tsystems.divider.service.impl.functional

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.tsystems.divider.dao.api.EmployeeDao
import ru.tsystems.divider.entity.Employee
import ru.tsystems.divider.service.api.functional.EmployeeBuilder
import ru.tsystems.divider.service.api.functional.FieldBuilder
import ru.tsystems.divider.utils.api.MessageWorker
import ru.tsystems.divider.utils.constants.PROPS_SYMBOLS_SOURCE_EMPLOYEE

@Service
class EmployeeBuilderImpl(@Autowired messageWorker: MessageWorker, @Autowired val dao: EmployeeDao) : EmployeeBuilder {

    private val fieldBuilder: FieldBuilder
    private val DIVIDER: String

    init {
        fieldBuilder = FieldBuilderImpl()
        DIVIDER = messageWorker.getObligatorySourceValue(PROPS_SYMBOLS_SOURCE_EMPLOYEE)
    }

    override fun buildEmployee(employee: String): Employee {
        val divededStrs: Array<String> = fieldBuilder.rebuildString(employee, DIVIDER)
        return when {
            divededStrs.isEmpty() -> throw IllegalArgumentException("todo")
            divededStrs.size == 1 -> findOrPersistEmployee(divededStrs[0])
            else -> findOrPersistEmployee(divededStrs[0], divededStrs[1])
        }
    }

    /**
     * Try to find employee into db. If it not exit, create new employee and persist it into.
     *
     * @param firstName  Employee's firstname.
     * @param secondName Employee's secondname.
     * @return Employee.
     */
    private fun findOrPersistEmployee(firstName: String, secondName: String): Employee {
        var employee: Employee? = dao.getByNamesIgnoreCase(firstName, secondName)
        return if (employee != null) {
            employee
        } else {
            employee = dao.getByNamesIgnoreCase(secondName, firstName)
            if (employee != null) {
                employee
            } else {
                employee = Employee(firstName, secondName)
                dao.persist(employee)
                employee
            }
        }
    }

    private fun findOrPersistEmployee(name: String): Employee {
        var employee: Employee? = dao.getByNamesIgnoreCase(name, name)
        return if (employee != null) {
            return employee
        } else {
            employee = Employee(name, name)
            dao.persist(employee)
            employee
        }
    }

    companion object {
        //private val logger = //logger.getLogger(EmployeeBuilderImpl::class.java)
    }
}