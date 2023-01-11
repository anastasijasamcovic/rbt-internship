package rs.rbt.internship.data.service.interfaces

import org.springframework.stereotype.Service
import rs.rbt.internship.data.model.Vacation
import java.util.*

@Service
interface IVacationService {
    fun getUsedVacationDaysPerYear(year: String, employeeEmail: String): List<Vacation>
    fun getVacationsForSpecificTimePeriod(employeeEmail: String, fromDate: Date, toDate: Date): List<Vacation>
    fun saveVacation(vacation: Vacation): Vacation
    fun deleteAll()
}