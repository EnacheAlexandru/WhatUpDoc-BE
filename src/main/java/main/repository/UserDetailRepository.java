package main.repository;

import main.domain.User;
import main.domain.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
    UserDetail findByUser(User user);
}
