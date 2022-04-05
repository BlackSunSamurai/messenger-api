package com.example.messenger.api.service

import com.example.messenger.api.exception.ConversationInvalidException
import com.example.messenger.api.models.Conversation
import com.example.messenger.api.models.User
import com.example.messenger.api.repositories.ConversationRepository
import org.springframework.stereotype.Service

@Service
class ConversationServiceImp(val repository: ConversationRepository) : ConversationService {
    override fun createConversation(userA: User, userB: User): Conversation {
        val conversation = Conversation(userA, userB)
        repository.save(conversation)
        return conversation
    }

    override fun conversationExist(userA: User, userB: User): Boolean {
        return if (repository.findByRecipientIdAndSenderId(userA.id, userB.id) != null)
            true
        else repository.findByRecipientIdAndSenderId(userB.id, userA.id) != null
    }

    override fun getConversation(userA: User, userB: User): Conversation? {
        return when{
            repository.findByRecipientIdAndSenderId(userA.id, userB.id) != null
                    -> repository.findByRecipientIdAndSenderId(userA.id, userB.id)
            repository.findByRecipientIdAndSenderId(userB.id, userA.id) != null
                    -> repository.findByRecipientIdAndSenderId(userB.id, userA.id)
            else -> null
        }
    }

    override fun retrieveThread(conversationId: Long): Conversation {
        val conversation = repository.findById(conversationId)
        if(conversation.isPresent){
            return conversation.get()
        }
        throw ConversationInvalidException("Invalid conversation id ${conversationId}")
    }

    override fun listUserConversation(userId: Long): List<Conversation> {
        val conversationList: ArrayList<Conversation> = ArrayList()
        conversationList.addAll(repository.findBySenderId(userId))
        conversationList.addAll(repository.findByRecipientId(userId))
        return conversationList
    }

    override fun nameSecondParty(conversation: Conversation, userId: Long): String {
        return if (conversation.sender?.id == userId){
            conversation.recipient?.username as String
        }else{
            conversation.sender?.username as String
        }

    }
}