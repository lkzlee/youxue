package com.youxue.pc.shopCart.dto;

import java.io.Serializable;
import java.util.List;

import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.vo.CampsVo;

public class AddShopCartDetailDto extends BaseResponseDto implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int count;//该营地在收藏车中的数量
	private CampsVo camps;
	private List<CampsVo> hotCamps;

	public CampsVo getCamps()
	{
		return camps;
	}

	public void setCamps(CampsVo camps)
	{
		this.camps = camps;
	}

	public List<CampsVo> getHotCamps()
	{
		return hotCamps;
	}

	public void setHotCamps(List<CampsVo> hotCamps)
	{
		this.hotCamps = hotCamps;
	}

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}

}
