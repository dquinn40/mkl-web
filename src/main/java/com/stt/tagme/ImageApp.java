package com.stt.tagme;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;

import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;
import com.stt.tagme.config.SpringMongoConfig;
import com.stt.tagme.model.User;


public class ImageApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		
		MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
		MongoDbFactory dbFactory = (MongoDbFactory)ctx.getBean("mongoDbFactory");
		
		GridFS gridFs = new GridFS(dbFactory.getDb());
		File file = new File("//Users//dquinn40//Pictures//gizmo//DSC00093.JPG");
		ByteArrayInputStream is = new ByteArrayInputStream(getImageBytes(file));
		GridFSInputFile inputFile = gridFs.createFile(is, file.getName(), true);
		inputFile.setContentType("image/jpeg");
		inputFile.save();
		
		System.out.println("Saved image as id " + inputFile.getId().toString());
		
		User user = new User("Dan", "Quinn", "DanDaMan-4");
		
		List<String> tagIds = new ArrayList<String>();
		tagIds.add("MK");
		tagIds.add("Bob-Lisa-Wedding");
		
		user.setTagIds(tagIds);

		mongoOperation.save(user, "users");
		
		System.out.println("Saved It!");
	}
	
	private static byte[] getImageBytes(File file) throws Exception {
		System.out.println("filename: " + file.getName());
		BufferedImage buf = ImageIO.read(file);
		System.out.println("image type: " + buf.getType());
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(buf, "jpg", os);
		os.flush();
		byte[] imageInBytes = os.toByteArray();
		os.close();
		return imageInBytes;
	}

}
