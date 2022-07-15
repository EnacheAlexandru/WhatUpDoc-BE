package main.repository;

import main.domain.Medic;
import main.domain.Patient;
import main.domain.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByUserDetail(UserDetail userDetail);

    @Query(value = "SELECT p FROM Patient p WHERE p.userDetail.user.id = :idUserPatient")
    Patient getPatientByIdUser(@Param("idUserPatient") Long idUserPatient);
}
