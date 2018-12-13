package messagingmicroservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import messagingmicroservice.dao.MessageDAO;
import messagingmicroservice.domains.Message;
import messagingmicroservice.domains.User;

@RestController
@CrossOrigin
public class MessagingController {

	@Autowired
	MessageDAO messagedao;
	
	@RequestMapping("/messages")
	public List<Message> getAllMessages() {
		return messagedao.findAll();
	}
	
	@RequestMapping("/messages/{id}")
	public ResponseEntity<Message> getMessage(@PathVariable int id) {
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
	
	
	
	@RequestMapping("/messages/find/{user}")
	public ResponseEntity<List<Message>> getMessageForUser(User user) {
		List<Message> messages = messagedao.findByReciever(user.getUserid());
		ResponseEntity<List<Message>> response = null;
		if (messages == null) {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else {
			response = new ResponseEntity<>(messages, HttpStatus.OK);
		}
		return response;
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/messages")
	public ResponseEntity<Message> saveMessage(@RequestBody Message message) {
		ResponseEntity<Message> response;
		Message savedMessage = messagedao.save(message);
		response = new ResponseEntity<>(savedMessage, HttpStatus.CREATED);
		return response;
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/messages/{id}")
	public void deleteUser(@PathVariable Long id) {
		messagedao.deleteById(id);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/messages/{id}")
	public ResponseEntity<Message> updateMessage(@PathVariable int id , @RequestBody @Validated Message message) {
		Message messageDB = messagedao.findById(id);
		ResponseEntity<Message> response;

		if (messageDB != null){
			if (message.getText()!=null) {
				messageDB.setText(message.getText());
			}
			if (message.getReciever()!=null) {
				messageDB.setReciever(message.getReciever());
			}
			if (message.getSender()!=null) {
				messageDB.setSender(message.getSender());
			}
			if (message.getTime_stamp()!=null) {
				messageDB.setTime_stamp(message.getTime_stamp());
			}
			response = new ResponseEntity<>(messageDB, HttpStatus.OK);
			return response;
		}
		response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return response;
	}
	
}
