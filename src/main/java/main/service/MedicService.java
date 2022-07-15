package main.service;

import main.controller.ResponseDTO.MedicCardItemDTO;
import main.domain.*;
import main.mapper.Mapper;
import main.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicService implements IMedicService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserDetailRepository userDetailRepository;
    @Autowired
    private MedicRepository medicRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Mapper mapper;

    @Override
    public void registerMedic(Medic medic) throws Exception {
        User u = medic.getUserDetail().getUser();
        u.setRole(Roles.MEDIC);
        try {
            u.setPassword(passwordEncoder.encode(u.getPassword()));
            u = userRepository.save(u);
        } catch(Exception ex) {
            throw new Exception("Duplicate email entry");
        }

        UserDetail userDetail = medic.getUserDetail();
        userDetail.setUser(u);

        if(medic.getUserDetail().getAddress() != null) {
            Address address = addressRepository.save(medic.getUserDetail().getAddress());
            userDetail.setAddress(address);
        }
        userDetail = userDetailRepository.save(userDetail);
        medic.setUserDetail(userDetail);
        medicRepository.save(medic);
    }

    @Override
    public List<MedicCardItemDTO> filterSortMedics(String sort, String name, String city, String specialisation) {
        List<Medic> filteredSortedMedics = medicRepository.filterSortMedics(sort, name, city, specialisation);

        return filteredSortedMedics
                .stream()
                .map(m -> mapper.convertToMedicCardItemDTO(m))
                .collect(Collectors.toList());
    }
}
