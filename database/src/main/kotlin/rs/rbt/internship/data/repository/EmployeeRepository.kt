package rs.rbt.internship.data.repository

import rs.rbt.internship.data.model.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface EmployeeRepository: JpaRepository<Employee, Long> {

    @Query(value="select vd.vacation_days from vacations_days_per_year vd where vd.year=?1 and vd.employee_id=?2", nativeQuery = true)
    fun findVacationDaysForEmployeePerYear(yearVacation:String, employeeId: Long): Int

    fun findEmployeeByEmail(employeeEmail: String): Optional<Employee>

}