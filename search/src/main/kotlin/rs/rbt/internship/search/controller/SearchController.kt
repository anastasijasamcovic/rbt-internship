package rs.rbt.internship.search.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import rs.rbt.internship.data.dto.VacationDTO
import rs.rbt.internship.data.model.Vacation
import rs.rbt.internship.search.exception.DateException
import rs.rbt.internship.search.service.SearchService

@RestController
@RequestMapping("/employee")
class SearchController {

    @Autowired
    lateinit var searchService: SearchService

    @GetMapping("/totalDaysPerYear/{employeeId}/{year}")
    fun getTotalNumberOfVacationDaysForEmployeePerYear(
        @PathVariable year: String,
        @PathVariable employeeId: Long
    ): Int {
        return searchService.getTotalVacationDaysPerYearForEmployee(year, employeeId)
    }

    @GetMapping("/usedVacationDaysPerYear/{employeeId}/{year}")
    fun getUsedNumberOfVacationDaysForEmployeePerYear(@PathVariable year: String, @PathVariable employeeId: Long): Int {
        return searchService.getUsedVacationDaysPerYearForEmployee(year, employeeId)
    }

    @GetMapping("/availableDaysPerYear/{employeeId}/{year}")
    fun getAvailableDaysPerYear(@PathVariable year: String, @PathVariable employeeId: Long): Int {
        return searchService.getAvailableDaysPerYear(year, employeeId)
    }

    @GetMapping("/usedVacations/{employeeId}/{fromDate}/{toDate}")
    fun getAvailableDaysPerYear(
        @PathVariable employeeId: Long,
        @PathVariable fromDate: String,
        @PathVariable toDate: String
    ): List<VacationDTO> {
        return searchService.getUsedVacationsForSpecificTimePeriodForEmployee(employeeId, fromDate, toDate)
    }

    @PostMapping("/addNewVacation/{employeeId}/{fromDate}/{toDate}")
    fun addNewVacation(
        @PathVariable employeeId: Long,
        @PathVariable fromDate: String,
        @PathVariable toDate: String
    ): ResponseEntity<VacationDTO> {

        return ResponseEntity.ok(searchService.addNewVacation(employeeId, fromDate, toDate))
    }

    @ExceptionHandler(DateException::class)
    fun invalidDate(exception: DateException): ResponseEntity<String> {

        return ResponseEntity.badRequest().body(exception.message)
    }

}