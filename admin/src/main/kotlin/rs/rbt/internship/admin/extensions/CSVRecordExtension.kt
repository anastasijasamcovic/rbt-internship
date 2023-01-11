package rs.rbt.internship.admin.extensions

import org.apache.commons.csv.CSVRecord

fun CSVRecord.getEmail(): String{
    return this.get(0)
}

fun CSVRecord.getPassword(): String{
    return this.get(1)
}

fun CSVRecord.getNumberOfVacationDays(): String{
    return this.get(1)
}

fun CSVRecord.getStartVacationDate(): String{
    return this.get(1)
}

fun CSVRecord.getEndVacationDate(): String{
    return this.get(2)
}