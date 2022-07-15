package main.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "medical_details")
    private String medicalDetails;

    @JoinColumn(name = "user_detail", referencedColumnName = "id")
    @OneToOne
    private UserDetail userDetail;

}
