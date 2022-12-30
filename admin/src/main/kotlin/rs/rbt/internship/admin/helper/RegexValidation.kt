package rs.rbt.internship.admin.helper

import java.util.regex.Pattern


fun patternMatches(field: String, regexPattern: String): Boolean {
    return Pattern.compile(regexPattern)
        .matcher(field)
        .matches()
}
