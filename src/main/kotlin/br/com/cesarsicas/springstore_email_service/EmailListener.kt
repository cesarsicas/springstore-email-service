package br.com.cesarsicas.springstore_email_service

import com.google.gson.Gson
import it.ozimov.springboot.mail.model.Email
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmail
import it.ozimov.springboot.mail.service.EmailService
import jakarta.mail.internet.InternetAddress
import org.springframework.amqp.rabbit.annotation.Exchange
import org.springframework.amqp.rabbit.annotation.Queue
import org.springframework.amqp.rabbit.annotation.QueueBinding
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component


@Component
class EmailListener {

    @Autowired
    lateinit var emailService: EmailService

    @Autowired
    private lateinit var env: Environment


    @RabbitListener(
        bindings = [QueueBinding(
            exchange = Exchange(value = "store-messages"),
            value = Queue("queue.orders"),
            key = arrayOf("rk.orders")
        )]
    )

    fun listener(received: String) {
        val customerEmail: CustomerEmail = Gson().fromJson(received, CustomerEmail::class.java)
        println("Ordem processada: $customerEmail")

        sendEmail(customerEmail)
    }


    fun sendEmail(customerEmail: CustomerEmail) {

        val email: Email = DefaultEmail.builder()
            .from(InternetAddress(env.getProperty("service_email_username")))
            .replyTo(InternetAddress(env.getProperty("service_email_username")))
            .to(arrayListOf(InternetAddress(customerEmail.email)))
            .subject(customerEmail.title)
            .body(customerEmail.message)
            .encoding("UTF-8").build()

        emailService.send(email)
    }

}