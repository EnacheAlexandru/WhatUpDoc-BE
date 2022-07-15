package service.messageServicesTests;

import main.domain.Roles;
import main.domain.User;
import main.mapper.Mapper;
import main.repository.MessageRepository;
import main.repository.UserRepository;
import main.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.*;

import java.util.Optional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class GetUsersWithActiveConversation {

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
        User u = new User(1l, "test@email.com","password", Roles.PATIENT);

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(u));

        messageService.getUsersWithActiveConversation(1L);

        Mockito.verify(userRepository, Mockito.times(1))
                .findById(Mockito.any(Long.class));
        Mockito.verify(messageRepository, Mockito.times(1))
                .getAllBySender(Mockito.any(User.class));
        Mockito.verify(messageRepository, Mockito.times(1))
                .getAllByReceiver(Mockito.any(User.class));
    }
}
