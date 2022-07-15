package main.controller.RequestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {

    @NotNull
    Long senderId;

    @NotNull
    Long receiverId;

    @NotNull
    String message;

    @NotNull
    LocalDateTime dateTime;
}
