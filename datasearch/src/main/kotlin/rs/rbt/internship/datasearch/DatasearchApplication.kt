package rs.rbt.internship.datasearch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class,  ManagementWebSecurityAutoConfiguration::class],
	scanBasePackages = ["rs.rbt.internship.service","rs.rbt.internship.repository"])
class DatasearchApplication

fun main(args: Array<String>) {
	runApplication<DatasearchApplication>(*args)
}
