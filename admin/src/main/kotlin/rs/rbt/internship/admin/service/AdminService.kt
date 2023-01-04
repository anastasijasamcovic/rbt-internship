package rs.rbt.internship.admin.service

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import org.apache.commons.io.FilenameUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import rs.rbt.internship.admin.constants.CSVConstants
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
import rs.rbt.internship.admin.constants.CSVConstants.*
import rs.rbt.internship.admin.extensions.*


@Service
class AdminService {

    @Autowired
    lateinit var employeeService: EmployeeService

    @Autowired
    lateinit var vacationService: VacationService


    fun importEmployees(file: MultipartFile) {

        val loadedRecord: List<CSVRecord> = readFile(file)
        var wrongRecordExist = false

        loadedRecord.forEach {
            if(it.size() == 2) {
                if (it.get(0) != CSVConstants.EMPLOYEE_EMAIL && it.get(0) != CSVConstants.VACATION_YEAR) {
                    if (isValidEmail(it.getEmail()) && isValidPassword(it.getPassword())) {
                        val employee = Employee(email = it.getEmail(), password = it.getPassword())
                        employeeService.saveEmployee(employee)
                    } else {
                        wrongRecordExist = true
                    }
                }
            }
        }

        if (wrongRecordExist) {
            throw WrongRecordsException(CSVConstants.WRONG_RECORDS_MESSAGE)
        }
    }

    fun importNumberOfVacationDays(file: MultipartFile) {
        val loadedRecord: List<CSVRecord> = readFile(file)

        var year = ""
        var wrongRecordExist = false

        loadedRecord.forEach {
            if (it.get(0).equals(CSVConstants.VACATION_YEAR)) {
                year = it.get(1)
            }
            if(it.size() == 2) {
                if (it.get(0) != CSVConstants.EMPLOYEE && it.get(0) != CSVConstants.VACATION_YEAR) {
                    if (isValidEmail(it.getEmail()) && isNumeric(it.getNumberOfVacationDays())) {
                        val employee: Employee = employeeService.findEmployeeByEmail(it.getEmail())
                        val vacationDaysPerYear: MutableMap<String, Int> = employee.vacationDaysPerYear
                        vacationDaysPerYear[year] = it.getNumberOfVacationDays().toInt()
                        employee.vacationDaysPerYear = vacationDaysPerYear
                        employeeService.saveEmployee(employee)
                    } else {
                        wrongRecordExist = true
                        println(it)
                    }
                }
            }
        }

        if (wrongRecordExist) {
            throw WrongRecordsException(CSVConstants.WRONG_RECORDS_MESSAGE)
        }
    }

    fun importUsedVacationDate(file: MultipartFile) {
        val loadedRecord: List<CSVRecord> = readFile(file)
        var wrongRecordExist = false

        loadedRecord.forEach {
            if(it.size() == 3){
            if(it.get(0) != CSVConstants.EMPLOYEE) {
                if (isValidEmail(it.getEmail()) && isValidDate(it.getStartVacationDate()) && isValidDate(it.getEndVacationDate())) {
                    val employee: Employee = employeeService.findEmployeeByEmail(it.get(0))
                    val formatter = SimpleDateFormat(CSVConstants.DATE_FORMAT)
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
        }
        if (wrongRecordExist) {
            throw WrongRecordsException(CSVConstants.WRONG_RECORDS_MESSAGE)
        }
    }

    fun clearDatabase() {
        vacationService.deleteAll()
        employeeService.deleteAll()
    }

    private fun readFile(file: MultipartFile): List<CSVRecord> {
        isCSVFile(file)
        val fileReader = BufferedReader(InputStreamReader(file.inputStream, StandardCharsets.UTF_8))
        val csvParser = CSVParser(fileReader, CSVFormat.DEFAULT)
        val loadedRecords: List<CSVRecord> = csvParser.records
        if(loadedRecords.isEmpty()){
            throw CsvException(CSVConstants.EMPTY_FILE_MESSAGE)
        }

        return loadedRecords
    }

    private fun isCSVFile(file: MultipartFile){
        val fileExtension = FilenameUtils.getExtension(file.originalFilename)

        if (fileExtension != CSVConstants.ALLOWED_FILE_EXTENSION) {
            throw CsvException(CSVConstants.INVALID_FILE_EXTENSION)
        }
    }

    private fun isNumeric(toCheck: String): Boolean {

        return toCheck.all { char -> char.isDigit() }
    }

    private fun isValidEmail(email: String): Boolean {

        return patternMatches(email, CSVConstants.EMAIL_REGEX_PATTERN)
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
        return patternMatches(password, CSVConstants.PASSWORD_REGEX_PATTERN)
    }
}