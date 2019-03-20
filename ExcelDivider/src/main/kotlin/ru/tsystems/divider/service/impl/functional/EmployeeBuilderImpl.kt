package ru.tsystems.divider.service.impl.functional

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.tsystems.divider.entity.Employee
import ru.tsystems.divider.service.api.functional.DataService
import ru.tsystems.divider.service.api.functional.EmployeeBuilder
import ru.tsystems.divider.service.api.functional.FieldBuilder
import ru.tsystems.divider.utils.api.MessageWorker
import ru.tsystems.divider.utils.constants.PROPS_SYMBOLS_SOURCE_EMPLOYEE

@Service
class EmployeeBuilderImpl(
    @Autowired messageWorker: MessageWorker,
    @Autowired val dataService: DataService
) : EmployeeBuilder {

    private val fieldBuilder: FieldBuilder
    private val DIVIDER: String

    init {
        fieldBuilder = FieldBuilderImpl()
        DIVIDER = messageWorker.getObligatorySourceValue(PROPS_SYMBOLS_SOURCE_EMPLOYEE)
    }

    override fun buildEmployee(employee: String): Employee {
        val divededStrs: Array<String> = fieldBuilder.rebuildString(employee, DIVIDER)
        return when {
            divededStrs.size == 1 -> dataService.findOrCreateEmployee(divededStrs[0])
            else -> dataService.findOrCreateEmployee(divededStrs[0], divededStrs[1])
        }
    }

    companion object {
        //private val logger = //logger.getLogger(EmployeeBuilderImpl::class.java)
    }
}