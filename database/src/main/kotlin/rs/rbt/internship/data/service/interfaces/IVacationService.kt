package rs.rbt.internship.data.service.interfaces

import org.springframework.stereotype.Service
import rs.rbt.internship.data.model.Employee
import rs.rbt.internship.data.model.Vacation
import java.util.*

@Service
interface IVacationService {
    fun getUsedVacationDaysPerYear(year: String, employee: Long): List<Vacation>
    fun getVacationsForSpecificTimePeriod(employeeId: Long, fromDate: Date, toDate: Date):List<Vacation>
    fun saveVacation(employee: Employee, startDate: Date, endDate: Date):Vacation
}