package rs.rbt.internship.search.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import rs.rbt.internship.data.model.Vacation

import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit
import rs.rbt.internship.data.dto.VacationDTO
import rs.rbt.internship.data.extensions.toResponse
import rs.rbt.internship.data.model.Employee
import rs.rbt.internship.data.service.implementation.EmployeeService
import rs.rbt.internship.data.service.implementation.VacationService
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
            //Duration.between(it.startDate.toInstant(), it.endDate)
            usedVacationDays += TimeUnit.DAYS.convert((it.endDate?.time?.minus(it.startDate.time!!)!!), TimeUnit.MILLISECONDS).toInt()
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
        val vacations: List<Vacation> = vacationService.getVacationsForSpecificTimePeriod(employeeId, startDate, endDate)

        return vacations.map { it.toResponse() }
    }

    fun addNewVacation(employeeId: Long, vacationStartDate: String, vacationEndDate: String): VacationDTO {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val employee: Employee = employeeService.findEmployeeById(employeeId)
        val startDateFormatted: Date = formatter.parse(vacationStartDate)
        val endDateFormatted: Date = formatter.parse(vacationEndDate)
        if(startDateFormatted < Date() || endDateFormatted < Date()){
            throw DateException("Date can not be in the past!")
        }

        return vacationService.saveVacation(employee, startDateFormatted, endDateFormatted).toResponse()
    }


}