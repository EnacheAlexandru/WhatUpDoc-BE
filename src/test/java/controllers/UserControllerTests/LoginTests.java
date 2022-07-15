package controllers.UserControllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import main.controller.RequestDTO.LoginRequest;
import main.controller.ResponseDTO.LoginResponseDTO;
import main.controller.UserController;
import main.mapper.Mapper;
import main.service.IAppointmentService;
import main.service.MedicService;
import main.service.PatientService;
import main.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@ContextConfiguration(classes = UserController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private UserService service;

    @MockBean
    private PatientService patientService;

    @MockBean
    private MedicService medicService;

    @MockBean
    private IAppointmentService appointmentService;

    @MockBean
    private Mapper mapper;

    @BeforeEach
    public void setup() {
        mockMvc = webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void shouldReturnStatusOk() throws Exception {

        val loginRequest = new LoginRequest("test@email.com", "1234567");

        Mockito.when(service.login("test@email.com", "1234567"))
                .thenReturn(new LoginResponseDTO(1L, "PATIENT"));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/whatsupdoc/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginRequest)))
                .andExpect(status().isOk());

        Mockito.verify(service, Mockito.times(1))
                .login(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void shouldReturnBadRequest_Email() throws Exception {

        val loginRequest = new LoginRequest("test.com", "1234567");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/whatsupdoc/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest());

        Mockito.verify(service, Mockito.times(0))
                .login(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void shouldReturnBadRequest_Password() throws Exception {

        val loginRequest = new LoginRequest("test@mail.com", "12");

        Mockito.when(service.login("test@mail.com", "1234567")).
                thenReturn(new LoginResponseDTO(1L, "PATIENT"));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/whatsupdoc/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest());

        Mockito.verify(service, Mockito.times(0))
                .login(Mockito.anyString(), Mockito.anyString());
    }

}
