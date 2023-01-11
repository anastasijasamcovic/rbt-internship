package rs.rbt.internship.admin.constants

enum class DayEnum(s: String) {
    MONDAY("Monday"),
    TUESDAY("Tuesday"),
    WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"),
    FRIDAY("Friday"),
    SATURDAY("Saturday"),
    SUNDAY("Sunday");

    companion object {
        fun isInDayEnum(day: String): Boolean {
            return try {
                DayEnum.valueOf(day)
                true
            } catch (e: Exception) {
                false
            }
        }
    }
}