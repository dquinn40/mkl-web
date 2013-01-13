package com.stt.tagme;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import com.stt.tagme.config.SpringMongoConfig;
import com.stt.tagme.model.User;


public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		
		MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
		
		User user = new User("Dan", "Quinn", "DanDaMan-4");
		
		List<String> tagIds = new ArrayList<String>();
		tagIds.add("MK");
		tagIds.add("Bob-Lisa-Wedding");
		
		user.setTagIds(tagIds);

		mongoOperation.save(user, "users");
		
		System.out.println("Saved It!");
	}

}
