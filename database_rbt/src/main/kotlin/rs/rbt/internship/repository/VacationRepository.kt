package rs.rbt.internship.repository

import rs.rbt.internship.model.Vacation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface VacationRepository: JpaRepository<Vacation, Long> {
    @Query("SELECT V FROM Vacation V LEFT JOIN FETCH Employee WHERE V.employee.id = :employee_id and EXTRACT(YEAR FROM V.startDate) = :year")
    fun getUsedVacationsPerYear(@Param("employee_id") employeeId: Long, @Param("year") year: String): List<Vacation>

    @Query("SELECT V FROM Vacation V LEFT JOIN FETCH Employee WHERE V.employee.id = :employee_id and (V.startDate between :from_date and :to_date or V.endDate between  :from_date and :to_date) ")
    fun getUsedVacationsForSpecificTimePeriod(@Param("employee_id") employeeId:Long, @Param("from_date") fromDate: Date, @Param("to_date") toDate: Date):List<Vacation>
}