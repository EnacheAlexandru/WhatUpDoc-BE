package main.service;

import main.controller.RequestDTO.AppointmentDTO;
import main.controller.ResponseDTO.AppointmentCardItemDTO;
import main.controller.ResponseDTO.AppointmentForMedicCardItemDTO;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public interface IAppointmentService {
    void makeAppointment(AppointmentDTO appointment) throws Exception;

    LocalDate getDateFromNextWeek(DayOfWeek day);
    String getValidIntervalForMedic(String medicIdStr, String dayStr) throws Exception;

    List<AppointmentCardItemDTO> filterSortAppointments(String idUserPatient, String sort, String name, String date);
    List<AppointmentForMedicCardItemDTO> getAppointmentsByMedic(String idUserMedic);
}
