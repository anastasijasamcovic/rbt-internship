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
import rs.rbt.internship.search.exception.DateException
import rs.rbt.internship.search.exception.YearNotFoundException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

@Service
class SearchService {
    companion object {
        private const val DATE_FORMAT = "yyyy-MM-dd"
        private const val INCORRECT_DATE = "Date format is incorrect: "
        private const val YEAR_NOT_FOUND = "Not found vacation days for year "
        private const val PAST_DATE = "Date can not be in the past!"
        private const val END_BEFORE_START_DATE = "End date must be after start date!"
    }

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
        if(!isValidDate(fromDate)){
            throw DateException(INCORRECT_DATE + fromDate)
        }
        if(!isValidDate(toDate)){
            throw DateException(INCORRECT_DATE + toDate)
        }
        val startDate: Date = formatter.parse(fromDate)
        val endDate: Date = formatter.parse(toDate)
        val vacations: List<Vacation> =
            vacationService.getVacationsForSpecificTimePeriod(employeeEmail, startDate, endDate)

        return vacations.map { it.toResponse() }
    }

    fun addNewVacation(employeeEmail: String, vacationStartDate: String, vacationEndDate: String): VacationDTO {
        if(!isValidDate(vacationStartDate)){
            throw DateException(INCORRECT_DATE + vacationStartDate)
        }
        if(!isValidDate(vacationEndDate)){
            throw DateException(INCORRECT_DATE + vacationEndDate)
        }
        val formatter = SimpleDateFormat(DATE_FORMAT)
        val employee: Employee = employeeService.findEmployeeByEmail(employeeEmail)
        val startDateFormatted: Date = formatter.parse(vacationStartDate)
        val endDateFormatted: Date = formatter.parse(vacationEndDate)
        if (startDateFormatted < Date() || endDateFormatted < Date()) {
            throw DateException(PAST_DATE)
        }
        if(endDateFormatted < startDateFormatted){
            throw DateException(END_BEFORE_START_DATE)
        }

        return vacationService.saveVacation(Vacation(startDate = startDateFormatted, endDate = endDateFormatted, employee = employee, )).toResponse()
    }

    private fun isValidDate(date: String): Boolean {
        //expected: yyyy-MM-dd
        if(date == "" || !date.contains("-")){
            return false
        }
        val splitDate = date.split("-")
        if(splitDate.size != 3){
            return false
        }
        return (isValidYear(splitDate[0]) && isValidDateInMonth(
            day = splitDate[1],
            month = splitDate[2],
            year = splitDate[0]
        ))
    }

    private fun isValidYear(year: String): Boolean {
        if (isNumeric(year) && year.length == 4) {
            return true
        }
        return false
    }

    private fun isNumeric(toCheck: String): Boolean {

        return toCheck.all { char -> char.isDigit() }
    }

    private fun isValidDateInMonth(day: String, month: String, year: String): Boolean {
        val thirtyDaysGroup = listOf(
            "04", "06",
            "09", "11"
        )
        val thirtyOneDaysGroup = listOf(
            "01", "03", "05", "07", "08",
            "10", "12"
        )
        if (month.toInt() < 1 || month.toInt() > 12 || month.length != 2) {
            return false
        } else {

            return if (thirtyDaysGroup.contains(month) && day.toInt() <= 30 && day.toInt() >= 1) {
                true
            } else if (thirtyOneDaysGroup.contains(month) && day.toInt() <= 31 && day.toInt() >= 1) {
                true
            } else if (month == "02" && day.toInt() >= 1) {
                if (isLeapYear(year) && day.toInt() <= 29) {
                    true
                } else !isLeapYear(year) && day.toInt() <= 28
            } else {
                false
            }
        }
    }

    private fun isLeapYear(year: String): Boolean {

        val leap = if (year.toInt() % 4 == 0) {
            if (year.toInt() % 100 == 0) {
                year.toInt() % 400 == 0
            } else {
                true
            }
        } else {
            false
        }

        return leap
    }


}