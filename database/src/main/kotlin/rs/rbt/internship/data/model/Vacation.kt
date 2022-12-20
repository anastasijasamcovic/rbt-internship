package rs.rbt.internship.data.model

import jakarta.persistence.*
import java.util.*
import com.fasterxml.jackson.annotation.JsonIgnore

@Entity
class Vacation (
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    var id: Long=0,
    @Column(name="start_date")
    var startDate: Date = Date(),
    @Column(name="end_date")
    var endDate: Date = Date(),
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="employee_id")
    var employee: Employee = Employee()
)