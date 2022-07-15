package main.service;

import main.controller.RequestDTO.MessageDTO;
import main.controller.ResponseDTO.ActiveConversationDTO;
import main.controller.ResponseDTO.MessageSenderNameDTO;

import java.util.List;

public interface IMessageService {

    long add(MessageDTO message) throws Exception;

    List<MessageSenderNameDTO> getMessagesBetween(Long senderId, Long receiverId) throws Exception;

    List<ActiveConversationDTO> getUsersWithActiveConversation(Long userId) throws Exception;
}
