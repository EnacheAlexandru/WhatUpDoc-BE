package controllers.UserControllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.controller.RequestDTO.LoginRequest;
import main.controller.RequestDTO.PatientDTO;
import main.controller.RequestDTO.UserDetailDTO;
import main.controller.UserController;
import main.domain.*;
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

import java.sql.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@ContextConfiguration(classes = UserController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AddPatientTests {
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

        PatientDTO request = new PatientDTO(
                "healthy",
                new UserDetailDTO(
                        "FirstName",
                        "LastName",
                        new Address(0L,"Country", "City", "Street", "Number"),
                        new Date(2000,11,10),
                        "male",
                        "0788888888",
                        new LoginRequest("test@email.com", "1234567")
                )
        );
        Mockito.when(mapper.convertToPatient(Mockito.any(PatientDTO.class))).thenReturn(
                new Patient(
                        1L,
                        "healthy",
                        new UserDetail(
                                1L,
                                "FirstName",
                                "LastName",
                                new Address(1L,"Country", "City", "Street", "Number"),
                                new Date(2000,11,10),
                                "male",
                                "0788888888",
                                "image.png",
                                new User(1l, "test@email.com","password", Roles.PATIENT)
                        )
                )
        );

        mockMvc.perform(MockMvcRequestBuilders
                .post("/whatsupdoc/users/patient/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldBadRequestStatus_UserDetail() throws Exception {

        PatientDTO request = new PatientDTO(
                "healthy",
                new UserDetailDTO(
                )
        );
        mockMvc.perform(MockMvcRequestBuilders
                .post("/whatsupdoc/users/patient/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldBadRequestStatus_DateBirth() throws Exception {

        PatientDTO request = new PatientDTO(
                "healthy",
                new UserDetailDTO(
                        "FirstName",
                        "LastName",
                        new Address(0L,"Country", "City", "Street", "Number"),
                        null,
                        "male",
                        "0788888888",
                        new LoginRequest("test@email.com", "1234567")
                )
        );
        mockMvc.perform(MockMvcRequestBuilders
                .post("/whatsupdoc/users/patient/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldBadRequestStatus_FirstName() throws Exception {

        PatientDTO request = new PatientDTO(
                "healthy",
                new UserDetailDTO(
                        "",
                        "LastName",
                        new Address(0L,"Country", "City", "Street", "Number"),
                        new Date(2000,10,10),
                        "male",
                        "0788888888",
                        new LoginRequest("test@email.com", "1234567")
                )
        );
        mockMvc.perform(MockMvcRequestBuilders
                .post("/whatsupdoc/users/patient/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldBadRequestStatus_LastName() throws Exception {

        PatientDTO request = new PatientDTO(
                "healthy",
                new UserDetailDTO(
                        "FirstName",
                        "",
                        new Address(0L,"Country", "City", "Street", "Number"),
                        new Date(2000,10,10),
                        "male",
                        "0788888888",
                        new LoginRequest("test@email.com", "1234567")
                )
        );
        mockMvc.perform(MockMvcRequestBuilders
                .post("/whatsupdoc/users/patient/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldBadRequestStatus_Login() throws Exception {

        PatientDTO request = new PatientDTO(
                "healthy",
                new UserDetailDTO(
                        "FirstName",
                        "LastName",
                        new Address(0L,"Country", "City", "Street", "Number"),
                        new Date(2000,10,10),
                        "male",
                        "0788888888",
                        null
                )
        );
        mockMvc.perform(MockMvcRequestBuilders
                .post("/whatsupdoc/users/patient/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
