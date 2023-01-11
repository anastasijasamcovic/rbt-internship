package rs.rbt.internship.data.service.implementation

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import rs.rbt.internship.data.exception.EmployeeNotFoundException
import rs.rbt.internship.data.model.Employee
import rs.rbt.internship.data.repository.EmployeeRepository
import rs.rbt.internship.data.service.interfaces.IEmployeeService
import java.util.*

@Component
class EmployeeService : IEmployeeService {
    @Autowired
    lateinit var employeeRepository: EmployeeRepository

    override fun saveEmployee(employee: Employee) {
        employeeRepository.save(employee)
    }

    override fun findEmployeeByEmail(employeeEmail: String): Employee {
        val employee: Optional<Employee> = employeeRepository.findEmployeeByEmail(employeeEmail)
        if (!employee.isPresent) {
            throw EmployeeNotFoundException("Employee with email $employeeEmail not found.")
        }

        return employee.get()
    }

    override fun getTotalVacationDaysPerYear(year: String, employeeId: Long): Int {

        return employeeRepository.findVacationDaysForEmployeePerYear(year, employeeId)
    }

    override fun findEmployeeById(employeeId: Long): Employee {
        val employee: Optional<Employee> = employeeRepository.findById(employeeId)
        if (!employee.isPresent) {
            throw EmployeeNotFoundException("Employee with id $employeeId not found.")
        }

        return employee.get()
    }

    override fun deleteAll() {
        employeeRepository.deleteAll()
    }
}