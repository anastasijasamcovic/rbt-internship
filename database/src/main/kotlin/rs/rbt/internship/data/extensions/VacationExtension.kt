package rs.rbt.internship.data.extensions

import rs.rbt.internship.data.dto.VacationDTO
import rs.rbt.internship.data.model.Vacation

fun Vacation.toResponse() = VacationDTO(
    endDateFormatted = endDate.formatDate(),
    startDateFormatted = startDate.formatDate()
)