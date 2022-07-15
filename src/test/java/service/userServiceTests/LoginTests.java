package service.userServiceTests;


import main.controller.ResponseDTO.LoginResponseDTO;
import main.domain.Roles;
import main.domain.User;
import main.repository.UserRepository;
import main.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginTests {

    @Spy
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCallFindByUsernameAndPassword() throws Exception {
        User u = new User(1L, "test@email.com", "password", Roles.PATIENT, true);

        Mockito.when(userRepository.findByEmail("test@email.com"))
                .thenReturn(u);
        Mockito.when(passwordEncoder.matches("password", "password")).thenReturn(true);

        LoginResponseDTO loginResponseDTO = service.login("test@email.com", "password");

        assert Objects.equals(loginResponseDTO, new LoginResponseDTO(1L, "PATIENT"));
        Mockito.verify(userRepository, Mockito.times(1))
                .findByEmail(Mockito.anyString());
    }

    @Test
    public void whenPasswordsDontMatch_shouldThrowException() throws Exception {
        User u = new User(1L, "test@email.com", "password123", Roles.PATIENT);

        Mockito.when(userRepository.findByEmail("test@email.com"))
                .thenReturn(u);
        Mockito.when(passwordEncoder.matches("password", "password123")).thenReturn(false);

        assertThrows(Exception.class,
                () -> service.login("test@email.com", "password"),
                "Bad credentials"
        );

        Mockito.verify(userRepository, Mockito.times(1))
                .findByEmail(Mockito.anyString());
    }

}