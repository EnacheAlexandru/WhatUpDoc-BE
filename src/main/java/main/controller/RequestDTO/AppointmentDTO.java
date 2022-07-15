package main.controller.RequestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {

    @NotNull
    Long patientID;

    @NotNull
    Long medicID;

    @NotNull
    DayOfWeek day;

    @NotNull
    Integer hour;
}
