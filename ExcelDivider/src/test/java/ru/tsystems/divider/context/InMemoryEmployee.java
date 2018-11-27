package ru.tsystems.divider.context;

import ru.tsystems.divider.dao.api.EmployeeDao;
import ru.tsystems.divider.entity.Employee;

public class InMemoryEmployee extends InMemoryDaoGeneral<Employee> implements EmployeeDao, ContextSimmulator<Employee> {

    private static InMemoryEmployee dao;

    protected static InMemoryEmployee getInMemoryDao() {
        if (dao == null) {
            dao = new InMemoryEmployee();
        }
        return dao;
    }

    private InMemoryEmployee() {
        super();
        reset();
    }

    /**
     * Finding employee who has specified first and second names
     *
     * @param firstName  first name
     * @param secondName second name
     * @return {@link Employee}
     */
    @Override
    public Employee getByNames(String firstName, String secondName) {
        for (Employee employee : database) {
            if (firstName.equals(employee.getFirstname()) && secondName.equals(employee.getSecondname())) {
                return employee;
            }
        }
        return null;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setValue(String key, Employee value) {

    }

    @Override
    public Employee getValue(String key) {
        return null;
    }
}
