package main.controller.ResponseDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.domain.Address;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PatientDetailsDTO {
    private Long id;

    @NotBlank(message = "Firstname is mandatory")
    private String firstName;

    @NotBlank(message = "Lastname is mandatory")
    private String lastName;

    private Address address;

    @JsonFormat(pattern="yyyy-MM-dd")
    @NotNull
    private Date birth;

    private String gender;

    private String phoneNumber;

    private String imageUrl;

}
