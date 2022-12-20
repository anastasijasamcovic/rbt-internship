package rs.rbt.internship.search

import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication(
	exclude = [SecurityAutoConfiguration::class,  ManagementWebSecurityAutoConfiguration::class],
	scanBasePackages = ["rs.rbt.internship.search","rs.rbt.internship.data"]
)
@EnableJpaRepositories("rs.rbt.internship.data.repository")
@EntityScan("rs.rbt.internship.data.model")
class SearchApplication

fun main(args: Array<String>) {
	runApplication<SearchApplication>(*args)
}
