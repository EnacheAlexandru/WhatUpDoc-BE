package main.service;

import main.controller.RequestDTO.MessageDTO;
import main.controller.ResponseDTO.ActiveConversationDTO;
import main.controller.ResponseDTO.MessageSenderNameDTO;
import main.domain.Message;
import main.domain.User;
import main.domain.UserDetail;
import main.mapper.Mapper;
import main.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageService implements IMessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MedicRepository medicRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private UserDetailRepository userDetailRepository;
    @Autowired
    private Mapper mapper;

    @Override
    public long add(MessageDTO message) throws Exception {

        User sender = checkUser(message.getSenderId(), "Sender");
        User receiver = checkUser(message.getReceiverId(), "Receiver");

        if (message.getReceiverId() == message.getSenderId()) {
            throw new Exception("Receiver and sender can't be the same");
        }

        Message newMessage = new Message();
        newMessage.setMessage(message.getMessage());
        newMessage.setSender(sender);
        newMessage.setReceiver(receiver);
        newMessage.setDateTime(message.getDateTime());
        newMessage = messageRepository.save(newMessage);

        return newMessage.getId();

    }

    @Override
    public List<MessageSenderNameDTO> getMessagesBetween(Long senderId, Long receiverId) throws Exception {

        User sender = checkUser(senderId, "Sender");
        User receiver = checkUser(receiverId, "Receiver");
        if (senderId.equals(receiverId)) {
            throw new Exception("Receiver and sender can't be the same");
        }

        UserDetail senderDetail = userDetailRepository.findByUser(sender);
        String firstName = senderDetail.getFirstName();
        String lastName = senderDetail.getLastName();

        List<Message> messages = messageRepository.getMessagesBetweenUsers(sender.getId(), receiver.getId());
        return messages.stream().map(message ->
                mapper.convertToMessageSenderNameDTO(message, firstName, lastName)
        ).collect(Collectors.toList());
    }


    @Override
    public List<ActiveConversationDTO> getUsersWithActiveConversation(Long userId) throws Exception {

        User user = checkUser(userId, "User");

        List<Message> messagesReceived = messageRepository.getAllByReceiver(user);
        List<Message> messagesSend = messageRepository.getAllBySender(user);

        List<ActiveConversationDTO> list = new ArrayList<>();
        List<Long> idList = new ArrayList<>();
        for (Message message : messagesReceived) {
            if (!idList.contains(message.getSender().getId())) {
                UserDetail userDetail = userDetailRepository.findByUser(checkUser(message.getSender().getId(), "User"));
                String name = userDetail.getFirstName() + " " + userDetail.getLastName();
                ActiveConversationDTO activeConversationDTO = new ActiveConversationDTO(message.getSender().getId(), name);
                idList.add(message.getSender().getId());
                list.add(activeConversationDTO);
            }
        }
        for (Message message : messagesSend) {
            if (!idList.contains(message.getReceiver().getId())) {
                UserDetail userDetail = userDetailRepository.findByUser(checkUser(message.getReceiver().getId(), "User"));
                String name = userDetail.getFirstName() + " " + userDetail.getLastName();
                ActiveConversationDTO activeConversationDTO = new ActiveConversationDTO(message.getReceiver().getId(), name);
                idList.add(message.getReceiver().getId());
                list.add(activeConversationDTO);
            }
        }
        return list;
    }

    private User checkUser(Long userId, String role) throws Exception {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            throw new Exception(role + " not found");
        }
        return optionalUser.get();
    }


}
