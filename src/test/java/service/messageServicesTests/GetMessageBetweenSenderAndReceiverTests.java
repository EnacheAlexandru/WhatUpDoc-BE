package service.messageServicesTests;

import main.controller.ResponseDTO.MessageSenderNameDTO;
import main.domain.*;
import main.mapper.Mapper;
import main.repository.MessageRepository;
import main.repository.UserDetailRepository;
import main.repository.UserRepository;
import main.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GetMessageBetweenSenderAndReceiverTests {
    @Mock
    private MessageRepository messageRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserDetailRepository userDetailRepository;
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
        User u1 = new User(1l, "test@email.com","password", Roles.PATIENT);
        User u2 = new User(2l, "test@email.com","password", Roles.MEDIC);
        UserDetail userDetail1 = new UserDetail(
                1L,
                "FirstName1",
                "LastName1",
                new Address(1L,"Country", "City", "Street", "Number"),
                new Date(2000,11,10),
                "male",
                "0788888888",
                "image.png",
                new User(1l, "test@email.com","password", Roles.PATIENT)
        );
        UserDetail userDetail2 = new UserDetail(
                2L,
                "FirstName2",
                "LastName2",
                new Address(1L,"Country", "City", "Street", "Number"),
                new Date(2000,11,10),
                "male",
                "0788888888",
                "image.png",
                new User(1l, "test@email.com","password", Roles.PATIENT)
        );

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(u1));
        Mockito.when(userRepository.findById(2L)).thenReturn(Optional.of(u2));
        Mockito.when(userDetailRepository.findByUser(u1)).thenReturn(userDetail1);
        Mockito.when(userDetailRepository.findByUser(u2)).thenReturn(userDetail2);

        messageService.getMessagesBetween(1L, 2L);

        Mockito.verify(userRepository, Mockito.times(2))
                .findById(Mockito.any(Long.class));
        Mockito.verify(userDetailRepository, Mockito.times(1))
                .findByUser(Mockito.any(User.class));
        Mockito.verify(messageRepository, Mockito.times(1))
                .getMessagesBetweenUsers(Mockito.anyLong(), Mockito.anyLong());
    }

    @Test
    public void shoudReturnCorrectMappedResponse() throws Exception {
        User user1 = new User(1l, "test1@email.com", "password", Roles.PATIENT);
        User user2 = new User(2l, "test2@email.com", "password", Roles.MEDIC);
        UserDetail userDetail1 = new UserDetail(
                1L,
                "FirstName1",
                "LastName1",
                new Address(1L,"Country", "City", "Street", "Number"),
                new Date(2000,11,10),
                "male",
                "0788888888",
                "image.png",
                new User(1l, "test@email.com","password", Roles.PATIENT)
        );
        UserDetail userDetail2 = new UserDetail(
                2L,
                "FirstName2",
                "LastName2",
                new Address(1L,"Country", "City", "Street", "Number"),
                new Date(2000,11,10),
                "male",
                "0788888888",
                "image.png",
                new User(1l, "test@email.com","password", Roles.PATIENT)
        );
        List<Message> messages = new ArrayList<>();
        LocalDateTime dateTime1=LocalDateTime.now();
        LocalDateTime dateTime2=LocalDateTime.now();
        messages.add(new Message(1L, user1, user2, "message1", dateTime1));
        messages.add(new Message(1L, user2, user1, "message1", dateTime2));
        List<MessageSenderNameDTO> messagesDTO = new ArrayList<>();
        messagesDTO.add(new MessageSenderNameDTO(user1.getId(), userDetail1.getFirstName(), userDetail1.getLastName(), user2.getId(), "message1", dateTime1));
        messagesDTO.add(new MessageSenderNameDTO(user2.getId(), userDetail1.getFirstName(), userDetail1.getLastName(),user1.getId(), "message1", dateTime2));

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        Mockito.when(userRepository.findById(2L)).thenReturn(Optional.of(user2));
        Mockito.when(userDetailRepository.findByUser(user1)).thenReturn(userDetail1);
        Mockito.when(userDetailRepository.findByUser(user2)).thenReturn(userDetail2);
        Mockito.when(messageRepository.getMessagesBetweenUsers(user1.getId(), user2.getId())).thenReturn(messages);

        List<MessageSenderNameDTO> response = messageService.getMessagesBetween(1L, 2L);

        assertEquals("Should be the same", response, messagesDTO);
    }
}
