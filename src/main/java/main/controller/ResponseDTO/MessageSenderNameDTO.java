package main.controller.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageSenderNameDTO {

    @NotNull
    Long senderId;

    @NotNull
    String senderFirstName;

    @NotNull
    String senderLastName;

    @NotNull
    Long receiverId;

    @NotNull
    String message;

    @NotNull
    LocalDateTime dateTime;
}

