package controllers.UserControllerTests;

import main.controller.UserController;
import main.mapper.Mapper;
import main.service.IAppointmentService;
import main.service.MedicService;
import main.service.PatientService;
import main.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
public class ViewDetailsMedicTests {
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

        mockMvc.perform(MockMvcRequestBuilders
                .get("/whatsupdoc/users/patient/medic/1"))
                .andExpect(status().isOk());

        Mockito.verify(patientService, Mockito.times(1))
                .getDetailsAboutMedic(Mockito.anyLong());
    }
}
