package messagingmicroservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import messagingmicroservice.domains.Message;
import messagingmicroservice.domains.User;

public interface MessageDAO extends CrudRepository<Message, Long> {
	@Query("Select m from Message where m.messageid=:messageid")
	public Message findById(@Param("messageid") int id);
	
	public List<Message> findByReciever(User reciever);

	public List<Message> findAll();


}
