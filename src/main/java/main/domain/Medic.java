package main.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "medics")
public class Medic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "specialisation")
    private String specialisation;

    @Column(name = "degree")
    private String degree;


    @JoinColumn(name = "user_detail", referencedColumnName = "id")
    @OneToOne
    private UserDetail userDetail;

}
