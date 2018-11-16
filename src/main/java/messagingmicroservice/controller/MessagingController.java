package messagingmicroservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import messagingmicroservice.dao.MessageDAO;
import messagingmicroservice.domains.Message;
import messagingmicroservice.domains.User;

@Controller
@CrossOrigin
public class MessagingController {

	@Autowired
	MessageDAO messagedao;
	
	@RequestMapping("/messages")
	public List<Message> getMessages() {
		return getAllMessages();
	}
	
	@RequestMapping("/messages")
	public List<Message> getAllMessages() {
		return messagedao.findAll();
	}
	
	@RequestMapping("/messages/{id}")
	public ResponseEntity<Message> getMessage(int id) {
		Message message = messagedao.findById(id);
		ResponseEntity<Message> response = null;
		if (message == null) {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else {
			response = new ResponseEntity<>(message, HttpStatus.OK);
		}
		return response;
	}
	
	@RequestMapping("/messages/{user}")
	public @ResponseBody List<Message> getMessageForUser(User user) {
		return messagedao.findByReciever(user);
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/messages")
	public ResponseEntity<Message> saveMessage(@RequestBody Message message) {
		ResponseEntity<Message> response;
		Message savedMessage = messagedao.save(message);
		response = new ResponseEntity<>(savedMessage, HttpStatus.CREATED);
		return response;
	}
	
}
