package main.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "patient", referencedColumnName = "id")
    @OneToOne
    private Patient patient;

    @JoinColumn(name = "medic", referencedColumnName = "id")
    @OneToOne
    private Medic medic;

    private LocalDate date;

    @Column(name = "start_time")
    private Integer startTime;

//    @Column(name = "end_time")
//    private LocalTime endTime;

//   private Integer duration;


}
