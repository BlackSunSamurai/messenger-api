package com.example.messenger.api.service

import com.example.messenger.api.models.Conversation
import com.example.messenger.api.models.User

interface ConversationService {
    fun createConversation(userA: User,userB: User): Conversation
    fun conversationExist(userA: User,userB: User): Boolean
    fun getConversation(userA: User,userB: User): Conversation?
    fun retrieveThread(conversationId: Long): Conversation
    fun listUserConversation(userId: Long): List<Conversation>
    fun nameSecondParty(conversation: Conversation,userId: Long): String
}