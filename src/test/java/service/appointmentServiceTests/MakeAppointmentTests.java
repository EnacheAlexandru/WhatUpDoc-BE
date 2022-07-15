package service.appointmentServiceTests;

import main.controller.RequestDTO.AppointmentDTO;
import main.domain.*;
import main.repository.AppointmentRepository;
import main.repository.MedicRepository;
import main.repository.PatientRepository;
import main.repository.ScheduleRepository;
import main.service.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MakeAppointmentTests {

    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private ScheduleRepository scheduleRepository;
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private MedicRepository medicRepository;
    @InjectMocks
    private AppointmentService appointmentService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCallAddMethodsFromRepositories() throws Exception {
        AppointmentDTO appointment = new AppointmentDTO(1L,1L, DayOfWeek.from(LocalDateTime.now()),1);
        Medic medic = new Medic(
                1L,
                "dermatologist",
                "first degree",
                new UserDetail(
                        1L,
                        "FirstName",
                        "LastName",
                        new Address(1L,"Country", "City", "Street", "Number"),
                        new Date(2000,11,10),
                        "male",
                        "0788888888",
                        "img.jpg",
                        new User(1l, "test@email.com","password", Roles.MEDIC)
                )
        );
        Patient patient = new Patient(
                1L,
                "healthy",
                new UserDetail(
                        1L,
                        "FirstName",
                        "LastName",
                        new Address(1L,"Country", "City", "Street", "Number"),
                        new Date(2000,11,10),
                        "male",
                        "0788888888",
                        "image.png",
                        new User(1l, "test@email.com","password", Roles.PATIENT)
                )
        );
        ScheduleItem scheduleItem = new ScheduleItem();
        scheduleItem.setDate(getDateFromNextWeek(DayOfWeek.from(LocalDateTime.now())));
        scheduleItem.setDay(DayOfWeek.from(LocalDateTime.now()));
        scheduleItem.setStartHour(1);
        scheduleItem.setEndHour(2);
        List<ScheduleItem> scheduleItems = new ArrayList<>();
        scheduleItems.add(scheduleItem);
        Schedule schedule = new Schedule(1L, medic, scheduleItems);

        Mockito.when(medicRepository.getMedicByIdUser(appointment.getMedicID())).thenReturn(medic);
        Mockito.when(patientRepository.getPatientByIdUser(appointment.getPatientID())).thenReturn(patient);
        Mockito.when(scheduleRepository.findByMedic(medic)).thenReturn(schedule);

        appointmentService.makeAppointment(appointment);

        Mockito.verify(medicRepository, Mockito.times(1))
                .getMedicByIdUser(Mockito.anyLong());
        Mockito.verify(patientRepository, Mockito.times(1))
                .getPatientByIdUser(Mockito.anyLong());
        Mockito.verify(scheduleRepository, Mockito.times(1))
                .findByMedic(Mockito.any(Medic.class));
        Mockito.verify(appointmentRepository, Mockito.times(1))
                .findByDateAndMedicAndStartTime(Mockito.any(LocalDate.class),Mockito.any(Medic.class),Mockito.anyInt());
        Mockito.verify(appointmentRepository, Mockito.times(1))
                .save(Mockito.any(Appointment.class));
    }

    @Test
    public void shouldThrowInvalidDateInMedicSchedule() throws Exception {
        AppointmentDTO appointment = new AppointmentDTO(1L,1L, DayOfWeek.from(LocalDateTime.now()),1);
        Medic medic = new Medic(
                1L,
                "dermatologist",
                "first degree",
                new UserDetail(
                        1L,
                        "FirstName",
                        "LastName",
                        new Address(1L,"Country", "City", "Street", "Number"),
                        new Date(2000,11,10),
                        "male",
                        "0788888888",
                        "img.jpg",
                        new User(1l, "test@email.com","password", Roles.MEDIC)
                )
        );
        Patient patient = new Patient(
                1L,
                "healthy",
                new UserDetail(
                        1L,
                        "FirstName",
                        "LastName",
                        new Address(1L,"Country", "City", "Street", "Number"),
                        new Date(2000,11,10),
                        "male",
                        "0788888888",
                        "image.png",
                        new User(1l, "test@email.com","password", Roles.PATIENT)
                )
        );
        ScheduleItem scheduleItem = new ScheduleItem();
        scheduleItem.setDate(getDateFromNextWeek(DayOfWeek.from(LocalDateTime.now().plusDays(1))));
        scheduleItem.setDay(DayOfWeek.from(LocalDateTime.now()));
        scheduleItem.setStartHour(1);
        scheduleItem.setEndHour(2);
        List<ScheduleItem> scheduleItems = new ArrayList<>();
        scheduleItems.add(scheduleItem);
        Schedule schedule = new Schedule(1L, medic, scheduleItems);

        Mockito.when(medicRepository.getMedicByIdUser(appointment.getMedicID())).thenReturn(medic);
        Mockito.when(patientRepository.getPatientByIdUser(appointment.getPatientID())).thenReturn(patient);
        Mockito.when(scheduleRepository.findByMedic(medic)).thenReturn(schedule);

        assertThrows(Exception.class,
                () -> appointmentService.makeAppointment(appointment),
                "Invalid date in medic schedule"
        );
    }

    @Test
    public void shouldThrowInvalidDayInMedicSchedule() throws Exception {
        AppointmentDTO appointment = new AppointmentDTO(1L,1L, DayOfWeek.from(LocalDateTime.now()),1);
        Medic medic = new Medic(
                1L,
                "dermatologist",
                "first degree",
                new UserDetail(
                        1L,
                        "FirstName",
                        "LastName",
                        new Address(1L,"Country", "City", "Street", "Number"),
                        new Date(2000,11,10),
                        "male",
                        "0788888888",
                        "img.jpg",
                        new User(1l, "test@email.com","password", Roles.MEDIC)
                )
        );
        Patient patient = new Patient(
                1L,
                "healthy",
                new UserDetail(
                        1L,
                        "FirstName",
                        "LastName",
                        new Address(1L,"Country", "City", "Street", "Number"),
                        new Date(2000,11,10),
                        "male",
                        "0788888888",
                        "image.png",
                        new User(1l, "test@email.com","password", Roles.PATIENT)
                )
        );
        ScheduleItem scheduleItem = new ScheduleItem();
        scheduleItem.setDate(getDateFromNextWeek(DayOfWeek.from(LocalDateTime.now())));
        scheduleItem.setDay(DayOfWeek.from(LocalDateTime.now().plusDays(1)));
        scheduleItem.setStartHour(1);
        scheduleItem.setEndHour(2);
        List<ScheduleItem> scheduleItems = new ArrayList<>();
        scheduleItems.add(scheduleItem);
        Schedule schedule = new Schedule(1L, medic, scheduleItems);

        Mockito.when(medicRepository.getMedicByIdUser(appointment.getMedicID())).thenReturn(medic);
        Mockito.when(patientRepository.getPatientByIdUser(appointment.getPatientID())).thenReturn(patient);
        Mockito.when(scheduleRepository.findByMedic(medic)).thenReturn(schedule);

        assertThrows(Exception.class,
                () -> appointmentService.makeAppointment(appointment),
                "Invalid day in medic schedule"
        );
    }

    @Test
    public void shouldThrowInvalidIntervalInMedicSchedule() throws Exception {
        AppointmentDTO appointment = new AppointmentDTO(1L,1L, DayOfWeek.from(LocalDateTime.now()),1);
        Medic medic = new Medic(
                1L,
                "dermatologist",
                "first degree",
                new UserDetail(
                        1L,
                        "FirstName",
                        "LastName",
                        new Address(1L,"Country", "City", "Street", "Number"),
                        new Date(2000,11,10),
                        "male",
                        "0788888888",
                        "img.jpg",
                        new User(1l, "test@email.com","password", Roles.MEDIC)
                )
        );
        Patient patient = new Patient(
                1L,
                "healthy",
                new UserDetail(
                        1L,
                        "FirstName",
                        "LastName",
                        new Address(1L,"Country", "City", "Street", "Number"),
                        new Date(2000,11,10),
                        "male",
                        "0788888888",
                        "image.png",
                        new User(1l, "test@email.com","password", Roles.PATIENT)
                )
        );
        ScheduleItem scheduleItem = new ScheduleItem();
        scheduleItem.setDate(getDateFromNextWeek(DayOfWeek.from(LocalDateTime.now())));
        scheduleItem.setDay(DayOfWeek.from(LocalDateTime.now()));
        scheduleItem.setStartHour(9);
        scheduleItem.setEndHour(10);
        List<ScheduleItem> scheduleItems = new ArrayList<>();
        scheduleItems.add(scheduleItem);
        Schedule schedule = new Schedule(1L, medic, scheduleItems);

        Mockito.when(medicRepository.getMedicByIdUser(appointment.getMedicID())).thenReturn(medic);
        Mockito.when(patientRepository.getPatientByIdUser(appointment.getPatientID())).thenReturn(patient);
        Mockito.when(scheduleRepository.findByMedic(medic)).thenReturn(schedule);

        assertThrows(Exception.class,
                () -> appointmentService.makeAppointment(appointment),
                "Invalid day in medic schedule"
        );
    }

    private LocalDate getDateFromNextWeek(DayOfWeek day) {
        LocalDate date = LocalDate.now();
        //to make sure that is the next week, we go on sunday
        return date.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).with(TemporalAdjusters.next(day));
    }
}
