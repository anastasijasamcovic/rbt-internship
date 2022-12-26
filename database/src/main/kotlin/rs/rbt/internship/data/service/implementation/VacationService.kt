package rs.rbt.internship.data.service.implementation

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import rs.rbt.internship.data.model.Employee
import rs.rbt.internship.data.model.Vacation
import rs.rbt.internship.data.repository.VacationRepository
import rs.rbt.internship.data.service.interfaces.IVacationService
import java.util.*

@Component
class VacationService: IVacationService {

    @Autowired
    lateinit var vacationRepository: VacationRepository

    override fun getUsedVacationDaysPerYear(year: String, employeeEmail: String): List<Vacation> {
        return vacationRepository.findUsedVacationsPerYear(employeeEmail, year)
    }

    override fun getVacationsForSpecificTimePeriod(employeeEmail: String, fromDate: Date, toDate: Date):List<Vacation>{
        return vacationRepository.findUsedVacationsForSpecificTimePeriod(employeeEmail, fromDate, toDate)
    }

    override fun saveVacation(employee: Employee, startDate: Date, endDate: Date):Vacation{
        return vacationRepository.save(Vacation(employee = employee, startDate = startDate, endDate = endDate))
    }
}