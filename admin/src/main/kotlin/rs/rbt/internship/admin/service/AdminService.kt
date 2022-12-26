package rs.rbt.internship.admin.service

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import rs.rbt.internship.admin.exception.CsvException
import rs.rbt.internship.data.model.Employee
import rs.rbt.internship.data.model.Vacation
import rs.rbt.internship.data.service.EmployeeService
import rs.rbt.internship.data.service.VacationService
import java.io.BufferedReader
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
        val loadedRecord: List<CSVRecord> = readFile(file)

        loadedRecord.forEach {
            if (it.get(0).equals("") || it.get(1).equals("")) {
                throw CsvException("Data not found")
            }
            if (it.recordNumber != 1L || it.recordNumber != 2L) {
                val employee = Employee(email = it.get(0), password = it.get(1))
                employeeService.saveEmployee(employee)
            }
        }
    }

    fun importNumberOfVacationDays(file: MultipartFile) {

        val loadedRecord: List<CSVRecord> = readFile(file)
        if (loadedRecord.isEmpty()) {
            throw CsvException("File is empty.")
        }
        val year: String = loadedRecord[0].get(1)

        loadedRecord.forEach {
            if (it.get(0).equals("") || it.get(1).equals("")) {
                throw CsvException("Data not found.")
            }
            if (it.recordNumber != 1L && it.recordNumber != 2L) {
                val employee: Employee = employeeService.findEmployeeByEmail(it.get(0))
                val vacationDaysPerYear: MutableMap<String, Int> = employee.vacationDaysPerYear
                vacationDaysPerYear[year] = it.get(1).toInt()
                employee.vacationDaysPerYear = vacationDaysPerYear
                employeeService.saveEmployee(employee)
            }
        }
    }

    fun importUsedVacationDate(file: MultipartFile) {

        val loadedRecord: List<CSVRecord> = readFile(file)

        loadedRecord.forEach {
            if (it.get(0).equals("") || it.get(1).equals("")) {
                throw CsvException("Data not found.")
            }
            if (it.recordNumber != 1L) {
                val employee: Employee = employeeService.findEmployeeByEmail(it.get(0))
                val formatter = SimpleDateFormat("EEE, MMM d, yyyy")
                val vacation =
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
    }

    private fun readFile(file: MultipartFile): List<CSVRecord> {
        val fileReader = BufferedReader(InputStreamReader(file.inputStream, StandardCharsets.UTF_8))
        val csvParser = CSVParser(fileReader, CSVFormat.DEFAULT)

        return csvParser.records
    }
}