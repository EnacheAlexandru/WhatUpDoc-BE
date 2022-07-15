package main.controller.RequestDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.domain.Address;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailDTO {
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

    @NotNull
    @Valid
    private LoginRequest user;

}
