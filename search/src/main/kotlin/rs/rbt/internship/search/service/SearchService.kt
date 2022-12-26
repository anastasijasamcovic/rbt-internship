package rs.rbt.internship.search.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import rs.rbt.internship.data.dto.VacationDTO
import rs.rbt.internship.data.extensions.toResponse
import rs.rbt.internship.data.model.Employee
import rs.rbt.internship.data.model.Vacation
import rs.rbt.internship.data.service.implementation.EmployeeService
import rs.rbt.internship.data.service.implementation.VacationService
import rs.rbt.internship.search.exception.DateException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

@Service
class SearchService {

    @Autowired
    lateinit var employeeService: EmployeeService

    @Autowired
    lateinit var vacationService: VacationService

    fun getTotalVacationDaysPerYearForEmployee(year: String, employeeEmail: String): Int {
        val employee: Employee = employeeService.findEmployeeByEmail(employeeEmail)

        return employeeService.getTotalVacationDaysPerYear(year, employee.id)
    }

    fun getUsedVacationDaysPerYearForEmployee(year: String, employeeEmail: String): Int {
        val usedVacationDates: List<Vacation> = vacationService.getUsedVacationDaysPerYear(year, employeeEmail)
        var usedVacationDays: Long = 0
        usedVacationDates.forEach {
            usedVacationDays += TimeUnit.DAYS.convert(
                (it.endDate.time - it.startDate.time),
                TimeUnit.MILLISECONDS)
        }

        return usedVacationDays.toInt()
    }

    fun getAvailableDaysPerYear(year: String, employeeEmail: String): Int {
        val totalDays: Int = getTotalVacationDaysPerYearForEmployee(year, employeeEmail)
        val usedDays: Int = getUsedVacationDaysPerYearForEmployee(year, employeeEmail)

        return totalDays - usedDays
    }

    fun getUsedVacationsForSpecificTimePeriodForEmployee(
        employeeEmail: String,
        fromDate: String,
        toDate: String
    ): List<VacationDTO> {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val startDate: Date = formatter.parse(fromDate)
        val endDate: Date = formatter.parse(toDate)
        val vacations: List<Vacation> =
            vacationService.getVacationsForSpecificTimePeriod(employeeEmail, startDate, endDate)

        return vacations.map { it.toResponse() }
    }

    fun addNewVacation(employeeEmail: String, vacationStartDate: String, vacationEndDate: String): VacationDTO {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val employee: Employee = employeeService.findEmployeeByEmail(employeeEmail)
        val startDateFormatted: Date = formatter.parse(vacationStartDate)
        val endDateFormatted: Date = formatter.parse(vacationEndDate)
        if (startDateFormatted < Date() || endDateFormatted < Date()) {
            throw DateException("Date can not be in the past!")
        }

        return vacationService.saveVacation(employee, startDateFormatted, endDateFormatted).toResponse()
    }


}