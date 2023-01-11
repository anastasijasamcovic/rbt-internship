package rs.rbt.internship.data.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.util.*

@Entity
data class Vacation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @Column(name = "start_date")
    var startDate: Date,
    @Column(name = "end_date")
    var endDate: Date,
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "employee_id")
    var employee: Employee
)