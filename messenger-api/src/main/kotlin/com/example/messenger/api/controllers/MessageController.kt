package com.example.messenger.api.controllers

import com.example.messenger.api.components.MessageAssembler
import com.example.messenger.api.helpers.`object`.MessageVO
import com.example.messenger.api.models.User
import com.example.messenger.api.repositories.UserRepository
import com.example.messenger.api.service.MessageServiceImp
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("messages")
class MessageController(val messageServiceImp: MessageServiceImp,
                        val userRepository: UserRepository,
                        val messageAssembler: MessageAssembler) {
    @PostMapping
    fun create(@RequestBody messageDeatails: MessageRequest, request: HttpServletRequest): ResponseEntity<MessageVO> {
        val principal = request.userPrincipal
        val sender = userRepository.findByUsername(principal.name) as User
        val message = messageServiceImp.sendMessage(sender, messageDeatails.recepientId,messageDeatails.message)
        return ResponseEntity.ok(messageAssembler.toMessageVO(message))
    }
}

data class MessageRequest(val recepientId: Long,val message: String)