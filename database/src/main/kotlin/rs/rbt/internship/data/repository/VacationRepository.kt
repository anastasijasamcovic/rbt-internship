package rs.rbt.internship.data.repository

import rs.rbt.internship.data.model.Vacation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface VacationRepository: JpaRepository<Vacation, Long> {
    @Query("SELECT V FROM Vacation V LEFT JOIN FETCH Employee WHERE V.employee.email = :employee_email and EXTRACT(YEAR FROM V.startDate) = :year_vacation")
    fun findUsedVacationsPerYear(@Param("employee_email") employeeEmail: String, @Param("year_vacation") year: String): List<Vacation>

    @Query("SELECT V FROM Vacation V LEFT JOIN FETCH Employee WHERE V.employee.email = :employee_email and (V.startDate between :start_date_vacation and :end_date_vacation or V.endDate between  :start_date_vacation and :end_date_vacation) ")
    fun findUsedVacationsForSpecificTimePeriod(@Param("employee_email") employeeEmail:String, @Param("start_date_vacation") startDateVacation: Date, @Param("end_date_vacation") endDateVacation: Date):List<Vacation>
}