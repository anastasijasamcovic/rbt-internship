package rs.rbt.internship.admin

import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication(
    scanBasePackages = ["rs.rbt.internship.admin", "rs.rbt.internship.data"],
)
@EnableJpaRepositories("rs.rbt.internship.data.repository")
@EntityScan("rs.rbt.internship.data.model")

class AdminApplication

fun main(args: Array<String>) {
    runApplication<AdminApplication>(*args)
}
