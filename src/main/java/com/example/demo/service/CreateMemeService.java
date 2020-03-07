package com.example.demo.service;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.example.demo.dtos.MemeDTO;

@Service
public class CreateMemeService {
	
	private static String fileName = "test.jpg";
	
	public String createMeme(MemeDTO dto) {
		BufferedImage scaleimg = scaleImage(dto.getImageName());
		scaleimg=addTextToImage(scaleimg, dto.getMemeText());
		String fileLocation = new File("static\\tmp\\").getAbsolutePath() ;
		
		try {
			
			fileLocation=fileLocation+"\\"+fileName;
			ImageIO.write(scaleimg, "jpg", new File(fileLocation));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileLocation;

	}

	private BufferedImage scaleImage(String imageName) {
		BufferedImage scaleimg=null;
		BufferedImage resized = null;
		try {
			
			//File file = ResourceUtils.getFile("classpath:application.properties");
			File file = ResourceUtils.getFile("classpath:static/"+imageName);
			scaleimg  = ImageIO.read(file);
		    int dWidth =  (int) (scaleimg.getWidth()*(0.75)) ; 
		    int dHeight = (int) (scaleimg.getHeight()*(0.75));
		    resized = new BufferedImage(dWidth, dHeight, scaleimg.getType());
		    Graphics2D graphics2D = resized.createGraphics();
            graphics2D.drawImage(scaleimg, 0, 0, dWidth, dHeight, null);
            graphics2D.dispose();
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		return resized;

	}
	
	private BufferedImage addTextToImage(BufferedImage img ,String text) {
		Graphics2D grphcs = img.createGraphics();
		grphcs.setFont(grphcs.getFont().deriveFont(20f));
		grphcs.drawString(text, 170, 30);
		grphcs.dispose();
		return img;
	}
}
