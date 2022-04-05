package com.example.messenger.api.service

import com.example.messenger.api.models.Message
import com.example.messenger.api.models.User

interface MessageService {
    fun sendMessage(sender: User, recepientId: Long, messageText: String): Message
}