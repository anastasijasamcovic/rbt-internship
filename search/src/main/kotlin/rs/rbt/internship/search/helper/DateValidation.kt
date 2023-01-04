package rs.rbt.internship.search.helper

fun isValidDate(date: String): Boolean {
    //expected: yyyy-MM-dd
    if (date == "" || !date.contains("-")) {
        return false
    }
    val splitDate = date.split("-")
    if (splitDate.size != 3) {
        return false
    }
    return (isValidYear(splitDate[0]) && isValidDateInMonth(
        day = splitDate[2],
        month = splitDate[1],
        year = splitDate[0]
    ))
}

fun isValidYear(year: String): Boolean {
    if (isNumeric(year) && year.length == 4) {
        return true
    }
    return false
}

fun isNumeric(toCheck: String): Boolean {

    return toCheck.all { char -> char.isDigit() }
}

fun isValidDateInMonth(day: String, month: String, year: String): Boolean {
    val thirtyDaysGroup = listOf(
        "04", "06",
        "09", "11"
    )
    val thirtyOneDaysGroup = listOf(
        "01", "03", "05", "07", "08",
        "10", "12"
    )
    if (month.toInt() < 1 || month.toInt() > 12 || month.length != 2) {
        return false
    } else {

        return if (thirtyDaysGroup.contains(month) && day.toInt() <= 30 && day.toInt() >= 1) {
            true
        } else if (thirtyOneDaysGroup.contains(month) && day.toInt() <= 31 && day.toInt() >= 1) {
            true
        } else if (month == "02" && day.toInt() >= 1) {
            if (isLeapYear(year) && day.toInt() <= 29) {
                true
            } else !isLeapYear(year) && day.toInt() <= 28
        } else {
            false
        }
    }
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