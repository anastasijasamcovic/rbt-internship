package rs.rbt.internship.search.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import rs.rbt.internship.data.dto.DaysDTO
import rs.rbt.internship.data.dto.VacationDTO
import rs.rbt.internship.data.extensions.toResponse
import rs.rbt.internship.data.model.Employee
import rs.rbt.internship.data.model.Vacation
import rs.rbt.internship.data.service.implementation.EmployeeService
import rs.rbt.internship.data.service.implementation.VacationService
import rs.rbt.internship.search.constants.DateConstants.Companion.DATE_FORMAT
import rs.rbt.internship.search.constants.DateConstants.Companion.END_BEFORE_START_DATE
import rs.rbt.internship.search.constants.DateConstants.Companion.INCORRECT_DATE
import rs.rbt.internship.search.constants.DateConstants.Companion.PAST_DATE
import rs.rbt.internship.search.constants.DateConstants.Companion.YEAR_NOT_FOUND
import rs.rbt.internship.search.exception.DateException
import rs.rbt.internship.search.exception.YearNotFoundException
import rs.rbt.internship.search.helper.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

@Service
class SearchService {

    @Autowired
    lateinit var employeeService: EmployeeService

    @Autowired
    lateinit var vacationService: VacationService

    fun getTotalVacationDaysPerYearForEmployee(year: String, employeeEmail: String): DaysDTO {
        val employee: Employee = employeeService.findEmployeeByEmail(employeeEmail)
        val vacationDaysPerYear = employee.vacationDaysPerYear
        if (!vacationDaysPerYear.keys.contains(year)) {
            throw YearNotFoundException(YEAR_NOT_FOUND + year)
        }

        return DaysDTO(vacationDaysPerYear.getValue(year))
    }

    fun getUsedVacationDaysPerYearForEmployee(year: String, employeeEmail: String): DaysDTO {
        val usedVacationDates: List<Vacation> = vacationService.getUsedVacationDaysPerYear(year, employeeEmail)
        var usedVacationDays: Long = 0
        usedVacationDates.forEach {
            usedVacationDays += TimeUnit.DAYS.convert(
                (it.endDate.time - it.startDate.time),
                TimeUnit.MILLISECONDS
            )
        }

        return DaysDTO(usedVacationDays.toInt())
    }

    fun getAvailableDaysPerYear(year: String, employeeEmail: String): DaysDTO {
        val totalDays: Int = getTotalVacationDaysPerYearForEmployee(year, employeeEmail).numberOfDays
        val usedDays: Int = getUsedVacationDaysPerYearForEmployee(year, employeeEmail).numberOfDays

        return DaysDTO(totalDays - usedDays)
    }

    fun getUsedVacationsForSpecificTimePeriodForEmployee(
        employeeEmail: String,
        fromDate: String,
        toDate: String
    ): List<VacationDTO> {
        val formatter = SimpleDateFormat(DATE_FORMAT)
        if (!isValidDate(fromDate)) {
            throw DateException(INCORRECT_DATE + fromDate)
        }
        if (!isValidDate(toDate)) {
            throw DateException(INCORRECT_DATE + toDate)
        }
        val startDate: Date = formatter.parse(fromDate)
        val endDate: Date = formatter.parse(toDate)
        if (endDate < startDate) {
            throw DateException(END_BEFORE_START_DATE)
        }
        val vacations: List<Vacation> =
            vacationService.getVacationsForSpecificTimePeriod(employeeEmail, startDate, endDate)

        return vacations.map { it.toResponse() }
    }

    fun addNewVacation(employeeEmail: String, vacationStartDate: String, vacationEndDate: String): VacationDTO {
        if (!isValidDate(vacationStartDate)) {
            throw DateException(INCORRECT_DATE + vacationStartDate)
        }
        if (!isValidDate(vacationEndDate)) {
            throw DateException(INCORRECT_DATE + vacationEndDate)
        }
        val formatter = SimpleDateFormat(DATE_FORMAT)
        val employee: Employee = employeeService.findEmployeeByEmail(employeeEmail)
        val startDateFormatted: Date = formatter.parse(vacationStartDate)
        val endDateFormatted: Date = formatter.parse(vacationEndDate)
        if (startDateFormatted < Date() || endDateFormatted < Date()) {
            throw DateException(PAST_DATE)
        }
        if (endDateFormatted < startDateFormatted) {
            throw DateException(END_BEFORE_START_DATE)
        }

        return vacationService.saveVacation(
            Vacation(
                startDate = startDateFormatted,
                endDate = endDateFormatted,
                employee = employee,
            )
        ).toResponse()
    }
}