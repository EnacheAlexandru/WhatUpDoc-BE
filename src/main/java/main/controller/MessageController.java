package main.controller;

import main.controller.RequestDTO.MessageDTO;
import main.controller.ResponseDTO.ActiveConversationDTO;
import main.controller.ResponseDTO.MessageSenderNameDTO;
import main.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/whatsupdoc/message")
public class MessageController {

    @Autowired
    private IMessageService messageService;

    @PostMapping
    public long createMessage(@RequestBody @Valid MessageDTO message) throws Exception {
        return messageService.add(message);
    }

    @GetMapping(params = {"sender", "receiver"})
    public List<MessageSenderNameDTO> getMessageBetweenSenderAndReceiver(@RequestParam("sender") Long senderId, @RequestParam("receiver") Long receiverId) throws Exception {
        return messageService.getMessagesBetween(senderId, receiverId);
    }

    @GetMapping("/{id}")
    public List<ActiveConversationDTO> getUsersWithActiveConversation(@PathVariable("id") Long userId) throws Exception {
        return messageService.getUsersWithActiveConversation(userId);
    }


}
