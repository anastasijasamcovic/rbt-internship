package rs.rbt.internship.repository

import rs.rbt.internship.model.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRepository: JpaRepository<Employee, Long> {
    @Query(value="select * from employee where email=?1", nativeQuery = true)
    fun findByEmail(email: String): Employee

    @Query(value="select vd.vacation_days from vacations_days_per_year vd where vd.year=?1 and vd.employee_id=?2", nativeQuery = true)
    fun getVacationDaysForEmployeePerYear(year:String, employeeId: Long): Int

    @Query(value="select * from employee where id=?1", nativeQuery = true)
    fun findEmployeeById(employeeId: Long):Employee
}