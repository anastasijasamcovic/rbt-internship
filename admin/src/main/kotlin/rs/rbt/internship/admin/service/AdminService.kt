package rs.rbt.internship.admin.service

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import rs.rbt.internship.data.model.Employee
import rs.rbt.internship.data.model.Vacation
import rs.rbt.internship.data.service.EmployeeService
import rs.rbt.internship.data.service.VacationService
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat

@Service
class AdminService {
    @Autowired
    lateinit var employeeService: EmployeeService

    @Autowired
    lateinit var vacationService: VacationService

    fun importEmployees(file: MultipartFile) {
        var fileReader: BufferedReader? = null
        var csvParser: CSVParser? = null
        try {
            fileReader = BufferedReader(InputStreamReader(file.inputStream, StandardCharsets.UTF_8))
            csvParser = CSVParser(fileReader, CSVFormat.DEFAULT.withHeader())

            val loadedRecord: List<CSVRecord> = csvParser.records

            loadedRecord.forEach {
                if (it.recordNumber != 1L) {
                    val days: HashMap<String, Int> = HashMap<String, Int>()
                    val employee: Employee = Employee(email = it.get(0), password = it.get(1))
                    employeeService.saveEmployee(employee)
                }
            }
        } catch (exception: IOException) {
            throw RuntimeException(exception)
        }
    }

    fun importNumberOfVacationDays(file: MultipartFile) {
        var fileReader: BufferedReader? = null
        var csvParser: CSVParser? = null
        try {
            fileReader = BufferedReader(InputStreamReader(file.inputStream, StandardCharsets.UTF_8))
            csvParser = CSVParser(fileReader, CSVFormat.DEFAULT)


            val loadedRecord: List<CSVRecord> = csvParser.records
            val year: String = loadedRecord[0].get(1)

            loadedRecord.forEach {
                if (it.recordNumber != 1L && it.recordNumber != 2L) {
                    val employee: Employee = employeeService.findEmployeeByEmail(it.get(0))
                    val vacationDaysPerYear: MutableMap<String, Int> = employee.vacationDaysPerYear
                    vacationDaysPerYear[year] = it.get(1).toInt()
                    employee.vacationDaysPerYear = vacationDaysPerYear
                    employeeService.saveEmployee(employee)
                }
            }
        } catch (exception: IOException) {
            throw RuntimeException(exception)
        }
    }

    fun importUsedVacationDate(file: MultipartFile) {
        var fileReader: BufferedReader? = null
        var csvParser: CSVParser? = null
        try {
            fileReader = BufferedReader(InputStreamReader(file.inputStream, StandardCharsets.UTF_8))
            csvParser = CSVParser(fileReader, CSVFormat.DEFAULT.withHeader())


            val loadedRecord: List<CSVRecord> = csvParser.records

            loadedRecord.forEach {
                if (it.recordNumber != 1L) {
                    val employee: Employee = employeeService.findEmployeeByEmail(it.get(0))
                    val formatter = SimpleDateFormat("EEE, MMM d, yyyy")
                    val vacation: Vacation =
                        Vacation(
                            startDate = formatter.parse(it.get(1)),
                            endDate = formatter.parse(it.get(2)),
                            employee = employee
                        )
                    val savedVacation: Vacation = vacationService.saveVacation(vacation)
                    employee.vacationList.add(savedVacation)
                    employeeService.saveEmployee(employee)

                }
            }
        } catch (exception: IOException) {
            throw RuntimeException(exception)
        }
    }
}