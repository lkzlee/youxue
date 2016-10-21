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

	/**
	 * @param request
	 * @param response
	 * @param imgUrl 文件按功能划分的路径： 如缩略图、商品图等
	 * @param imgType 文件按后缀类型划分的路径
	 * @param imgName 文件名
	 * @throws Exception
	 */
	@RequestMapping("/img/{imgUrl}/{imgType}/{imgName}.png")
	public void getImage(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("imgUrl") String imgUrl, @PathVariable("imgType") String imgType,
			@PathVariable("imgName") String imgName) throws Exception
	{
		if (StringUtils.isBlank(imgUrl) || StringUtils.isBlank(imgName) || StringUtils.isBlank(imgType))
		{
			return;
		}
		String filePath = ImgConstant.getImgUrl(imgUrl, imgType, imgName);
		File file = new File(filePath);
		if (file == null || !file.exists())
		{
			System.out.println("file not exist,filePath:" + filePath);
			return;
		}
		BufferedImage image = ImageIO.read(file);
		response.setContentType("image/" + imgType);
		OutputStream os = response.getOutputStream();
		ImageIO.write(image, "png", os);
	}
}
