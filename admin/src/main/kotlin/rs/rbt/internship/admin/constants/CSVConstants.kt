package rs.rbt.internship.admin.constants

public class CSVConstants {
    companion object{
        const val ALLOWED_FILE_EXTENSION = "csv"
        const val INVALID_FILE_EXTENSION = "File must have csv extension"
        const val DATE_FORMAT = "EEE, MMM d, yyyy"
        const val WRONG_RECORDS_MESSAGE = "File have wrong records. Correct record were written. "
        const val EMPTY_FILE_MESSAGE = "File is empty."
        const val EMAIL_REGEX_PATTERN = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"
        const val PASSWORD_REGEX_PATTERN = "[A-Z]+[a-z]+[0-9]*[!|#|@|\$]+"
        const val EMPLOYEE_EMAIL = "Employee Email"
        const val VACATION_YEAR = "Vacation year"
        const val EMPLOYEE = "Employee"
    }
}