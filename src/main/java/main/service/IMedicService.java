package main.service;

import main.controller.ResponseDTO.MedicCardItemDTO;
import main.domain.Medic;

import java.util.List;

public interface IMedicService {

    void registerMedic(Medic medic) throws Exception;

    List<MedicCardItemDTO> filterSortMedics(String sort, String name, String city, String specialisation);

}
