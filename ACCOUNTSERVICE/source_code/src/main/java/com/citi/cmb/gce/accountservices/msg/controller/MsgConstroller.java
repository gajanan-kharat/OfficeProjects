package com.citi.cmb.gce.accountservices.msg.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.citi.cmb.gce.accountservices.TestMessageSender;

@RestController
public class MsgConstroller {

	@Autowired
	ApplicationContext applicationContext;
	
	@Autowired
	TestMessageSender messageSender;
	
	//Here we can use @GetMapping,@PostMapping
	@RequestMapping(path= {"/accountservice/{operation}"}, method= {RequestMethod.GET,RequestMethod.POST})
	public String testThread(@PathVariable("operation") String operation) throws URISyntaxException, IOException {
		String response = messageSender.sendmsgResponse(operation);
		return response;
	}
}
