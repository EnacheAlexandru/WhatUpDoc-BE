package main.controller.RequestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicDTO {

    @NotBlank
    private String specialisation;

    @NotBlank
    private String degree;

    @NotNull
    @Valid
    private UserDetailDTO userDetail;
}
