package com.RL.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	public ResponseEntity<String> createPublisher(@Valid @RequestBody Publisher publisher) {
		publisherService.createPublisher(publisher);
		return ResponseEntity.ok("The publisher is created");
	}

	@GetMapping
	public ResponseEntity<List<Publisher>> getAll() {
		List<Publisher> publisher = publisherService.getAll();
		return ResponseEntity.ok(publisher);
	}

//	@GetMapping("/{id}")
//
//	public ResponseEntity<Publisher> getPublisher(@PathVariable("id") Long id) {
//		Publisher publisher = publisherService.getPublisher(id);
//		return ResponseEntity.ok(publisher);
//	}
//
//	@DeleteMapping("/{id}")
//	public ResponseEntity<Map<String, String>> deletePublisher(@PathVariable Long id) {
//		publisherService.deletePublisher(id);
//		Map<String, String> map = new HashMap<>();
//		map.put("message", "Publisher successfully deleted");
//		map.put("status", "true");
//		return new ResponseEntity<>(map, HttpStatus.OK);
//
//	}
//@PutMapping("/{id}")
//	public ResponseEntity<Map<String, String>> updatePublisher(@PathVariable Long id, @Valid @RequestBody Publisher publisher) {
//		PublisherService.updatePublisher(id, publisher);
//		Map<String, String> map=new HashMap<>();
//		map.put("message", "Publisher successfully update");
//		map.put("status", "true");
//		return new ResponseEntity<>(map,HttpStatus.OK);
// }

}