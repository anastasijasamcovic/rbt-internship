package rs.rbt.internship.data.extensions

import rs.rbt.internship.data.constants.Constants.Companion.DATE_FORMAT
import java.text.SimpleDateFormat
import java.util.*

fun Date.formatDate(): String{
    val dateFormatter = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
    return dateFormatter.format(this)
}