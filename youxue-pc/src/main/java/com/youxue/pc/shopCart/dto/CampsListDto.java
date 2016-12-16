package com.youxue.pc.shopCart.dto;

import java.util.List;

import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.vo.CampsVo;

public class CampsListDto extends BaseResponseDto
{

	private static final long serialVersionUID = 1L;
	private List<CampsVo> campsList;
	public List<CampsVo> getCampsList()
	{
		return campsList;
	}
	public void setCampsList(List<CampsVo> campsList)
	{
		this.campsList = campsList;
	}

}
