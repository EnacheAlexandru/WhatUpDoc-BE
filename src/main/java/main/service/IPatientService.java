package main.service;

import main.controller.RequestDTO.AppointmentDTO;
import main.controller.RequestDTO.MedicDTO;
import main.controller.ResponseDTO.PatientDetailsDTO;
import main.controller.ResponseDTO.ViewDetailsMedicDTO;
import main.domain.Medic;
import main.domain.Patient;

public interface IPatientService {
    void registerPatient(Patient patient) throws Exception;

    ViewDetailsMedicDTO getDetailsAboutMedic(Long idUserMedic) throws Exception;

    PatientDetailsDTO getPatientByIdUser(String idUserPatient) throws Exception;
}
