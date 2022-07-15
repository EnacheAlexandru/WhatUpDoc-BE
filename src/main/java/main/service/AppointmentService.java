package main.service;

import main.controller.RequestDTO.AppointmentDTO;
import main.controller.ResponseDTO.AppointmentCardItemDTO;
import main.controller.ResponseDTO.AppointmentForMedicCardItemDTO;
import main.domain.*;
import main.mapper.Mapper;
import main.repository.AppointmentRepository;
import main.repository.MedicRepository;
import main.repository.PatientRepository;
import main.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService implements IAppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private MedicRepository medicRepository;
    @Autowired
    private Mapper mapper;

    @Override
    public void makeAppointment(AppointmentDTO appointment) throws Exception {
        LocalDate date = getDateFromNextWeek(appointment.getDay());
        Medic medic;
        Patient patient;

        medic = medicRepository.getMedicByIdUser(appointment.getMedicID());
        if (medic == null) {
            throw new Exception("Medic not found");
        }

        patient = patientRepository.getPatientByIdUser(appointment.getPatientID());
        if (patient == null) {
            throw new Exception("Patient not found");
        }

        Schedule schedule = scheduleRepository.findByMedic(medic);
        if (schedule == null) {
            throw new Exception("No schedule found!");
        }

        boolean validDateInMedicSchedule = false;
        boolean validDayInMedicSchedule = false;
        boolean validHourInMedicSchedule = false;
        for (ScheduleItem item : schedule.getScheduleItems()) {
            if (item.getDate().equals(date)) {
                validDateInMedicSchedule = true;
                if (item.getDay() == appointment.getDay()) {
                    validDayInMedicSchedule = true;
                    if (item.getStartHour() <= appointment.getHour() && appointment.getHour() < item.getEndHour()) {
                        validHourInMedicSchedule = true;
                    }
                }
            }
        }

        if (!validDateInMedicSchedule) {
            throw new Exception("Invalid date in medic schedule");
        }

        if (!validDayInMedicSchedule) {
            throw new Exception("Invalid day in medic schedule");
        }

        if (!validHourInMedicSchedule) {
            throw new Exception("Invalid interval in medic schedule");
        }

        if (appointmentRepository.findByDateAndMedicAndStartTime(date, medic, appointment.getHour()) != null) {
            throw new Exception("This interval was taken by someone else");
        }

        Appointment newAppointment = new Appointment();
        newAppointment.setMedic(medic);
        newAppointment.setPatient(patient);
        newAppointment.setDate(date);
        newAppointment.setStartTime(appointment.getHour());
        appointmentRepository.save(newAppointment);
    }

    public LocalDate getDateFromNextWeek(DayOfWeek day) {
        LocalDate date = LocalDate.now();
        //to make sure that is the next week, we go on sunday
        return date.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).with(TemporalAdjusters.next(day));
    }

    /**
     <p> Get free intervals for a medic on specified day.
     If there is no schedule for that day, the method throws an exception
     If the medic's schedule is full, the method throws an exception
     </p>
     * @param medicIdStr medic's id
     * @param dayStr the day we want to see medic's schedule
     * @return schedule string of starting hours
     * @throws Exception if the medic is unavailable for that day
     */
    public String getValidIntervalForMedic(String medicIdStr, String dayStr) throws Exception {

        long medicId;
        try {
            medicId = Long.parseLong(medicIdStr);
        }
        catch (Exception e) {
            throw new Exception("Medic ID not a number!");
        }

        Medic medic = medicRepository.getMedicByIdUser(medicId);
        if (medic == null) {
            throw new Exception("Medic not found");
        }

        DayOfWeek day;
        try {
            day = DayOfWeek.valueOf(dayStr);
        }
        catch (Exception e) {
            throw new Exception("Invalid day!");
        }

        LocalDate date = getDateFromNextWeek(day);
        Schedule schedule = scheduleRepository.findByMedic(medic);

        if (schedule == null) {
            throw new Exception("The medic is unavailable for this day!");
        }

        ScheduleItem item = null;
        List<Integer> scheduleInts;
        StringBuilder scheduleString = new StringBuilder();

        for (ScheduleItem scheduleItem : schedule.getScheduleItems()) {
            if (scheduleItem.getDate().equals(date)) {
                System.out.println(scheduleItem.getDate());
                item = scheduleItem;
                break;
            }
        }

        if (item == null) {
            throw new Exception("Unavailable day");
        }

        scheduleInts = IntStream.rangeClosed(item.getStartHour(), item.getEndHour() - 1)
                .boxed().collect(Collectors.toList());

        final List<Appointment> appointments = appointmentRepository.findByDateAndMedic(date, medic);

        scheduleInts.removeIf((x) -> {
            for (Appointment appointment : appointments) {
                if (appointment.getStartTime().equals(x)) {
                    return true;
                }
            }
            return false;
        });

        if (scheduleInts.isEmpty()) {
            throw new Exception("Unavailable intervals for appointments");
        }

        for (Integer integer : scheduleInts) {
            scheduleString.append(integer.toString()).append('-');
        }

        String res = scheduleString.toString();
        return res.substring(0, scheduleString.length() - 1);
    }

    @Override
    public List<AppointmentCardItemDTO> filterSortAppointments(String idUserPatient, String sort, String name, String date) {
        List<Appointment> filteredSortedAppointments = appointmentRepository.filterSortAppointments(Long.parseLong(idUserPatient), sort, name, date);
        return filteredSortedAppointments
                .stream()
                .map(a -> mapper.convertToAppointmentCardItemDTO(a))
                .collect(Collectors.toList());
    }
    @Override
    public List<AppointmentForMedicCardItemDTO> getAppointmentsByMedic(String idUserMedic) {
        List<Appointment> getAppointmentsByMedic = appointmentRepository.getAppointmentsByMedic(Long.parseLong(idUserMedic));
        return getAppointmentsByMedic
                .stream()
                .map(a -> mapper.convertToAppointmentForMedicCardItemDTO(a))
                .collect(Collectors.toList());
    }
}
