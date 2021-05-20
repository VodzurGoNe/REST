package org.gruzdov.spring.rest.dao;

import org.gruzdov.spring.rest.entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class EmployeeDAOImpl implements EmployeeDAO {
    private SessionFactory factory;

    @Autowired
    public EmployeeDAOImpl(SessionFactory sessionFactory) {
        this.factory = sessionFactory;
    }

    @Override
    public List<Employee> getAllEmployees() {
        Session session = factory.getCurrentSession();
        List<Employee> allEmployees = session.createQuery("from Employee "
                , Employee.class).getResultList();

        return allEmployees;
    }

    @Override
    public void saveEmployee(Employee employee) {
        Session session = factory.getCurrentSession();

        session.saveOrUpdate(employee);
    }

    @Override
    public Employee getEmployee(Long id) {
        Session session = factory.getCurrentSession();

        Employee employee = session.get(Employee.class, id);
        return employee;
    }

    @Override
    public void deleteEmployee(Long id) {
        Session session = factory.getCurrentSession();

        Query<Employee> query = session.createQuery("delete from Employee " +
                "where id =:employeeId");

        query.setParameter("employeeId", id);
        query.executeUpdate();
    }
}
