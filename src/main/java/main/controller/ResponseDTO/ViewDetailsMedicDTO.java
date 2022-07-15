package main.controller.ResponseDTO;

import lombok.*;
import main.domain.Address;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewDetailsMedicDTO {

    private String firstname;

    private String lastname;

    private String imageURL;

    private Date birth;

    private String gender;

    private String number;

    private String email;

    private Address address;

    private String specialization;

    private String grade;

}
