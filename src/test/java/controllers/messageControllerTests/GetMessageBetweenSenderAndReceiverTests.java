package controllers.messageControllerTests;

import main.controller.MessageController;
import main.controller.UserController;
import main.service.IMessageService;
import main.service.MessageService;
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
@WebMvcTest(MessageController.class)
@ContextConfiguration(classes = MessageController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GetMessageBetweenSenderAndReceiverTests {
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

        mockMvc.perform(MockMvcRequestBuilders
                .get("/whatsupdoc/message")
                .param("sender", "1")
                .param("receiver", "2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenSenderIsMissing_shouldReturnBadRequest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/whatsupdoc/message")
                .param("receiver", "2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenReceiverIsMissing_shouldReturnBadRequest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/whatsupdoc/message")
                .param("receiver", "2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
