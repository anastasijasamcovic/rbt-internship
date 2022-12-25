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

    override fun getUsedVacationDaysPerYear(year: String, employee: Long): List<Vacation> {
        return vacationRepository.findUsedVacationsPerYear(employee, year)
    }

    override fun getVacationsForSpecificTimePeriod(employeeId: Long, fromDate: Date, toDate: Date):List<Vacation>{
        return vacationRepository.findUsedVacationsForSpecificTimePeriod(employeeId, fromDate, toDate)
    }

    override fun saveVacation(employee: Employee, startDate: Date, endDate: Date):Vacation{
        return vacationRepository.save(Vacation(employee = employee, startDate = startDate, endDate = endDate))
    }
}