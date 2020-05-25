package com.citi.cmb.gce.accountservices.msg.mapper;

import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import com.citi.cmb.gce.accountservices.msg.entity.AcctMainInfoEntity;
import com.citi.cmb.gce.accountservices.msg.vo.MessageVO;

@Component
public class AcctMainInfoEntityIdMapper extends PropertyMap<MessageVO, AcctMainInfoEntity>{

	@Override
	protected void configure() {
		map().getId().setCustAccNo(source.getCustAccNo());
		map().getId().setBranch(source.getBranch());
	}

}
