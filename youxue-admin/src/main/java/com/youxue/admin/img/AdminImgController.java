package com.youxue.admin.img;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.baidu.ueditor.ActionEnter;
import com.google.common.collect.Maps;
import com.lkzlee.pay.utils.DateUtil;
import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.constant.ImgConstant;
import com.youxue.core.util.JsonUtil;

@Controller
public class AdminImgController
{
	private static final Log log = LogFactory.getLog(AdminImgController.class);

	@RequestMapping("/img/{businessType}/uploadImg.do")
	@ResponseBody
	public String uploadImage(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("businessType") String businessType,
			@RequestParam(value = "imgFile", required = false) MultipartFile imgFile) throws Exception
	{
		if (imgFile == null)
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("上传文件不存在"));
		try
		{
			String fileName = imgFile.getOriginalFilename();
			String prefix = DateUtil.formatNowTime(DateUtil.FMT_DATE_YYYYMMDDHHMMSS);
			String headName = fileName.substring(0, fileName.indexOf("."));
			String fileType = fileName.substring(fileName.indexOf(".") + 1);
			String realdFileName = headName + "_" + prefix + "." + fileType;
			String imgPath = businessType + File.separator + fileType + File.separator + realdFileName;
			String filePath = ImgConstant.getImgFilePath(imgPath);
			log.info("文件路径:" + filePath + ",文件名：fileName=" + fileName);
			File targetFile = new File(filePath);
			if (!targetFile.exists())
			{
				targetFile.mkdirs();
			}
			imgFile.transferTo(targetFile);
			String httpFileUrl = ImgConstant.getHttpImgUrl("/img/" + businessType + "/" + fileType + "/" + headName
					+ "_" + prefix);
			Map<String, Object> resultMap = Maps.newHashMap();
			resultMap.put("url", httpFileUrl);
			resultMap.put("retCode", 200);
			resultMap.put("msg", "success");
			return JsonUtil.serialize(resultMap);
		}
		catch (Exception e)
		{
			log.fatal("上传文件异常，msg:" + e.getMessage(), e);
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("上传文件失败，系统繁忙"));
		}

	}

	@RequestMapping("/ueditorUpload.do")
	public void ueditorUpload(HttpServletRequest request, HttpServletResponse response, String action) throws Exception
	{
		//		log.info("get ueditorUpload request,action:" + action);
		try
		{
			if (!"config".equalsIgnoreCase(action) && !"uploadImage".equalsIgnoreCase(action))
			{
				response.getWriter().write(JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("不支持的格式，请检查")));
			}

			request.setCharacterEncoding("utf-8");
			response.setHeader("Content-Type", "text/html");

			if ("uploadImage".equalsIgnoreCase(action))
			{
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				// 获得文件：
				MultipartFile imgFile = multipartRequest.getFile("upfile"); //UEditor传到后台的是这个upfile，其他的文件上
				String fileName = imgFile.getOriginalFilename();
				String prefix = DateUtil.formatNowTime(DateUtil.FMT_DATE_YYYYMMDDHHMMSS);
				String headName = fileName.substring(0, fileName.indexOf("."));
				String fileType = fileName.substring(fileName.indexOf(".") + 1);
				String realdFileName = headName + "_" + prefix + "." + fileType;
				String imgPath = "uEditor" + File.separator + fileType + File.separator + realdFileName;
				String filePath = ImgConstant.getImgFilePath(imgPath);
				log.info("文件路径:" + filePath + ",文件名：fileName=" + fileName);
				File targetFile = new File(filePath);
				if (!targetFile.exists())
				{
					targetFile.mkdirs();
				}
				imgFile.transferTo(targetFile);
				String httpFileUrl = ImgConstant.getHttpImgUrl("/img/" + "uEditor" + "/" + fileType + "/" + headName
						+ "_" + prefix);
				ReturnUploadImage result = new ReturnUploadImage();
				result.setState("SUCCESS");
				result.setOriginal(realdFileName);
				result.setTitle(realdFileName);
				result.setUrl(httpFileUrl);
				result.setSize(imgFile.getSize());
				result.setType("." + fileType);
				response.getWriter().write(JsonUtil.serialize(result));
			}
			else
			{

				//			String rootPath = PropertyUtils.getProperty("img.root.dir", "/home/qinggu/img/");
				String rootPath = request.getSession().getServletContext().getRealPath("/");
				//				log.info("ueditorUpload rootPath:" + rootPath);
				response.getWriter().write(new ActionEnter(request, rootPath).exec());
			}
		}
		catch (Exception e)
		{
			log.fatal("上传文件异常，msg:" + e.getMessage(), e);
		}
	}

	//	@RequestMapping("/img/uploadUEDitorImage.do")
	//	@ResponseBody
	//	public String uploadUEDitorImage(HttpServletRequest request, HttpServletResponse response, String action)
	//			throws Exception
	//	{
	//		log.info("get uploadUEDitorImage request,action:" + action);
	//		if (StringUtils.isBlank(action))
	//		{
	//			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("上传文件失败，参数缺失"));
	//		}
	//
	//		try
	//		{
	//			if ("config".equalsIgnoreCase(action))
	//			{
	//				Map<String, Object> resultMap = Maps.newHashMap();
	//				resultMap.put("imageActionName", "uploadimage");
	//				resultMap.put("imageFieldName", "upfile");
	//				resultMap.put("imageMaxSize", "2048000");
	//				return JsonUtil.serialize(resultMap);
	//			}
	//			if ("uploadImage".equalsIgnoreCase(action))
	//			{
	//				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	//				// 获得文件：
	//				MultipartFile imgFile = multipartRequest.getFile("upfile"); //UEditor传到后台的是这个upfile，其他的文件上
	//				String fileName = imgFile.getOriginalFilename();
	//				String prefix = DateUtil.formatNowTime(DateUtil.FMT_DATE_YYYYMMDDHHMMSS);
	//				String headName = fileName.substring(0, fileName.indexOf("."));
	//				String fileType = fileName.substring(fileName.indexOf(".") + 1);
	//				String realdFileName = headName + "_" + prefix + "." + fileType;
	//				String imgPath = "uEditor" + File.separator + fileType + File.separator + realdFileName;
	//				String filePath = ImgConstant.getImgFilePath(imgPath);
	//				log.info("文件路径:" + filePath + ",文件名：fileName=" + fileName);
	//				File targetFile = new File(filePath);
	//				if (!targetFile.exists())
	//				{
	//					targetFile.mkdirs();
	//				}
	//				imgFile.transferTo(targetFile);
	//				String httpFileUrl = ImgConstant.getHttpImgUrl("/img/" + "uEditor" + "/" + fileType + "/" + headName
	//						+ "_" + prefix);
	//				ReturnUploadImage result = new ReturnUploadImage();
	//				result.setState("SUCCESS");
	//				result.setOriginal(realdFileName);
	//				result.setTitle(realdFileName);
	//				result.setUrl(httpFileUrl);
	//			}
	//			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("上传文件失败，请检查"));
	//		}
	//		catch (Exception e)
	//		{
	//			log.fatal("上传文件异常，msg:" + e.getMessage(), e);
	//			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("上传文件失败，系统繁忙"));
	//		}
	//	}

}
