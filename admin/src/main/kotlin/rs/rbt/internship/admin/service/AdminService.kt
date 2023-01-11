package rs.rbt.internship.admin.service

import org.apache.commons.csv.CSVRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import rs.rbt.internship.admin.constants.CSVConstants
import rs.rbt.internship.admin.exception.WrongRecordsException
import rs.rbt.internship.admin.extensions.*
import rs.rbt.internship.admin.helper.*
import rs.rbt.internship.data.model.Employee
import rs.rbt.internship.data.model.Vacation
import rs.rbt.internship.data.service.implementation.EmployeeService
import rs.rbt.internship.data.service.implementation.VacationService
import java.text.SimpleDateFormat


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
            if (it.size() == 2) {
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
            if (it.size() == 2) {
                if (it.get(0) != CSVConstants.EMPLOYEE && it.get(0) != CSVConstants.VACATION_YEAR) {
                    if (isValidEmail(it.getEmail()) && isNumeric(it.getNumberOfVacationDays())) {
                        val employee: Employee = employeeService.findEmployeeByEmail(it.getEmail())
                        val vacationDaysPerYear: MutableMap<String, Int> = employee.vacationDaysPerYear
                        vacationDaysPerYear[year] = it.getNumberOfVacationDays().toInt()
                        employee.vacationDaysPerYear = vacationDaysPerYear
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

    fun importUsedVacationDate(file: MultipartFile) {
        val loadedRecord: List<CSVRecord> = readFile(file)
        var wrongRecordExist = false

        loadedRecord.forEach {
            if (it.size() == 3) {
                if (it.get(0) != CSVConstants.EMPLOYEE) {
                    if (isValidEmail(it.getEmail()) && isValidDate(it.getStartVacationDate()) && isValidDate(it.getEndVacationDate())) {
                        val employee: Employee = employeeService.findEmployeeByEmail(it.getEmail())
                        val formatter = SimpleDateFormat(CSVConstants.DATE_FORMAT)
                        val vacation =
                            Vacation(
                                startDate = formatter.parse(it.getStartVacationDate()),
                                endDate = formatter.parse(it.getStartVacationDate()),
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

}