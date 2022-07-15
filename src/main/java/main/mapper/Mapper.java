package main.mapper;

import main.controller.RequestDTO.*;
import main.controller.ResponseDTO.*;
import main.domain.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    @Autowired
    private ModelMapper modelMapper;

    public Patient convertToPatient(PatientDTO patientDTO) {
        Patient patient = modelMapper.map(patientDTO, Patient.class);
        return patient;
    }


    public Medic convertToMedic(MedicDTO medicDTO) {
        Medic medic = modelMapper.map(medicDTO, Medic.class);
        return medic;
    }

    public Address convertToAddress(AddressDTO addressDTO) {
        Address address = modelMapper.map(addressDTO, Address.class);
        return address;
    }
    public User convertToUser(LoginRequest userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        return user;
    }

    public UserDetail convertToUserDetail(UserDetailDTO userDetailDTO) {
        UserDetail userDetail = modelMapper.map(userDetailDTO, UserDetail.class);
        return userDetail;
    }

    public MessageSenderNameDTO convertToMessageSenderNameDTO(Message message, String firstName, String lastName) {
        MessageSenderNameDTO messageSenderNameDTO = new MessageSenderNameDTO(message.getSender().getId(), firstName, lastName, message.getReceiver().getId(), message.getMessage(), message.getDateTime());
        return messageSenderNameDTO;
    }

    public MedicCardItemDTO convertToMedicCardItemDTO(Medic medic) {
        return new MedicCardItemDTO(
                medic.getUserDetail().getUser().getId(),
                medic.getUserDetail().getLastName(),
                medic.getUserDetail().getFirstName(),
                medic.getUserDetail().getAddress().getCity(),
                medic.getSpecialisation(),
                medic.getUserDetail().getUrlImage());
    }

    public AppointmentCardItemDTO convertToAppointmentCardItemDTO(Appointment appointment) {
        return new AppointmentCardItemDTO(
                appointment.getId(),
                appointment.getMedic().getUserDetail().getFirstName(),
                appointment.getMedic().getUserDetail().getLastName(),
                appointment.getDate().toString(),
                appointment.getStartTime(),
                appointment.getMedic().getUserDetail().getAddress().getCity(),
                appointment.getMedic().getUserDetail().getAddress().getStreet(),
                appointment.getMedic().getUserDetail().getAddress().getNumber());
    }

    public AppointmentForMedicCardItemDTO convertToAppointmentForMedicCardItemDTO(Appointment appointment) {
        return new AppointmentForMedicCardItemDTO(
                appointment.getId(),
                appointment.getPatient().getUserDetail().getFirstName(),
                appointment.getPatient().getUserDetail().getLastName(),
                appointment.getPatient().getMedicalDetails(),
                appointment.getPatient().getUserDetail().getPhoneNumber(),
                appointment.getDate().toString(),
                appointment.getStartTime());
    }

    public ViewDetailsMedicDTO covertToViewDetailsMedicDTO(Medic medic) {
        return new ViewDetailsMedicDTO(
                medic.getUserDetail().getFirstName(),
                medic.getUserDetail().getLastName(),
                medic.getUserDetail().getUrlImage(),
                medic.getUserDetail().getBirth(),
                medic.getUserDetail().getGender(),
                medic.getUserDetail().getPhoneNumber(),
                medic.getUserDetail().getUser().getEmail(),
                medic.getUserDetail().getAddress(),
                medic.getSpecialisation(),
                medic.getDegree());
    }
}
