package rs.rbt.internship.admin.service

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import rs.rbt.internship.admin.exception.CsvException
import rs.rbt.internship.admin.exception.WrongRecordsException
import rs.rbt.internship.admin.helper.patternMatches
import rs.rbt.internship.data.model.Employee
import rs.rbt.internship.data.model.Vacation
import rs.rbt.internship.data.service.implementation.EmployeeService
import rs.rbt.internship.data.service.implementation.VacationService
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat


@Service
class AdminService {
    companion object {
        private const val DATE_FORMAT = "EEE, MMM d, yyyy"
        private const val WRONG_RECORDS_MESSAGE = "File have wrong records. Correct record were written. "
        private const val EMPTY_FILE_MESSAGE = "File is empty."
        private const val EMAIL_REGEX_PATTERN = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"
        private const val PASSWORD_REGEX_PATTERN = "[A-Z]+[a-z]+[0-9]*[!|#|@|\$]+"
    }

    @Autowired
    lateinit var employeeService: EmployeeService

    @Autowired
    lateinit var vacationService: VacationService


    fun importEmployees(file: MultipartFile) {
        val loadedRecord: List<CSVRecord> = readFile(file)
        if (loadedRecord.isEmpty()) {
            throw CsvException(EMPTY_FILE_MESSAGE)
        }
        var wrongRecordExist = false

        loadedRecord.forEach {
            if(it.get(0) != "Employee Email" && it.get(0) != "Vacation year") {
                if (isValidEmail(it.get(0)) && isValidPassword(it.get(1)) && it.size() == 2) {
                    val employee = Employee(email = it.get(0), password = it.get(1))
                    employeeService.saveEmployee(employee)
                } else {
                    wrongRecordExist = true
                }
            }
        }

        if (wrongRecordExist) {
            throw WrongRecordsException(WRONG_RECORDS_MESSAGE)
        }
    }

    fun importNumberOfVacationDays(file: MultipartFile) {

        val loadedRecord: List<CSVRecord> = readFile(file)
        if (loadedRecord.isEmpty()) {
            throw CsvException(EMPTY_FILE_MESSAGE)
        }
        var year = ""
        var wrongRecordExist = false

        loadedRecord.forEach {
            if (it.get(0).equals("Vacation year")) {
                year = it.get(1)
            }
            if(it.get(0) != "Employee" && it.get(0) != "Vacation year") {
                if (isValidEmail(it.get(0)) && isNumeric(it.get(1)) && it.size() == 2) {
                    val employee: Employee = employeeService.findEmployeeByEmail(it.get(0))
                    val vacationDaysPerYear: MutableMap<String, Int> = employee.vacationDaysPerYear
                    vacationDaysPerYear[year] = it.get(1).toInt()
                    employee.vacationDaysPerYear = vacationDaysPerYear
                    employeeService.saveEmployee(employee)
                } else {
                    wrongRecordExist = true
                    println(it)
                }
            }
        }

        if (wrongRecordExist) {
            throw WrongRecordsException(WRONG_RECORDS_MESSAGE)
        }
    }

    fun importUsedVacationDate(file: MultipartFile) {

        val loadedRecord: List<CSVRecord> = readFile(file)
        var wrongRecordExist = false

        loadedRecord.forEach {
            if(it.get(0) != "Employee") {
                if (isValidEmail(it.get(0)) && isValidDate(it.get(1)) && isValidDate(it.get(2)) && it.size() == 3) {
                    val employee: Employee = employeeService.findEmployeeByEmail(it.get(0))
                    val formatter = SimpleDateFormat(DATE_FORMAT)
                    val vacation =
                        Vacation(
                            startDate = formatter.parse(it.get(1)),
                            endDate = formatter.parse(it.get(2)),
                            employee = employee
                        )
                    val savedVacation: Vacation = vacationService.saveVacation(vacation)
                    employee.vacationList.add(savedVacation)
                    employeeService.saveEmployee(employee)

                } else {
                    wrongRecordExist = true
                }
            }
        }
        if (wrongRecordExist) {
            throw WrongRecordsException(WRONG_RECORDS_MESSAGE)
        }
    }

    fun clearDatabase() {
        vacationService.deleteAll()
        employeeService.deleteAll()
    }

    private fun readFile(file: MultipartFile): List<CSVRecord> {
        val fileReader = BufferedReader(InputStreamReader(file.inputStream, StandardCharsets.UTF_8))
        val csvParser = CSVParser(fileReader, CSVFormat.DEFAULT)

        return csvParser.records
    }

    private fun isNumeric(toCheck: String): Boolean {

        return toCheck.all { char -> char.isDigit() }
    }

    private fun isValidEmail(email: String): Boolean {

        return patternMatches(email, EMAIL_REGEX_PATTERN)
    }

    private fun isValidDate(date: String): Boolean {
        //date format "EEE, MMM d, yyyy"
        val dateWithoutComma = date.replace(",", "")
        val splitDate = dateWithoutComma.split(" ")
        if(splitDate.size == 4){
            if (isValidDay(splitDate[0]) && isValidMonth(splitDate[1]) && isValidDayInMonth(
                    day = splitDate[2],
                    month = splitDate[1],
                    year = splitDate[3]
                ) && isValidYear(splitDate[3])
            ) {
                return true
            }
        }
        return false
    }

    private fun isValidDay(day: String): Boolean {
        val days = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")

        return days.contains(day)
    }

    private fun isValidMonth(month: String): Boolean {
        val months = listOf(
            "January", "February", "March", "April", "May", "June", "July", "August",
            "September", "October", "November", "December"
        )

        return months.contains(month)
    }

    private fun isValidDayInMonth(day: String, month: String, year: String): Boolean {
        val thirtyDaysGroup = listOf(
            "April", "June",
            "September", "November"
        )
        val thirtyOneDaysGroup = listOf(
            "January", "March", "May", "July", "August",
            "October", "December"
        )
        return if (thirtyDaysGroup.contains(month) && day.toInt() <= 30 && day.toInt() >= 1) {
            true
        } else if (thirtyOneDaysGroup.contains(month) && day.toInt() <= 31 && day.toInt() >= 1) {
            true
        } else if (month == "February" && day.toInt() >= 1) {
            if (isLeapYear(year) && day.toInt() <= 29) {
                true
            } else !isLeapYear(year) && day.toInt() <= 28
        } else {
            false
        }
    }

    private fun isValidYear(year: String): Boolean {
        if (isNumeric(year) && year.length == 4) {
            return true
        }
        return false
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

    private fun isValidPassword(password: String): Boolean{
        return patternMatches(password, PASSWORD_REGEX_PATTERN)
    }
}