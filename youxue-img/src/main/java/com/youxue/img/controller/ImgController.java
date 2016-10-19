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

import com.youxue.core.constant.ImgConstant;

@Controller
public class ImgController
{
	@RequestMapping("/img/{imgType}/{imgName}.png")
	public void getImage(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("imgType") String imgType, @PathVariable("imgName") String imgName) throws Exception
	{
		if (StringUtils.isBlank(imgName) || StringUtils.isBlank(imgType))
		{
			return;
		}
		BufferedImage image = ImageIO.read(new File(ImgConstant.getImgUrl(imgType, imgName)));
		response.setContentType("image/png");
		OutputStream os = response.getOutputStream();
		ImageIO.write(image, "png", os);
	}
}
