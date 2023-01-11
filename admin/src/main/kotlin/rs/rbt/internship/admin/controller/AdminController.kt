package rs.rbt.internship.admin.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import rs.rbt.internship.admin.service.AdminService
import rs.rbt.internship.data.dto.ExceptionDTO

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

    @PostMapping("/clearDatabase")
    fun clearDatabase() {

        adminService.clearDatabase()
    }

    @ExceptionHandler(Exception::class)
    fun handleException(exception: Exception): ResponseEntity<ExceptionDTO> {

        return ResponseEntity.ok(exception.message?.let {
            ExceptionDTO(message= it, status = 400, statusText = "BAD REQUEST")
        })


    }

}