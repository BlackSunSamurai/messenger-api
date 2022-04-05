package com.example.messenger.api.components

import com.example.messenger.api.constants.ErrorResponse
import com.example.messenger.api.exception.ConversationInvalidException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ConversationControllerAdvice {

    @ExceptionHandler
    fun conversationInvalidException(conversationInvalidException: ConversationInvalidException): ResponseEntity<ErrorResponse>{
        val res = ErrorResponse("",conversationInvalidException.message)
        return ResponseEntity.unprocessableEntity().body(res)
    }
}