package main.repository;

import main.domain.Message;
import main.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "SELECT m FROM Message m WHERE (m.sender.id = :idSender AND m.receiver.id = :idReceiver) OR (m.sender.id = :idReceiver AND m.receiver.id = :idSender) ORDER BY m.dateTime ASC")
    List<Message> getMessagesBetweenUsers(
            @Param("idSender") Long idSender,
            @Param("idReceiver") Long idReceiver);

    List<Message> getAllBySender(User sender);

    List<Message> getAllByReceiver(User receiver);
}
