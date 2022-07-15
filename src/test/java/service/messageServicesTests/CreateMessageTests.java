package service.messageServicesTests;

import main.controller.RequestDTO.MessageDTO;
import main.domain.*;
import main.mapper.Mapper;
import main.repository.MessageRepository;
import main.repository.UserRepository;
import main.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CreateMessageTests {
    @Mock
    private MessageRepository messageRepository;
    @Mock
    private UserRepository userRepository;
    @Spy
    private Mapper mapper;
    @InjectMocks
    private MessageService messageService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCallAllMethodsFromRepositories() throws Exception {
        MessageDTO message = new MessageDTO(1L, 2L, "message", LocalDateTime.now());
        User u1 = new User(1l, "test@email.com","password", Roles.PATIENT);
        User u2 = new User(2l, "test@email.com","password", Roles.MEDIC);
        Message newMessage = new Message(null,u1, u2, "message", LocalDateTime.now());
        Message response = new Message(1L,u1, u2, "message", LocalDateTime.now());

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(u1));
        Mockito.when(userRepository.findById(2L)).thenReturn(Optional.of(u2));
        Mockito.when(messageRepository.save(Mockito.any(Message.class))).thenReturn(response);

        messageService.add(message);

        Mockito.verify(userRepository, Mockito.times(2))
                .findById(Mockito.any(Long.class));
        Mockito.verify(messageRepository, Mockito.times(1))
                .save(Mockito.any(Message.class));
    }

}
