package ru.tsystems.divider.context;

import ru.tsystems.divider.dao.api.EmployeeDao;
import ru.tsystems.divider.entity.Employee;

public class InMemoryEmployee extends InMemoryDaoGeneral<Employee> implements EmployeeDao {

    private static InMemoryEmployee dao;

    protected static InMemoryEmployee getInMemoryDao(){
        if(dao == null){
            dao = new InMemoryEmployee();
        }
        return dao;
    }

    private InMemoryEmployee() {
        super();
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
        for(Employee employee: database){
            if(firstName.equals(employee.getFirstname()) && secondName.equals(employee.getSecondname())){
                return employee;
            }
        }
        return null;
    }
}
