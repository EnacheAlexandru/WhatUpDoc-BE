package main.repository;

import main.domain.Appointment;
import main.domain.Medic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Appointment findByDateAndMedicAndStartTime(LocalDate date, Medic medic, Integer startTime);
    List<Appointment> findByDateAndMedic(LocalDate date, Medic medic);

    @Query(value = "SELECT a FROM Appointment a WHERE (:date = '2022-01-01'  OR a.date = :date) AND (a.date > current_date) AND (:name = ''  OR a.medic.userDetail.lastName = :name) AND (a.patient.userDetail.user.id = :idUserPatient) ORDER BY CASE WHEN :sort = 'desc' THEN CONCAT(a.medic.userDetail.lastName, ' ', a.medic.userDetail.firstName) END DESC, CASE WHEN :sort <> 'desc' THEN CONCAT(a.medic.userDetail.lastName, ' ', a.medic.userDetail.firstName) END ASC")
    List<Appointment> filterSortAppointments(
            @Param("idUserPatient") Long idUserPatient,
            @Param("sort") String sort,
            @Param("name") String name,
            @Param("date") String date
    );

    @Query(value = "SELECT a FROM Appointment a WHERE (a.medic.userDetail.user.id = :idUserMedic) ORDER BY a.date, a.startTime ASC")
    List<Appointment> getAppointmentsByMedic(
            @Param("idUserMedic") Long idUserMedic

    );
}