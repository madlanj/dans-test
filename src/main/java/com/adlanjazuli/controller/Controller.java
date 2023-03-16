package com.adlanjazuli.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.adlanjazuli.dto.Job;
import com.adlanjazuli.dto.UserRequest;
import com.adlanjazuli.service.AuthService;
import com.adlanjazuli.service.JobService;

import jakarta.servlet.http.HttpServletResponse;



@RestController
@RequestMapping("/dans")
public class Controller {
	
	@Autowired
	AuthService authService;
	
	@Autowired
    RestTemplate restTemplate;
	
	@Autowired
	JobService jobService;
	
	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody UserRequest loginRequest) {
		return authService.login(loginRequest);
		
	}
	
	@RequestMapping(value = "/joblist")
	public String getProductList()  {
	  HttpHeaders headers = new HttpHeaders();
	  headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	  HttpEntity <String> entity = new HttpEntity<String>(headers);
	  
	  return restTemplate.exchange("http://dev3.dansmultipro.co.id/api/recruitment/positions.json", HttpMethod.GET, entity, String.class).getBody();
		
	}			
	
	@RequestMapping(value = "/job")
	public ResponseEntity<String> getJobList()  {
	  return jobService.getJobList();
	}
	
	@RequestMapping(value = "/job/{id}")
	public ResponseEntity<Job> getJob(@PathVariable String id)  {
	  return jobService.getJob(id);
	}
	
	@RequestMapping(value = "/job/download")
	public void downloadFileCsv(HttpServletResponse response) throws Exception  {
	  jobService.generateCsv(response);
	}
	
				
}
