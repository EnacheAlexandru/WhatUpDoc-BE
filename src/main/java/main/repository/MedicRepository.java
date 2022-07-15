package main.repository;

import main.domain.Medic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicRepository extends JpaRepository<Medic, Long> {

    @Query(value = "SELECT m FROM Medic m WHERE (:city = '' OR m.userDetail.address.city = :city) AND (:name = '' OR CONCAT(m.userDetail.lastName, ' ', m.userDetail.firstName) LIKE CONCAT('%', :name, '%')) AND (:specialisation = '' OR m.specialisation = :specialisation) ORDER BY CASE WHEN :sort = 'desc' THEN CONCAT(m.userDetail.lastName, ' ', m.userDetail.firstName) END DESC, CASE WHEN :sort <> 'desc' THEN CONCAT(m.userDetail.lastName, ' ', m.userDetail.firstName) END ASC")
    List<Medic> filterSortMedics(
            @Param("sort") String sort,
            @Param("name") String name,
            @Param("city") String city,
            @Param("specialisation") String specialisation
    );

    @Query(value = "SELECT m FROM Medic m WHERE m.userDetail.user.id = :idUserMedic")
    Medic getMedicByIdUser(@Param("idUserMedic") Long idUserMedic);


}
