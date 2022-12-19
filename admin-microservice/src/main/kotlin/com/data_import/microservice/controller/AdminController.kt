package com.data_import.microservice.controller

import com.data_import.microservice.service.AdminService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/admins")
class AdminController(val adminService: AdminService) {


    @PostMapping("/import-employees")
    fun importEmployees(@RequestParam("file") file: MultipartFile) {
        adminService.importEmployees(file);
    }

    @PostMapping("/import-total-vacation-days")
    fun importTotalVacationDays(@RequestParam("file") file: MultipartFile){
        adminService.importNumberOfVacationDays(file);
    }

    @PostMapping("/import-used-vacation-date")
    fun importUsedVacationDate(@RequestParam("file") file: MultipartFile){
        adminService.importUsedVacationDate(file)
    }

}