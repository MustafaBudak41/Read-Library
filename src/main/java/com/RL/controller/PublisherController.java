package com.RL.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/publisher")
@AllArgsConstructor
public class PublisherController {
	
	@Autowired
	private PublisherService publisherService;
	
	@PostMapping
	public ResponseEntity<String> createPublisher(@Valid @RequestBody Publisher publisher){
		publisherService.createPublisher(publisher);
		return ResponseEntity.ok("The publisher is created");
	}
	
	@GetMapping
	public ResponseEntity<List<Publisher>> getAll(){
		List<Publisher> publisher = publisherService.getAll();
		return ResponseEntity.ok(publisher);
	}
	

}
