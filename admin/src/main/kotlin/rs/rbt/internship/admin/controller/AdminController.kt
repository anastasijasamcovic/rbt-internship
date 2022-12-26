package rs.rbt.internship.admin.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import rs.rbt.internship.admin.exception.CsvException
import rs.rbt.internship.admin.service.AdminService

@RestController
@RequestMapping("/admins")
class AdminController(val adminService: AdminService) {


    @PostMapping("/import-employees")
    fun importEmployees(@RequestParam("file") file: MultipartFile) {
        adminService.importEmployees(file)
    }

    @PostMapping("/import-total-vacation-days")
    fun importTotalVacationDays(@RequestParam("file") file: MultipartFile) {
        adminService.importNumberOfVacationDays(file)
    }

    @PostMapping("/import-used-vacation-date")
    fun importUsedVacationDate(@RequestParam("file") file: MultipartFile) {
        adminService.importUsedVacationDate(file)
    }


    @ExceptionHandler(CsvException::class)
    fun invalidDate(exception: CsvException): ResponseEntity<String> {

        return ResponseEntity.badRequest().body(exception.message)
    }

}