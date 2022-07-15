package main.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "schedules")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "medic", referencedColumnName = "id")
    @OneToOne
    private Medic medic;

    @OneToMany()
    @JoinColumn(name = "schedule_id")
    private List<ScheduleItem> scheduleItems;
}
