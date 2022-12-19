package rs.rbt.internship.datasearch.controller

import org.springframework.beans.factory.annotation.Autowired
import rs.rbt.internship.model.Vacation
import rs.rbt.internship.dto.VacationDTO
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PathVariable
import rs.rbt.internship.datasearch.service.SearchService

@RestController
@RequestMapping("/employee")
class SearchController {

    @Autowired
    lateinit var searchService: SearchService

//    @GetMapping("/totalDaysPerYear/{employeeId}/{year}")
//    fun getTotalNumberOfVacationDaysForEmployeePerYear(
//        @PathVariable year: String,
//        @PathVariable employeeId: Long
//    ): Int {
//        return searchService.getTotalVacationDaysPerYearForEmployee(year, employeeId)
//    }


//    @GetMapping("/usedVacationDaysPerYear/{employeeId}/{year}")
//    fun getUsedNumberOfVacationDaysForEmployeePerYear(@PathVariable year: String, @PathVariable employeeId: Long): Int {
//        return searchService.getUsedVacationDaysPerYearForEmployee(year, employeeId)
//    }
//
//    @GetMapping("/availableDaysPerYear/{employeeId}/{year}")
//    fun getAvailableDaysPerYear(@PathVariable year: String, @PathVariable employeeId: Long):Int{
//        return searchService.getAvailableDaysPerYear(year, employeeId)
//    }
//
//    @GetMapping("/usedVacations/{employeeId}/{fromDate}/{toDate}") //dd-MM-yyyy kao string datuma
//    fun getAvailableDaysPerYear(@PathVariable employeeId: Long, @PathVariable fromDate: String, @PathVariable toDate:String):List<VacationDTO>{
//        return searchService.getUsedVacationsForSpecificTimePeriodForEmployee(employeeId, fromDate, toDate);
//    }
//
//    @PostMapping("/addNewVacation/{employeeId}/{fromDate}/{toDate}")
//    fun addNewVacation(@PathVariable employeeId: Long, @PathVariable fromDate: String, @PathVariable toDate:String):Vacation?{
//        return searchService.addNewVacation(employeeId, fromDate, toDate)
//    }

}