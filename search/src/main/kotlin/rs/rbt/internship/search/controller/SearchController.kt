package rs.rbt.internship.search.controller

import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import rs.rbt.internship.data.dto.VacationDTO
import rs.rbt.internship.search.exception.DateException
import rs.rbt.internship.search.service.SearchService


@RestController
@RequestMapping("/employee")
class SearchController {

    @Autowired
    lateinit var searchService: SearchService

    @GetMapping("/totalDaysPerYear/{employeeId}/{vacationYear}")
    fun getTotalNumberOfVacationDaysForEmployeePerYear(
        @PathVariable @Valid @NotNull vacationYear: String,
        @PathVariable @Valid @NotNull employeeId: Long
    ): ResponseEntity<Int> {

//        val authentication: Authentication = SecurityContextHolder.getContext().authentication
//        val currentPrincipalName: String = authentication.name
//        println(currentPrincipalName)
        return ResponseEntity.ok(searchService.getTotalVacationDaysPerYearForEmployee(vacationYear, employeeId))
    }

    @GetMapping("/usedVacationDaysPerYear/{employeeId}/{vacationYear}")
    fun getUsedNumberOfVacationDaysForEmployeePerYear(
        @PathVariable @Valid @NotNull vacationYear: String,
        @PathVariable @Valid @NotNull employeeId: Long
    ): ResponseEntity<Int> {

        return ResponseEntity.ok(searchService.getUsedVacationDaysPerYearForEmployee(vacationYear, employeeId))
    }

    @GetMapping("/availableDaysPerYear/{employeeId}/{vacationYear}")
    fun getAvailableDaysForEmployeePerYear(
        @PathVariable @Valid @NotNull vacationYear: String,
        @PathVariable @Valid @NotNull employeeId: Long
    ): ResponseEntity<Int> {

        return ResponseEntity.ok(searchService.getAvailableDaysPerYear(vacationYear, employeeId))
    }

    @GetMapping("/usedVacations/{employeeId}/{vacationStartDate}/{vacationEndDate}")
    fun getUsedVacationsForSpecificTimePeriodForEmployee(
        @PathVariable @Valid @NotNull employeeId: Long,
        @PathVariable @Valid @NotNull vacationStartDate: String,
        @PathVariable @Valid @NotNull vacationEndDate: String
    ): ResponseEntity<List<VacationDTO>> {

        return ResponseEntity.ok(searchService.getUsedVacationsForSpecificTimePeriodForEmployee(employeeId, vacationStartDate, vacationEndDate))
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