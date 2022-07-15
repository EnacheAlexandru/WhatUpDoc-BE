package main.controller.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentForMedicCardItemDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String medicalDetails;
    private String phoneNumber;
    private String date;
    private Integer startTime;
}
