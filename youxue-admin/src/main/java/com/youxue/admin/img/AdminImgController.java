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
			String httpFileUrl = ImgConstant.getHttpImgUrl("/img/" + businessType + "/" + fileType + "/" + headName + "_"
					+ prefix);
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
}
