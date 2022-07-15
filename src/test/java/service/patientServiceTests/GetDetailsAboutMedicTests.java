package service.patientServiceTests;

import main.controller.ResponseDTO.ViewDetailsMedicDTO;
import main.domain.*;
import main.mapper.Mapper;
import main.repository.MedicRepository;
import main.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.*;

import java.sql.Date;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GetDetailsAboutMedicTests {

    @Mock
    private MedicRepository medicRepository;
    @Spy
    private Mapper mapper;
    @InjectMocks
    private PatientService patientService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCallAddMethodsFromRepositories() throws Exception {
        Medic medic = new Medic(
                1L,
                "dermatologist",
                "first degree",
                new UserDetail(
                        1L,
                        "FirstName",
                        "LastName",
                        new Address(1L, "Country", "City", "Street", "Number"),
                        new Date(2000, 11, 10),
                        "male",
                        "0788888888",
                        "img.jpg",
                        new User(1l, "test@email.com", "password", Roles.MEDIC)
                )
        );
        ViewDetailsMedicDTO viewDetailsMedicDTO = new ViewDetailsMedicDTO(
                "FirstName",
                "LastName",
                "img.jpg",
                new Date(2000, 11, 10), "male",
                "0788888888",
                "test@email.com",
                new Address(1L, "Country", "City", "Street", "Number"),
                "dermatologist",
                "first degree"
                );

        Mockito.when(medicRepository.getMedicByIdUser(1L)).thenReturn(medic);
        Mockito.when(mapper.covertToViewDetailsMedicDTO(medic)).thenReturn(viewDetailsMedicDTO);

        patientService.getDetailsAboutMedic(1L);

        Mockito.verify(medicRepository, Mockito.times(1))
                .getMedicByIdUser(Mockito.anyLong());
        Mockito.verify(mapper, Mockito.times(1))
                .covertToViewDetailsMedicDTO(Mockito.any(Medic.class));
    }

    @Test
    public void shouldReturnCorrectMappedResponse() throws Exception {
        Medic medic = new Medic(
                1L,
                "dermatologist",
                "first degree",
                new UserDetail(
                        1L,
                        "FirstName",
                        "LastName",
                        new Address(1L, "Country", "City", "Street", "Number"),
                        new Date(2000, 11, 10),
                        "male",
                        "0788888888",
                        "image",
                        new User(1l, "test@email.com", "password", Roles.MEDIC)
                )
        );
        ViewDetailsMedicDTO medicDTO = new ViewDetailsMedicDTO(
                "FirstName",
                "LastName",
                "image",
                new Date(2000, 11, 10),
                "male",
                "0788888888",
                "test@email.com",
                new Address(1L, "Country", "City", "Street", "Number"),
                "dermatologist",
                "first degree"
        );

        Mockito.when(medicRepository.getMedicByIdUser(1L)).thenReturn(medic);

        var response = patientService.getDetailsAboutMedic(1L);

        assertEquals("Should be the same", response.getFirstname(), medicDTO.getFirstname());
        assertEquals("Should be the same", response.getLastname(), medicDTO.getLastname());
        assertEquals("Should be the same", response.getImageURL(), medicDTO.getImageURL());
        assertEquals("Should be the same", response.getBirth(), medicDTO.getBirth());
        assertEquals("Should be the same", response.getGender(), medicDTO.getGender());
        assertEquals("Should be the same", response.getNumber(), medicDTO.getNumber());
        assertEquals("Should be the same", response.getEmail(), medicDTO.getEmail());
        assertEquals("Should be the same", response.getSpecialization(), medicDTO.getSpecialization());
        assertEquals("Should be the same", response.getGrade(), medicDTO.getGrade());
    }
}
