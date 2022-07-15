package main.controller;


import main.controller.RequestDTO.AppointmentDTO;
import main.controller.ResponseDTO.PatientDetailsDTO;
import main.mapper.Mapper;
import main.service.IAppointmentService;
import main.service.IMedicService;
import main.service.IPatientService;
import main.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/whatsupdoc/patient")
public class PatientController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IPatientService patientService;
    @Autowired
    private IAppointmentService appointmentService;
    @Autowired
    private IMedicService medicService;
    @Autowired
    private Mapper mapper;


    @PostMapping("/appointment")
    public void makeAnAppointment(@RequestBody @Valid AppointmentDTO appointment) throws Exception {
        appointmentService.makeAppointment(appointment);
    }

    @GetMapping("/{idUserPatient}")
    public ResponseEntity<PatientDetailsDTO> getPatientDetails(@PathVariable String idUserPatient) throws Exception {
        return ResponseEntity.ok().body(patientService.getPatientByIdUser(idUserPatient));
    }

    @GetMapping("/medic/schedule")
    public ResponseEntity<String> viewSchedule(@RequestParam(value = "medicId", defaultValue = "") String medicIdStr,
                                       @RequestParam(value = "day", defaultValue = "") String dayStr) throws Exception {
        return ResponseEntity.ok().body(appointmentService.getValidIntervalForMedic(medicIdStr, dayStr));
    }
}
