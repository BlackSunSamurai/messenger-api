package com.example.messenger.api.components

import com.example.messenger.api.constants.ErrorResponse
import com.example.messenger.api.constants.ResponseConstants
import com.example.messenger.api.exception.MessageEmptyException
import com.example.messenger.api.exception.MessageRecipientInvalidException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class MessageControllerAdvice {

    @ExceptionHandler(MessageEmptyException::class)
    fun messageEmpty(messageEmptyException: MessageEmptyException): ResponseEntity<ErrorResponse> {
        val res = ErrorResponse(ResponseConstants.MESSAGE_EMPTY.value, messageEmptyException.message)
        return ResponseEntity.unprocessableEntity().body(res)
    }

    @ExceptionHandler(MessageRecipientInvalidException::class)
    fun messageRecipientInvalid(messageEmptyException: MessageEmptyException): ResponseEntity<ErrorResponse>{
        val res = ErrorResponse(ResponseConstants.MESSAGE_RECIPIENT_INVALID.value, messageEmptyException.message)
        return ResponseEntity.unprocessableEntity().body(res)
    }


}