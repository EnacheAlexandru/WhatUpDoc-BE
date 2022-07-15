package service.medicServiceTests;


import main.controller.ResponseDTO.MedicCardItemDTO;
import main.domain.*;
import main.mapper.Mapper;
import main.repository.*;
import main.service.MedicService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FilterSortMedicsTests {

    @Mock
    private MedicRepository medicRepository;
    @Spy
    private Mapper mapper;
    @InjectMocks
    private MedicService medicService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shoudCallAddMethodsFromRepositories() throws Exception {

        medicService.filterSortMedics("asc", "John", "NY", "dermatologist");

        Mockito.verify(medicRepository, Mockito.times(1))
                .filterSortMedics(Mockito.any(String.class),Mockito.any(String.class),Mockito.any(String.class),Mockito.any(String.class));
    }

    @Test
    public void shoudReturnCorrectMappedResponse() throws Exception {

        List<Medic> medics = new ArrayList<>();
        medics.add(new Medic(1L, "dermatologist","first",new UserDetail(
                1L,
                "John1",
                "LastName1",
                new Address(1L,"Country", "City", "Street", "Number"),
                new Date(2000,11,10),
                "male",
                "0788888888",
                "img.jpg",
                new User(1l, "test@email.com","password", Roles.MEDIC)
        )));
        medics.add(new Medic(2L, "dermatologist","first",new UserDetail(
                2L,
                "John2",
                "LastName2",
                new Address(2L,"Country", "City", "Street", "Number"),
                new Date(2000,11,10),
                "male",
                "0788888888",
                "img.jpg",
                new User(2l, "test@email.com","password", Roles.MEDIC)
        )));
        medics.add(new Medic(3L, "dermatologist","first",new UserDetail(
                3L,
                "John3",
                "LastName3",
                new Address(3L,"Country", "City", "Street", "Number"),
                new Date(2000,11,10),
                "male",
                "0788888888",
                "img.jpg",
                new User(3l, "test@email.com","password", Roles.MEDIC)
        )));

        List<MedicCardItemDTO> cards = new ArrayList<>();
        cards.add(new MedicCardItemDTO(1L,"LastName1","John1","City","dermatologist","img.jpg"));
        cards.add(new MedicCardItemDTO(2L,"LastName2","John2","City","dermatologist","img.jpg"));
        cards.add(new MedicCardItemDTO(3L,"LastName3","John3","City","dermatologist","img.jpg"));

        Mockito.when(medicRepository.filterSortMedics("asc", "John", "City", "dermatologist")).thenReturn(medics);

        List<MedicCardItemDTO> response = medicService.filterSortMedics("asc", "John", "City", "dermatologist");

        assertEquals("Should be the same",response,cards);
    }
}
