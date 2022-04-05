package com.example.messenger.api.service

import com.example.messenger.api.exception.MessageEmptyException
import com.example.messenger.api.exception.MessageRecipientInvalidException
import com.example.messenger.api.models.Conversation
import com.example.messenger.api.models.Message
import com.example.messenger.api.models.User
import com.example.messenger.api.repositories.ConversationRepository
import com.example.messenger.api.repositories.MessageRepository
import com.example.messenger.api.repositories.UserRepository
import org.springframework.stereotype.Service
import kotlin.jvm.Throws

@Service
class MessageServiceImp(val repository: MessageRepository,
                        val conversationRepository: ConversationRepository,
                        val conversatoniService: ConversationService,
                        val serRepository: UserRepository
) : MessageService {

    @Throws(MessageEmptyException::class,MessageRecipientInvalidException::class)
    override fun sendMessage(sender: User, recepientId: Long, messageText: String): Message {
        val optional = serRepository.findById(recepientId)

        if (optional.isPresent){
            val recepient = optional.get()

            if(!messageText.isEmpty()){
                val conversation: Conversation = if(conversatoniService.conversationExist(sender, recepient)){
                    conversatoniService.getConversation(sender, recepient) as Conversation
                }else{
                    conversatoniService.createConversation(sender, recepient)
                }
                conversationRepository.save(conversation)

                val message = Message(sender,recepient,messageText,conversation)
                repository.save(message)
                return message
            }
        }else{
            throw MessageRecipientInvalidException("The recipient id ${recepientId} is invalid")
        }
        throw MessageEmptyException()
    }


}