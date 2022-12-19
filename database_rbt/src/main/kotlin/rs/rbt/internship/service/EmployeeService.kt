package rs.rbt.internship.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import rs.rbt.internship.model.Employee
import rs.rbt.internship.repository.EmployeeRepository

@Service
class EmployeeService {
    @Autowired
    lateinit var employeeRepository: EmployeeRepository

    fun saveEmployee(employee: Employee) {
        employeeRepository.save(employee)
    }

    fun findEmployeeByEmail(email: String): Employee {
        return employeeRepository.findByEmail(email)
    }

    fun getTotalVacationDaysPerYear(year:String, employeeId: Long):Int{
        return employeeRepository.getVacationDaysForEmployeePerYear(year, employeeId)
    }

    fun findById(employeeId: Long): Employee {
        return employeeRepository.findEmployeeById(employeeId)
    }

}