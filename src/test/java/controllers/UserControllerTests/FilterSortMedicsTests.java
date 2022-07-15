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
public class FilterSortMedicsTests {
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
    public void whenAllParamsAreCompleted_shouldReturnStatusOk() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/whatsupdoc/users/patient/medics")
                .param("sort", "asc")
                .param("sort", "John")
                .param("sort", "NY")
                .param("sort", "dermatologist")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void whenSortIsMissing_shouldReturnStatusOk() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/whatsupdoc/users/patient/medics")
                .param("name", "John")
                .param("city", "NY")
                .param("specialisation", "dermatologist")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void whenNameIsMissing_shouldReturnStatusOk() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/whatsupdoc/users/patient/medics")
                .param("sort", "asc")
                .param("city", "NY")
                .param("specialisation", "dermatologist")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void whenCityIsMissing_shouldReturnStatusOk() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/whatsupdoc/users/patient/medics")
                .param("sort", "asc")
                .param("name", "John")
                .param("city", "NY")
                .param("specialisation", "dermatologist")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void whenSpecialisationIsMissing_shouldReturnStatusOk() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/whatsupdoc/users/patient/medics")
                .param("sort", "asc")
                .param("name", "John")
                .param("city", "NY")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void whenAllParamsAreMissing_shouldReturnStatusOk() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/whatsupdoc/users/patient/medics")
                .param("sort", "asc")
                .param("sort", "John")
                .param("sort", "NY")
                .param("sort", "dermatologist")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
    }
}
