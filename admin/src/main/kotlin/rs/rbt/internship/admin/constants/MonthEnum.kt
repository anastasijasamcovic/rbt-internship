package rs.rbt.internship.admin.constants

enum class MonthEnum(s: String) {
    JANUARY("January"),
    FEBRUARY("February"),
    MARCH("March"),
    APRIL("April"),
    MAY("May"),
    JUNE("June"),
    JULY("July"),
    AUGUST("August"),
    SEPTEMBER("September"),
    OCTOBER("October"),
    NOVEMBER("November"),
    DECEMBER("December");

    companion object {
        fun isInMonthEnum(month: String): Boolean {
            return try {
                valueOf(month)
                true
            } catch (e: Exception) {
                false
            }
        }
    }
}