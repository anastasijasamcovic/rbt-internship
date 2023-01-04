package rs.rbt.internship.admin.helper

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import org.apache.commons.io.FilenameUtils
import org.springframework.web.multipart.MultipartFile
import rs.rbt.internship.admin.constants.CSVConstants
import rs.rbt.internship.admin.exception.CsvException
import rs.rbt.internship.admin.service.AdminService
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import rs.rbt.internship.admin.constants.CSVConstants.*


 fun isCSVFile(file: MultipartFile){
    val fileExtension = FilenameUtils.getExtension(file.originalFilename)

    if (fileExtension != CSVConstants.ALLOWED_FILE_EXTENSION) {
        throw CsvException(CSVConstants.INVALID_FILE_EXTENSION)
    }
}

fun isNumeric(toCheck: String): Boolean {

    return toCheck.all { char -> char.isDigit() }
}

fun isValidEmail(email: String): Boolean {

    return patternMatches(email, CSVConstants.EMAIL_REGEX_PATTERN)
}

fun isValidDate(date: String): Boolean {
    //date format "EEE, MMM d, yyyy"
    val dateWithoutComma = date.replace(",", "")
    val splitDate = dateWithoutComma.split(" ")
    if(splitDate.size == 4){
        if (isValidDay(splitDate[0]) && isValidMonth(splitDate[1]) && isValidDayInMonth(
                day = splitDate[2],
                month = splitDate[1],
                year = splitDate[3]
            ) && isValidYear(splitDate[3])
        ) {
            return true
        }
    }
    return false
}

fun isValidDay(day: String): Boolean {
    val days = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")

    return days.contains(day)
}

fun isValidMonth(month: String): Boolean {
    val months = listOf(
        "January", "February", "March", "April", "May", "June", "July", "August",
        "September", "October", "November", "December"
    )

    return months.contains(month)
}

fun isValidDayInMonth(day: String, month: String, year: String): Boolean {
    val thirtyDaysGroup = listOf(
        "April", "June",
        "September", "November"
    )
    val thirtyOneDaysGroup = listOf(
        "January", "March", "May", "July", "August",
        "October", "December"
    )
    return if (thirtyDaysGroup.contains(month) && day.toInt() <= 30 && day.toInt() >= 1) {
        true
    } else if (thirtyOneDaysGroup.contains(month) && day.toInt() <= 31 && day.toInt() >= 1) {
        true
    } else if (month == "February" && day.toInt() >= 1) {
        if (isLeapYear(year) && day.toInt() <= 29) {
            true
        } else !isLeapYear(year) && day.toInt() <= 28
    } else {
        false
    }
}

fun isValidYear(year: String): Boolean {
    if (isNumeric(year) && year.length == 4) {
        return true
    }
    return false
}

fun isLeapYear(year: String): Boolean {

    val leap = if (year.toInt() % 4 == 0) {
        if (year.toInt() % 100 == 0) {
            year.toInt() % 400 == 0
        } else {
            true
        }
    } else {
        false
    }

    return leap
}

fun isValidPassword(password: String): Boolean{
    return patternMatches(password, CSVConstants.PASSWORD_REGEX_PATTERN)
}