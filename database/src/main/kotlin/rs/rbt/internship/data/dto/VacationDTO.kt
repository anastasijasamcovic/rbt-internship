package rs.rbt.internship.data.dto

import rs.rbt.internship.data.model.Vacation
import java.text.SimpleDateFormat
import java.util.*

class VacationDTO (
    var endDateFormatted:   String,
    var startDateFormatted: String,
)

fun Vacation.toResponse() = VacationDTO(
    endDateFormatted = formatDate(endDate),
    startDateFormatted = formatDate(startDate)
)

fun formatDate(date: Date): String{
    val dateFormatter = SimpleDateFormat("dd.MM.YYYY.", Locale.getDefault())

    //return the formatted date string
    return dateFormatter.format(date)
}