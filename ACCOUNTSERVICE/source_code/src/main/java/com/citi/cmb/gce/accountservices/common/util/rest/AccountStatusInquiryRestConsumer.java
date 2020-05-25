package com.citi.cmb.gce.accountservices.common.util.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.citi.cmb.gce.accountservices.common.repository.AccountServicesConfigRepository;
import com.citi.cmb.gce.accountservices.common.repository.SequenceGenerator;
import com.citi.cmb.gce.accountservices.common.util.AccountServiceConfigCache;
import com.citi.cmb.gce.accountservices.common.util.BalanceInquiryRequestWrapper;
import com.citi.cmb.gce.accountservices.common.util.RetryManager;
import com.citi.cmb.gce.accountservices.common.util.rest.vo.BalanceInquiryResponseVO;
import com.citi.cmb.gce.accountservices.constant.ASConstant;
import com.citi.cmb.gce.accountservices.msg.vo.AccountIdVO;

public class AccountStatusInquiryRestConsumer {

	private static final String CLASS_NAME= AccountStatusInquiryRestConsumer.class.getName();
	
	@Autowired
	Logger logger;
	
	@Autowired
	AccountServiceConfigCache configCache;
	
	@Autowired
	SequenceGenerator sequenceGenerator;
	
	@Autowired
	AccountServicesConfigRepository configRepository;
	
	@Autowired
	ApplicationContext applicationContext;
	
	public Map<String, BalanceInquiryResponseVO> getAccountStatusDetails(ArrayList<AccountIdVO> listOfAccountInfoVO) throws InterruptedException{

		Map<String, BalanceInquiryResponseVO> mapOfStatusResponse = new HashMap<>();
		BalanceInquiryRequestWrapper wrapperObject = getWrapperObject(listOfAccountInfoVO);
		cosumeRest(wrapperObject);
		
		return mapOfStatusResponse;
	}

	private Map<String, BalanceInquiryResponseVO> cosumeRest(BalanceInquiryRequestWrapper wrapperObject) throws InterruptedException {
		String methodName = "cosumeRest()";
		
		RestTemplate restTemplate = new RestTemplate();
		
		ParameterizedTypeReference<Map<String,BalanceInquiryResponseVO>> typeReference = 
				new ParameterizedTypeReference<Map<String,BalanceInquiryResponseVO>>() {};
				
		String serviceUrl = configCache.getAccountServiceConfig(ASConstant.BALANCE_INQUIRY_HTTP_URL);
		//String serviceUrl="http://10.108.54.30:8080/accountService/accountStatus"
		
		Integer retryInterval = Integer.valueOf(configRepository.findByConfigName(ASConstant.BAL_INQ_REST_INTERVAL).getConfigValue());
		
		Integer retryFrequency = Integer.valueOf(configRepository.findByConfigName(ASConstant.BAL_INQ_REST_RETRY_FREQUENCY).getConfigValue());
		
		RetryManager retryManager = applicationContext.getBean(RetryManager.class,retryFrequency, new Long(retryInterval), CLASS_NAME.concat("_").concat(methodName));
		
		HttpEntity<BalanceInquiryRequestWrapper> requestEntity = new HttpEntity<BalanceInquiryRequestWrapper>(wrapperObject, setHeaders());
		
		while (retryManager.shouldRetry()) {
			try {
				ResponseEntity<Map<String, BalanceInquiryResponseVO>> responseEntity = 
						restTemplate.exchange(serviceUrl, HttpMethod.POST, requestEntity, typeReference);
				retryManager.setNumberOfTriesLeft(0);
				return responseEntity.getBody();
			} catch (RestClientException e) {
				retryManager.setNumberOfTriesLeft(retryManager.getNumberOfTriesLeft() - 1);

				if (retryManager.shouldRetry()) {
					Thread.sleep(retryInterval * 1000);
				} else {
					System.out.println("Failed connecting account service!!");
				}
			}
		}

		return null;
	}

	private HttpHeaders setHeaders() {
		String authkey = AccountServiceConfigCache.accountServiceConfigMap.get(ASConstant.ACCOUNT_SERVICE_AUTH_KEY);
		Map<String, String> mapOfHeader = new HashMap<>();
		mapOfHeader.put("API_AUTH_KEY", authkey);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setAll(mapOfHeader);

		return httpHeaders;
	}

	private BalanceInquiryRequestWrapper getWrapperObject(ArrayList<AccountIdVO> listOfAccountInfoVO) {
		BalanceInquiryRequestWrapper wrapper = new BalanceInquiryRequestWrapper();
		wrapper.setAccountIdVOList(listOfAccountInfoVO);
		wrapper.setBalanceServiceRefNo(getServiceRefNo());
		wrapper.setSrcSystem("ACCOUNT_SERVICES");
		return wrapper;
	}

	private String getServiceRefNo() {
		BigDecimal refNumber = sequenceGenerator.getStatusServiceRefNumber();
		return "ACC_SERVICES_"+refNumber;
	}
	
}
