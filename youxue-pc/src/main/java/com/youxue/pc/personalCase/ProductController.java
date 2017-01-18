package com.youxue.pc.personalCase;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youxue.core.common.BaseController;
import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.dao.CommonDao;
import com.youxue.core.dao.SurroundProductDao;
import com.youxue.core.util.JsonUtil;
import com.youxue.core.vo.SurroundProductVo;

@Controller
public class ProductController extends BaseController
{
	private static final Log log = LogFactory.getLog(ProductController.class);
	@Resource
	private SurroundProductDao surroundProductDao;
	@Resource
	private CommonDao commonDao;

	@RequestMapping("/productDetail.do")
	@ResponseBody
	public String productDetail(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception
	{
		try
		{
			SurroundProductVo product1 = surroundProductDao.selectProductByType(1);
			SurroundProductVo product2 = surroundProductDao.selectProductByType(2);
			ProductDetailsDto dto = new ProductDetailsDto();
			dto.setResult(100);
			dto.setDesc("success");
			dto.getProductList().add(product1);
			dto.getProductList().add(product2);
			return JsonUtil.serialize(dto);
		}
		catch (Exception e)
		{
			log.fatal("error in productDetail.do，msg:", e);
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("请求异常"));
		}
	}
}
