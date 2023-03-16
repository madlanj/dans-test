package com.adlanjazuli.service;


import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.adlanjazuli.dto.Job;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobService {
	@Autowired
    RestTemplate restTemplate;
	
	public ResponseEntity<String> getJobList()  {
	  String url = "http://dev3.dansmultipro.co.id/api/recruitment/positions.json";
	  ResponseEntity<String> response = restTemplate.getForEntity(url , String.class);
	  return response;
		
	}	
	
	public ResponseEntity<Job> getJob(String id)  {
	  HttpHeaders headers = new HttpHeaders();
	  headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	  
	  String url = "http://dev3.dansmultipro.co.id/api/recruitment/positions/" + id;
	  Job job  = restTemplate.getForObject(url , Job.class);
	  
	  return ResponseEntity.ok(job);
		
	}	
	
	
	public void generateCsv(HttpServletResponse response)throws Exception {
		
		String urlRest = "http://dev3.dansmultipro.co.id/api/recruitment/positions.json";
		ResponseEntity<List<Job>> responseRest = restTemplate.exchange(
				urlRest,
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<Job>>(){});
		List<Job> jobs = responseRest.getBody();
		
        String filename = "jobs-data.csv";
 
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");

        StatefulBeanToCsv<Job> writer = new StatefulBeanToCsvBuilder<Job>(response.getWriter())
                .withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER).withSeparator(CSVWriter.DEFAULT_SEPARATOR).withOrderedResults(false)
                .build();

        writer.write(jobs);
	}
	
	
	
}
