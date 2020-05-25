package com.citi.cmb.gce.accountservices.msg.processor;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.citi.cmb.gce.accountservices.cache.AccountCache;
import com.citi.cmb.gce.accountservices.cache.entity.ActiveAccounts;
import com.citi.cmb.gce.accountservices.msg.entity.AcctMainInfoEntity;
import com.citi.cmb.gce.accountservices.msg.mapper.AcctMainInfoEntityIdMapper;
import com.citi.cmb.gce.accountservices.msg.repository.AccountDataRepository;
import com.citi.cmb.gce.accountservices.msg.vo.MessageVO;
import com.citi.cmb.gce.accountservices.postrestriction.audit.service.AuditService;

@Component
public class MessageProcessor {

	@Autowired
	private AuditService auditService;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	AccountDataRepository accountDataRepository;

	@Autowired
	private AcctMainInfoEntityIdMapper acctMainInfoIdMapper;

	public void processMessage(MessageVO messageVO, String xmlMessage) {

		ActiveAccounts activeAccounts = new ActiveAccounts();
		activeAccounts.setAccountNo(messageVO.getCustAccNo());
		activeAccounts.setBranchCd(messageVO.getBranch());

		if (AccountCache.configMap.contains(activeAccounts)) {
			auditService.persistAuditMessage(xmlMessage);

			TypeMap<MessageVO, AcctMainInfoEntity> typeMap = modelMapper.getTypeMap(MessageVO.class,
					AcctMainInfoEntity.class);
			if (typeMap == null) {
				modelMapper.addMappings(acctMainInfoIdMapper);
			}
			AcctMainInfoEntity entity = modelMapper.map(messageVO, AcctMainInfoEntity.class);
			accountDataRepository.saveAndFlush(entity);
		} else {
			System.out.println("Discarding the account simply");
		}
	}

}
