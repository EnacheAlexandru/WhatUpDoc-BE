package main.controller.RequestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {

    private String medicalDetails;

    @NotNull
    @Valid
    private UserDetailDTO userDetail;
}
