package rs.rbt.internship.data.model;

import jakarta.persistence.*


@Entity
class Employee (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long=0,
    @Column(name = "email")
    var email: String = "",
    @Column(name = "password")
    var password: String = "",
    @OneToMany(mappedBy = "employee")
    var vacationList: MutableList<Vacation> = mutableListOf(),
    @ElementCollection
    @CollectionTable(name = "vacations_days_per_year", joinColumns = [JoinColumn(name="employee_id")])
    @MapKeyColumn(name = "year")
    @Column(name = "vacation_days")
    var vacationDaysPerYear: MutableMap<String, Int> = mutableMapOf()
)