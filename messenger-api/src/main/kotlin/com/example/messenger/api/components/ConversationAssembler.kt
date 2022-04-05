package com.example.messenger.api.components

import com.example.messenger.api.helpers.`object`.ConversationListVO
import com.example.messenger.api.helpers.`object`.ConversationVO
import com.example.messenger.api.helpers.`object`.MessageVO
import com.example.messenger.api.models.Conversation
import com.example.messenger.api.service.ConversationServiceImp
import org.springframework.stereotype.Component

@Component
class ConversationAssembler(val conversationService: ConversationServiceImp,val messageAssembler: MessageAssembler) {
    fun toConversationVO(conversation: Conversation,userId: Long): ConversationVO{
        val conversationMessage: ArrayList<MessageVO> = ArrayList()
        conversation.messages?.mapTo(conversationMessage){
            messageAssembler.toMessageVO(it)
        }
        return ConversationVO(conversation.id,conversationService.nameSecondParty(conversation, userId),
        conversationMessage)
    }

    fun toConversationListVO(conversations: ArrayList<Conversation>, userId: Long): ConversationListVO{
        val conversationVOList = conversations.map {
            toConversationVO(it, userId)
        }
        return ConversationListVO(conversationVOList)
    }
}