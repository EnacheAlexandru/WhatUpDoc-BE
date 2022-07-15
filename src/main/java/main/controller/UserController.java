package main.controller;

import main.controller.RequestDTO.LoginRequest;
import main.controller.ResponseDTO.*;
import main.controller.RequestDTO.MedicDTO;
import main.controller.RequestDTO.PatientDTO;
import main.domain.Medic;
import main.domain.Patient;
import main.domain.User;
import main.domain.VerificationToken;
import main.mapper.Mapper;
import main.service.IAppointmentService;
import main.service.IMedicService;
import main.service.IPatientService;
import main.service.IUserService;
import main.service.email.Utils;
import main.service.email.events.OnRegistrationCompleteEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.ui.Model;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/whatsupdoc/users")
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IPatientService patientService;
    @Autowired
    private IMedicService medicService;
    @Autowired
    private IAppointmentService appointmentService;
    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    private Mapper mapper;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequest loginRequest) throws Exception {
        return ResponseEntity.ok().body(userService.login(loginRequest.getEmail(), loginRequest.getPassword()));
    }

    @PostMapping("/patient/register")
    public void registerPatient(@RequestBody @Valid PatientDTO regRequest) throws Exception {
        Patient patient = mapper.convertToPatient(regRequest);
        patient.getUserDetail().getUser().setIsEnabled(false);
        patientService.registerPatient(patient);
        //String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(patient.getUserDetail().getUser(), "/whatsupdoc/users/patient/register"));

    }

    @RequestMapping(value = "/patient/register/registrationConfirm", method = RequestMethod.GET)
    public String confirmRegistration
            (@RequestParam("token") String token) {

        VerificationToken verificationToken = userService.getVerificationToken(token);

        if (verificationToken == null) {
            return Utils.getErrorPage();
        }

        User user = verificationToken.getUser();
        if (!user.getIsEnabled()) {
            Calendar cal = Calendar.getInstance();
            if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
                //throw new Exception("This link has expired!");
                return Utils.getErrorPage();
            }

            userService.enableUser(user);
        } else {
            return Utils.getErrorPage();
        }
        return Utils.getConfirmPage();

    }

    @PostMapping("/medic/register")
    public void registerMedic(@RequestBody @Valid MedicDTO regRequest) throws Exception {
        Medic medic = mapper.convertToMedic(regRequest);
        medicService.registerMedic(medic);
    }

    /**
     * <p>GET request using GET parameters. Filters medics by name, city and specialisation then sorts
     * alphabetically (or reverse order) by name (last name then first name).
     * URI syntax example (parameters can be in any order or some can be missing):</p>
     * <p>.../patient/medics?name=Montana&city=Miami&specialisation=toxicolog&sort=desc</p>
     * <p>If there are no filter parameters, the whole list is returned; if the sort parameter is present, the whole
     * list is sorted accordingly.
     * All parameters are case-insensitive.</p>
     *
     * @param name           can be optional; can also be partial.
     * @param city           can be optional; must match exactly.
     * @param specialisation can be optional; must match exactly.
     * @param sort           if 'desc', the list is sorted alphabetically in reverse order;
     *                       if not present, or has a value other than 'desc', then the list is sorted alphabetically.
     * @return the filtered and sorted list of medics, in JSON format; the medic object has the following fields:
     * id, lastName, firstName, city, specialisation and urlImage.
     **/
    @GetMapping("/patient/medics")
    public List<MedicCardItemDTO> filterSortMedics(
            @RequestParam(required = false, defaultValue = "asc") String sort,
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "") String city,
            @RequestParam(required = false, defaultValue = "") String specialisation
    ) {
        return medicService.filterSortMedics(sort, name, city, specialisation);
    }

    @GetMapping("/patient/medic/{idUserMedic}")
    public @ResponseBody
    ViewDetailsMedicDTO viewDetailsMedic(@PathVariable @Valid Long idUserMedic) throws Exception {
        return patientService.getDetailsAboutMedic(idUserMedic);
    }

    @GetMapping("/patient/appointment/{idUserPatient}")
    public List<AppointmentCardItemDTO> filterSortAppointments(
            @PathVariable String idUserPatient,
            @RequestParam(required = false, defaultValue = "asc") String sort,
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "2022-01-01") String date

    ) {
        return appointmentService.filterSortAppointments(idUserPatient, sort, name, date);
    }

    @GetMapping("/medic/appointment/{idUserMedic}")
    public List<AppointmentForMedicCardItemDTO> getAppointmentsByMedic(
            @PathVariable String idUserMedic

    ) {
        return appointmentService.getAppointmentsByMedic(idUserMedic);
    }


}
