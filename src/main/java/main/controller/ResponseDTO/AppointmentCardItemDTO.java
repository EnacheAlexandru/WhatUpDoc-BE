package main.controller.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentCardItemDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String date;
    private Integer startTime;
    private String city;
    private String street;
    private String number;
}
