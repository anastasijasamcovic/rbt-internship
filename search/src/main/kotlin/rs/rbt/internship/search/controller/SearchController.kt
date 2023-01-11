package rs.rbt.internship.search.controller

import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import rs.rbt.internship.data.dto.DaysDTO
import rs.rbt.internship.data.dto.VacationDTO
import rs.rbt.internship.data.request.AddVacationRequest
import rs.rbt.internship.search.service.SearchService


@RestController
@RequestMapping("/employee")
class SearchController {

    @Autowired
    lateinit var searchService: SearchService

    @GetMapping("/totalDaysPerYear")
    fun getTotalNumberOfVacationDaysForEmployeePerYear(
        authentication: Authentication,
        @RequestParam @Valid @NotNull vacationYear: String
    ): ResponseEntity<DaysDTO> {
        return ResponseEntity.ok(
            searchService.getTotalVacationDaysPerYearForEmployee(
                vacationYear,
                authentication.name
            )
        )
    }

    @GetMapping("/usedVacationDaysPerYear")
    fun getUsedNumberOfVacationDaysForEmployeePerYear(
        authentication: Authentication,
        @RequestParam @Valid @NotNull vacationYear: String
    ): ResponseEntity<DaysDTO> {

        return ResponseEntity.ok(searchService.getUsedVacationDaysPerYearForEmployee(vacationYear, authentication.name))
    }

    @GetMapping("/availableDaysPerYear")
    fun getAvailableDaysForEmployeePerYear(
        authentication: Authentication,
        @RequestParam @Valid @NotNull vacationYear: String,
    ): ResponseEntity<DaysDTO> {

        return ResponseEntity.ok(searchService.getAvailableDaysPerYear(vacationYear, authentication.name))
    }

    @GetMapping("/usedVacations")
    fun getUsedVacationsForSpecificTimePeriodForEmployee(
        authentication: Authentication,
        @RequestParam @Valid @NotNull vacationStartDate: String,
        @RequestParam @Valid @NotNull vacationEndDate: String
    ): ResponseEntity<List<VacationDTO>> {

        return ResponseEntity.ok(
            searchService.getUsedVacationsForSpecificTimePeriodForEmployee(
                authentication.name,
                vacationStartDate,
                vacationEndDate
            )
        )

    }

    @PostMapping("/addNewVacation")
    fun addNewVacation(authentication: Authentication,
        @RequestBody request: AddVacationRequest
    ): ResponseEntity<VacationDTO> {

        return ResponseEntity.ok(
            searchService.addNewVacation(
                authentication.name,
                request.vacationStartDate,
                request.vacationEndDate
            )
        )
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleException(exception: Exception): ResponseEntity<String> {

        return ResponseEntity.badRequest().body(exception.message)
    }

}