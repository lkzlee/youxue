package com.youxue.img.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.lkzlee.pay.utils.DateUtil;
import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.constant.ImgConstant;
import com.youxue.core.util.JsonUtil;
import com.youxue.img.dto.ImageResultDto;

@Controller
public class ImgController
{
	private static final Log log = LogFactory.getLog(ImgController.class);

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

	@RequestMapping("/img/uploadImg.html")
	@ResponseBody
	public String uploadImage(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "uploadFile", required = false) MultipartFile uploadFile) throws Exception
	{
		if (uploadFile == null)
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("上传文件不存在"));
		try
		{
			String path = request.getSession().getServletContext().getRealPath("upload");
			String fileName = uploadFile.getOriginalFilename();
			String prefix = DateUtil.formatNowTime(DateUtil.FMT_DATE_YYYYMMDDHHMMSS);
			fileName = fileName.substring(0, fileName.indexOf(".")) + "_" + prefix
					+ fileName.substring(fileName.indexOf("."));
			log.info("文件路径:" + path + ",文件名：fileName=" + fileName);
			File targetFile = new File(path, fileName);
			if (!targetFile.exists())
			{
				targetFile.mkdirs();
			}
			uploadFile.transferTo(targetFile);
			String httpFileUrl = request.getContextPath() + "/upload/" + fileName;
			ImageResultDto resultDto = new ImageResultDto();
			resultDto.setImageUlr(httpFileUrl);
			resultDto.setResult(100);
			resultDto.setDesc("上传成功");
			return JsonUtil.serialize(resultDto);
		}
		catch (Exception e)
		{
			log.fatal("上传文件异常，msg:" + e.getMessage(), e);
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("上传文件失败，系统繁忙"));
		}

	}
}
