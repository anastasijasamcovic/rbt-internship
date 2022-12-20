package rs.rbt.internship.data.dto

import rs.rbt.internship.data.model.Vacation
import java.util.Date

class VacationDTO (
    var endDateFormatted: Date?,
    var startDateFormatted: Date?,
)

fun Vacation.toResponse() = VacationDTO(
    endDateFormatted = endDate,
    startDateFormatted = startDate
)