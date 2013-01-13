package com.stt.tagme.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


import com.stt.tagme.model.User;

@Controller
public class UserController {
	private MongoTemplate mongoTemplate;
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@RequestMapping(value ="/user/{name}", method=RequestMethod.GET)
	public @ResponseBody User getUser(@PathVariable String name) {
		logger.info("Request for user " + name);
		User user = new User("Dan", "Quinn", name);
		return user;
	}
	
	@RequestMapping(value="/user", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody void setUser(@RequestBody User user) {
		this.mongoTemplate.save(user, "users");
	}
	
	@Autowired
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

}
