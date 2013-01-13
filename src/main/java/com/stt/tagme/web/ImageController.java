package com.stt.tagme.web;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.stt.tagme.model.Image;

@Controller
public class ImageController {

	private MongoTemplate mongoTemplate;
	private MongoDbFactory mongoDbFactory;
	private Logger logger = LoggerFactory.getLogger(ImageController.class);
	
	@RequestMapping(value="/{userId}/{tagId}/image", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody void addImage(@RequestParam MultipartFile file, @PathVariable String userId, @PathVariable String tagId) throws Exception {
		logger.info("Received image file orig: " + file.getOriginalFilename() + " name: " + file.getName());
		
		GridFS gridFs = new GridFS(this.mongoDbFactory.getDb());
		GridFSInputFile inputFile = gridFs.createFile(file.getInputStream(), file.getOriginalFilename(), true);
		inputFile.setContentType("image/jpeg");
		inputFile.save();
		
		Image image = new Image(inputFile.getId().toString(), file.getOriginalFilename(), userId, tagId, file.getSize());

		mongoTemplate.save(image, "images");
	}
	
	@RequestMapping(value="/image/{id}", headers="Accept=image/jpeg, image/jpg, image/png, image/gif", method = RequestMethod.GET)
	public @ResponseBody byte[] getImage(@PathVariable String id) throws Exception {
		GridFS gridFs = new GridFS(this.mongoDbFactory.getDb());
		GridFSDBFile dbFile = gridFs.find(new ObjectId(id));
		return IOUtils.toByteArray(dbFile.getInputStream());
	}

	@RequestMapping(value="/image/{id}/thumbnail", headers="Accept=image/jpeg, image/jpg, image/png, image/gif", method = RequestMethod.GET)
	public @ResponseBody byte[] getImageThumbnail(@PathVariable String id) throws Exception {
		GridFS gridFs = new GridFS(this.mongoDbFactory.getDb());
		GridFSDBFile dbFile = gridFs.find(new ObjectId(id));
		
		return resizeImage(dbFile.getInputStream(), 50);
	}
	
	@RequestMapping(value="/image/{id}/preview", headers="Accept=image/jpeg, image/jpg, image/png, image/gif", method = RequestMethod.GET)
	public @ResponseBody byte[] getImagePreview(@PathVariable String id) throws Exception {
		GridFS gridFs = new GridFS(this.mongoDbFactory.getDb());
		GridFSDBFile dbFile = gridFs.find(new ObjectId(id));
		
		return resizeImage(dbFile.getInputStream(), 150);
	}
	
	private byte[] resizeImage(InputStream inputStream, int dimensions) throws Exception {
		BufferedImage originalImage = ImageIO.read(inputStream);
		BufferedImage resizedImage = new BufferedImage(dimensions, dimensions, originalImage.getType());
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, dimensions, dimensions, null);
		g.dispose();
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(resizedImage, "jpg", baos);
		byte[] ba = baos.toByteArray();
		baos.close();
	 
		return ba;
	}
	
	@Autowired
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	@Autowired
	public void setMongoDbFactory(MongoDbFactory mongoDbFactory) {
		this.mongoDbFactory = mongoDbFactory;
	}
	
}