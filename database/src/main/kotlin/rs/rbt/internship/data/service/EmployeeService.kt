package rs.rbt.internship.data.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import rs.rbt.internship.data.model.Employee
import rs.rbt.internship.data.repository.EmployeeRepository

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