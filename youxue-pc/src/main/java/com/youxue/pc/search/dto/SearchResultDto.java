package com.youxue.pc.search.dto;

import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.vo.CampsVo;
import com.youxue.core.vo.Page;

public class SearchResultDto extends BaseResponseDto
{
	private static final long serialVersionUID = 1L;
	private Page<CampsVo> campsList;
	public Page<CampsVo> getCampsList()
	{
		return campsList;
	}
	public void setCampsList(Page<CampsVo> campsList)
	{
		this.campsList = campsList;
	}

}
