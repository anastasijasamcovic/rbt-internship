package rs.rbt.internship.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import rs.rbt.internship.model.Employee
import rs.rbt.internship.model.Vacation
import rs.rbt.internship.repository.VacationRepository
import java.util.*

@Service
class VacationService {

    @Autowired
    lateinit var vacationRepository: VacationRepository

    fun saveVacation(vacation: Vacation): Vacation {
        return vacationRepository.save(vacation)
    }

    fun getUsedVacationDaysPerYear(year: String, employee: Long): List<Vacation> {
        return vacationRepository.getUsedVacationsPerYear(employee, year)
    }

    fun getListVacationsForSpecificTimePeriod(employeeId: Long, fromDate: Date, toDate: Date):List<Vacation>{
        return vacationRepository.getUsedVacationsForSpecificTimePeriod(employeeId, fromDate, toDate)
    }

    fun saveVacation(employee: Employee, startDate: Date, endDate: Date):Vacation{
        return vacationRepository.save(Vacation(employee = employee, startDate = startDate, endDate = endDate))
    }
}