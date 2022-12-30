package rs.rbt.internship.data.service.interfaces

import org.springframework.stereotype.Service
import rs.rbt.internship.data.model.Employee

@Service
interface IEmployeeService {
    fun saveEmployee(employee: Employee)

    fun findEmployeeByEmail(employeeEmail: String): Employee

    fun getTotalVacationDaysPerYear(year:String, employeeId: Long):Int

    fun findEmployeeById(employeeId: Long): Employee

    fun deleteAll()

}