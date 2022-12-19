package rs.rbt.internship.dto

data class EmployeeDTO (
    var email: String,
    var password: String,
    var vacationDTOList: List<VacationDTO>,
    var vacationDaysPerYear: Map<String, Int>,
)