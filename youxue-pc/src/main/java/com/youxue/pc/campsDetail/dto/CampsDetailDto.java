package com.youxue.pc.campsDetail.dto;

import java.io.Serializable;
import java.util.List;

import com.youxue.core.vo.CampsTraceVo;
import com.youxue.core.vo.CampsVo;

public class CampsDetailDto extends CampsVo implements Serializable
{
	private static final long serialVersionUID = 1L;
	private List<CampsTraceVo> traces;

	public List<CampsTraceVo> getTraces()
	{
		return traces;
	}

	public void setTraces(List<CampsTraceVo> traces)
	{
		this.traces = traces;
	}

}
