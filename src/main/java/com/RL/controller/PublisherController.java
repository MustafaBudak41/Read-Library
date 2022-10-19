package com.RL.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import com.RL.dto.response.RLResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.RL.domain.Publisher;
import com.RL.service.PublisherService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping
@AllArgsConstructor
public class PublisherController {

	private PublisherService publisherService;
	
	@PostMapping("/publisher")
	public ResponseEntity<Map<String,String>> createPublisher(@Valid @RequestBody Publisher publisher){
		Publisher newPublisher= publisherService.createPublisher(publisher);


		Map<String,String> map=new HashMap<>();
		map.put("id : ", newPublisher.getId().toString());
		map.put("name : ",newPublisher.getName());

		return new ResponseEntity<>(map, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<Publisher>> getAll(){
		List<Publisher> publisher = publisherService.getAll();
		return ResponseEntity.ok(publisher);
	}
	

}
