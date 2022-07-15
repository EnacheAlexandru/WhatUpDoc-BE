package service.patientServiceTests;

import main.domain.*;
import main.repository.AddressRepository;
import main.repository.PatientRepository;
import main.repository.UserDetailRepository;
import main.repository.UserRepository;
import main.service.PatientService;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AddPatientTests {

    @Mock
    private UserRepository userRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private UserDetailRepository userDetailRepository;
    @Spy
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private PatientService patientService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shoudcallAddMethodsFromRepositories() throws Exception {
        Patient patient = new Patient(
                1L,
                "healthy",
                new UserDetail(
                        1L,
                        "FirstName",
                        "LastName",
                        new Address(1L,"Country", "City", "Street", "Number"),
                        new Date(2000,11,10),
                        "male",
                        "0788888888",
                        "image.png",
                        new User(1l, "test@email.com","password", Roles.PATIENT)
                )
        );

        patientService.registerPatient(patient);

        Mockito.verify(userRepository, Mockito.times(1))
                .save(Mockito.any(User.class));
        Mockito.verify(addressRepository, Mockito.times(1))
                .save(Mockito.any(Address.class));
        Mockito.verify(patientRepository, Mockito.times(1))
                .save(Mockito.any(Patient.class));
    }

}
