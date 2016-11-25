package com.youxue.pc.index.dto;

import java.util.List;

import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.vo.CampsVo;
import com.youxue.core.vo.CategoryVo;

public class IndexCampsDetailsDto extends BaseResponseDto
{

	private static final long serialVersionUID = 1L;
	private List<CampsVo> hotCampsList;
	private List<CampsVo> priceCampsList;
	private List<CategoryVo> subjectList;

	public List<CampsVo> getHotCampsList()
	{
		return hotCampsList;
	}

	public void setHotCampsList(List<CampsVo> hotCampsList)
	{
		this.hotCampsList = hotCampsList;
	}

	public List<CategoryVo> getSubjectList()
	{
		return subjectList;
	}

	public void setSubjectList(List<CategoryVo> subjectList)
	{
		this.subjectList = subjectList;
	}

	public List<CampsVo> getPriceCampsList()
	{
		return priceCampsList;
	}

	public void setPriceCampsList(List<CampsVo> priceCampsList)
	{
		this.priceCampsList = priceCampsList;
	}
}
