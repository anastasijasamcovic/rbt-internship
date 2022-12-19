package rs.rbt.internship.dto

import rs.rbt.internship.model.Vacation
import java.util.Date

class VacationDTO (
    var endDateFormatted: Date?,
    var startDateFormatted: Date?,
)

fun Vacation.toResponse() = VacationDTO(
    endDateFormatted = endDate,
    startDateFormatted = startDate
)