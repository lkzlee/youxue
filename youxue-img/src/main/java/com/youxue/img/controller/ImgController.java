package com.youxue.img.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youxue.core.constant.ImgConstant;

@Controller
public class ImgController
{
	@RequestMapping("/index.html")
	@ResponseBody
	public String index(HttpServletRequest request, HttpServletResponse response)
	{
		return "hello world";
	}

	@RequestMapping("/img/{imgType}/{imgName}")
	public void getImage(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("imgType") String imgType, @PathVariable("imgName") String imgName) throws Exception
	{
		if (StringUtils.isBlank(imgType) || StringUtils.isBlank(imgName))
		{
			return;
		}
		if (!"png".equals(imgType) && !"jpg".equals(imgType))
		{
			return;
		}
		String filePath = ImgConstant.getImgFilePath(imgType + File.separator + imgName + "." + imgType);
		File file = new File(filePath);
		if (file == null || !file.exists())
		{
			System.out.println("file not exist,filePath:" + filePath);
			return;
		}
		BufferedImage image = ImageIO.read(file);
		response.setContentType("image/" + imgType);
		OutputStream os = response.getOutputStream();
		ImageIO.write(image, imgType, os);
	}
}
