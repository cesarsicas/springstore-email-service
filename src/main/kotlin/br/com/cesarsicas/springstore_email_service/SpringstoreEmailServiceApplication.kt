package br.com.cesarsicas.springstore_email_service

import it.ozimov.springboot.mail.configuration.EnableEmailTools
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication


@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
@EnableEmailTools
class SpringstoreEmailServiceApplication


fun main(args: Array<String>){
	runApplication<SpringstoreEmailServiceApplication>(*args)
}
