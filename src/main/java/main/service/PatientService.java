package main.service;

import main.controller.ResponseDTO.PatientDetailsDTO;
import main.controller.ResponseDTO.ViewDetailsMedicDTO;
import main.domain.*;
import main.mapper.Mapper;
import main.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class PatientService implements IPatientService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private UserDetailRepository userDetailRepository;
    @Autowired
    private MedicRepository medicRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Mapper mapper;

    @Override
    public void registerPatient(Patient patient) throws Exception {
        User u = patient.getUserDetail().getUser();
        u.setRole(Roles.PATIENT);

        try {
            u.setPassword(passwordEncoder.encode(u.getPassword()));
            u = userRepository.save(u);
        } catch (Exception ex) {
            throw new Exception("Duplicate email entry");
        }

        UserDetail userDetail = patient.getUserDetail();
        userDetail.setUser(u);

        if (patient.getUserDetail().getAddress() != null) {
            Address address = addressRepository.save(patient.getUserDetail().getAddress());
            userDetail.setAddress(address);
        }

        userDetail = userDetailRepository.save(userDetail);
        patient.setUserDetail(userDetail);
        patientRepository.save(patient);
    }

    @Override
    public ViewDetailsMedicDTO getDetailsAboutMedic(Long idUserMedic) throws Exception {

        Medic medic = medicRepository.getMedicByIdUser(idUserMedic);
        try {
            return mapper.covertToViewDetailsMedicDTO(medic);
        } catch (Exception ex) {
            throw new Exception("No medic with this ID was found!");
        }

    }

    @Override
    public PatientDetailsDTO getPatientByIdUser(String idUserPatient) throws Exception {
        long patientId;

        try {
            patientId = Long.parseLong(idUserPatient);
        } catch(Exception e) {
            throw new Exception("Invalid patient id");
        }

        Patient patient = patientRepository.getPatientByIdUser(patientId);

        if(patient == null)
            throw new Exception("Patient not found");

        UserDetail ud = patient.getUserDetail();

        return new PatientDetailsDTO(
                ud.getUser().getId(),
                ud.getFirstName(),
                ud.getLastName(),
                ud.getAddress(),
                ud.getBirth(),
                ud.getGender(),
                ud.getPhoneNumber(),
                ud.getUrlImage()
        );
    }

}
