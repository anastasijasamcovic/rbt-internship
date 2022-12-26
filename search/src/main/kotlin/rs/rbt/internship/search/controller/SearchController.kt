package rs.rbt.internship.search.controller

import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import rs.rbt.internship.data.dto.VacationDTO
import rs.rbt.internship.data.exception.EmployeeNotFoundException
import rs.rbt.internship.search.exception.DateException
import rs.rbt.internship.search.service.SearchService


@RestController
@RequestMapping("/employee")
class SearchController {

    @Autowired
    lateinit var searchService: SearchService

    @GetMapping("/totalDaysPerYear/{vacationYear}")
    fun getTotalNumberOfVacationDaysForEmployeePerYear(
        @PathVariable @Valid @NotNull vacationYear: String
    ): ResponseEntity<Int> {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        val loggedEmail: String = authentication.name

        return ResponseEntity.ok(searchService.getTotalVacationDaysPerYearForEmployee(vacationYear, loggedEmail))
    }

    @GetMapping("/usedVacationDaysPerYear/{vacationYear}")
    fun getUsedNumberOfVacationDaysForEmployeePerYear(
        @PathVariable @Valid @NotNull vacationYear: String
    ): ResponseEntity<Int> {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        val loggedEmail: String = authentication.name

        return ResponseEntity.ok(searchService.getUsedVacationDaysPerYearForEmployee(vacationYear, loggedEmail))
    }

    @GetMapping("/availableDaysPerYear/{vacationYear}")
    fun getAvailableDaysForEmployeePerYear(
        @PathVariable @Valid @NotNull vacationYear: String,
    ): ResponseEntity<Int> {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        val loggedEmail: String = authentication.name

        return ResponseEntity.ok(searchService.getAvailableDaysPerYear(vacationYear, loggedEmail))
    }

    @GetMapping("/usedVacations/{vacationStartDate}/{vacationEndDate}")
    fun getUsedVacationsForSpecificTimePeriodForEmployee(
        @PathVariable @Valid @NotNull vacationStartDate: String,
        @PathVariable @Valid @NotNull vacationEndDate: String
    ): ResponseEntity<List<VacationDTO>> {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        val loggedEmail: String = authentication.name

        return ResponseEntity.ok(
            searchService.getUsedVacationsForSpecificTimePeriodForEmployee(
                loggedEmail,
                vacationStartDate,
                vacationEndDate
            )
        )
    }

    @PostMapping("/addNewVacation/{vacationStartDate}/{vacationEndDate}")
    fun addNewVacation(
        @PathVariable vacationStartDate: String,
        @PathVariable vacationEndDate: String
    ): ResponseEntity<VacationDTO> {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        val loggedEmail: String = authentication.name

        return ResponseEntity.ok(searchService.addNewVacation(loggedEmail, vacationStartDate, vacationEndDate))
    }

    @ExceptionHandler(*[DateException::class, EmployeeNotFoundException::class])
    fun invalidDate(exception: Exception): ResponseEntity<String> {

        return ResponseEntity.badRequest().body(exception.message)
    }



}