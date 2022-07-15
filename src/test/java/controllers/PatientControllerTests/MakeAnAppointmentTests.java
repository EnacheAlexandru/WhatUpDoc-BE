package controllers.PatientControllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.controller.PatientController;
import main.controller.RequestDTO.AppointmentDTO;
import main.controller.UserController;
import main.mapper.Mapper;
import main.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.time.DayOfWeek;

import java.sql.Date;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@ContextConfiguration(classes = PatientController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MakeAnAppointmentTests {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private IUserService userService;
    @MockBean
    private IPatientService patientService;
    @MockBean
    private IAppointmentService appointmentService;
    @MockBean
    private IMedicService medicService;
    @MockBean
    private Mapper mapper;

    @BeforeEach
    public void setup() {
        mockMvc = webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void shouldReturnStatusOk() throws Exception {

        AppointmentDTO request = new AppointmentDTO(1L,1L,DayOfWeek.from(LocalDateTime.now()),1);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/whatsupdoc/patient/appointment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}
