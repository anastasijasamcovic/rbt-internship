package rs.rbt.internship.admin.helper

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import org.apache.commons.io.FilenameUtils
import org.springframework.web.multipart.MultipartFile
import rs.rbt.internship.admin.constants.CSVConstants
import rs.rbt.internship.admin.exception.CsvException
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

fun readFile(file: MultipartFile): List<CSVRecord> {
    isCSVFile(file)
    val fileReader = BufferedReader(InputStreamReader(file.inputStream, StandardCharsets.UTF_8))
    val csvParser = CSVParser(fileReader, CSVFormat.DEFAULT)
    val loadedRecords: List<CSVRecord> = csvParser.records
    if(loadedRecords.isEmpty()){
        throw CsvException(CSVConstants.EMPTY_FILE_MESSAGE)
    }

    return loadedRecords
}

fun isCSVFile(file: MultipartFile) {
    val fileExtension = FilenameUtils.getExtension(file.originalFilename)

    if (fileExtension != CSVConstants.ALLOWED_FILE_EXTENSION) {
        throw CsvException(CSVConstants.INVALID_FILE_EXTENSION)
    }
}