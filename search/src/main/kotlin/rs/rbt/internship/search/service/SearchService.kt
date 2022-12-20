package rs.rbt.internship.search.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import rs.rbt.internship.data.model.Vacation

import rs.rbt.internship.data.service.EmployeeService
import rs.rbt.internship.data.service.VacationService
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.concurrent.TimeUnit
import rs.rbt.internship.data.dto.VacationDTO
import rs.rbt.internship.data.dto.toResponse
import rs.rbt.internship.data.model.Employee
import rs.rbt.internship.search.exception.DateException

@Service
class SearchService {
    @Autowired
    lateinit var employeeService: EmployeeService

    @Autowired
    lateinit var vacationService: VacationService

    fun getTotalVacationDaysPerYearForEmployee(year: String, employeeId:Long):Int{
        return employeeService.getTotalVacationDaysPerYear(year, employeeId)
    }

    fun getUsedVacationDaysPerYearForEmployee(year: String, employeeId: Long): Int{
        val usedVacationDates: List<Vacation> = vacationService.getUsedVacationDaysPerYear(year, employeeId)
        var usedVacationDays: Int = 0
        usedVacationDates.forEach{
            usedVacationDays += TimeUnit.DAYS.convert((it.endDate?.time?.minus(it.startDate?.time!!)!!), TimeUnit.MILLISECONDS).toInt()
        }
        return usedVacationDays
    }

    fun getAvailableDaysPerYear(year:String, employeeId: Long): Int{
        val totalDays: Int = getTotalVacationDaysPerYearForEmployee(year, employeeId)
        val usedDays: Int = getUsedVacationDaysPerYearForEmployee(year, employeeId)
        return totalDays - usedDays
    }

    fun getUsedVacationsForSpecificTimePeriodForEmployee(employeeId:Long, fromDate: String, toDate: String):List<VacationDTO>{
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val startDate:Date = formatter.parse(fromDate)
        val endDate: Date = formatter.parse(toDate)
        val vacations: List<Vacation> = vacationService.getListVacationsForSpecificTimePeriod(employeeId, startDate, endDate)
        return vacations.map { it.toResponse() }
    }

    fun addNewVacation(employeeId: Long, startDate: String, endDate: String): VacationDTO {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val employee: Employee = employeeService.findById(employeeId)
        val startDateFormatted: Date = formatter.parse(startDate)
        val endDateFormatted: Date = formatter.parse(endDate)
        if(startDateFormatted < Date() || endDateFormatted < Date()){
            throw DateException("Date can not be in the past!")
        }
        return vacationService.saveVacation(employee, startDateFormatted, endDateFormatted).toResponse()
    }


}