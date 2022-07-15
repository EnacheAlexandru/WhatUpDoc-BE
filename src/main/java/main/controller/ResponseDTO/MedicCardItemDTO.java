package main.controller.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicCardItemDTO {
    private Long id;
    private String lastName;
    private String firstName;
    private String city;
    private String specialisation;
    private String urlImage;
}
