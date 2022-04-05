package com.example.messenger.api.models

import com.example.messenger.api.listeners.UserListener
import org.springframework.format.annotation.DateTimeFormat
import java.time.Instant
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@Entity
@Table(name = "`user`")
@EntityListeners(UserListener::class)
class User(
    @Column(unique = true)
    @Size(min = 2)
    var username: String = "",
    @Size(min = 11)
    @Pattern(regexp = "\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$")
    var phoneNumber: String = " ",
    @Size(min = 6,max = 16)
    var password: String = " ",
    var status: String = " ",
    @Pattern(regexp = "\\A(activated|deactivated)\\z")
    var accountStatus: String = " ",
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0,
    @DateTimeFormat
    var createdAt: Date = Date.from(Instant.now())
) {
    @OneToMany(mappedBy = "sender",targetEntity = Message::class)
    private var sentMessage: Collection<Message>? = null
    @OneToMany(mappedBy = "recipient",targetEntity = Message::class)
    private var receivedMessage: Collection<Message>? = null
}