package rs.rbt.internship.data.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.formatDate(): String{
    val dateFormatter = SimpleDateFormat("dd.MM.YYYY.", Locale.getDefault())
    return dateFormatter.format(this)
}