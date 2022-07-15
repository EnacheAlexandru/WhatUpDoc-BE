package controllers.messageControllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.controller.MessageController;
import main.controller.RequestDTO.MessageDTO;
import main.controller.ResponseDTO.LoginResponseDTO;
import main.controller.UserController;
import main.service.*;
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

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MessageController.class)
@ContextConfiguration(classes = MessageController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CreateMessageTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private IMessageService messageService;


    @BeforeEach
    public void setup() {
        mockMvc = webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void shouldReturnStatusOk() throws Exception {
        MessageDTO request = new MessageDTO(1L, 2L, "message", LocalDateTime.now());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/whatsupdoc/message")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)));
    }

    @Test
    public void shouldBadRequestStatus_SenderId() throws Exception {

        MessageDTO request = new MessageDTO(null, 2L, "message", LocalDateTime.now());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/whatsupdoc/message")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldBadRequestStatus_ReceiverId() throws Exception {

        MessageDTO request = new MessageDTO(1L, null, "message", LocalDateTime.now());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/whatsupdoc/message")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldBadRequestStatus_Message() throws Exception {

        MessageDTO request = new MessageDTO(1L, 2L, "", LocalDateTime.now());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/whatsupdoc/message")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldBadRequestStatus_DateTime() throws Exception {

        MessageDTO request = new MessageDTO(1L, 2L, "message", null);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/whatsupdoc/message")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
