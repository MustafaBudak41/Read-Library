package com.RL.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import com.RL.domain.Author;
import com.RL.dto.AuthorDTO;
import com.RL.dto.PublisherDTO;
import com.RL.dto.response.RLResponse;
import com.RL.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.RL.domain.Publisher;
import com.RL.service.PublisherService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping
@AllArgsConstructor
public class PublisherController {

	private final PublisherService publisherService;

	@PostMapping("/publishers")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Map<String,String>> createPublisher(@Valid @RequestBody PublisherDTO publisherDTO){
		Publisher newPublisher  = publisherService.createPublisher(publisherDTO);
		Map<String,String> map=new HashMap<>();
		map.put("id : ", newPublisher.getId().toString());
		map.put("name : ",newPublisher.getName());
		return new ResponseEntity<>(map,HttpStatus.CREATED);
	}


	//   http://localhost:8080/publishers?size=10&page=0&sort=name&direction=ASC
	@GetMapping("/publishers")
	public ResponseEntity<Page<PublisherDTO>> getAllPublisherByPage(@RequestParam("page") int page,
															@RequestParam("size") int size,
															@RequestParam("sort") String prop,
															@RequestParam("direction") Sort.Direction direction){

		Pageable pageable= PageRequest.of(page, size, Sort.by(direction,prop));
		Page<PublisherDTO> userDTOPage=publisherService.getPublisherPage(pageable);
		return ResponseEntity.ok(userDTOPage);
	}



	@GetMapping("/publishers/{id}")
	public ResponseEntity<Map<String,String>>  findById(@PathVariable("id") Long id){
		PublisherDTO publisherDTO= publisherService.findById(id);
		Map<String,String> map=new HashMap<>();
		map.put("id : ", publisherDTO.getId().toString());
		map.put("name : ",publisherDTO.getName());
		return new ResponseEntity<>(map,HttpStatus.OK);
	}

	@PutMapping("/publishers/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Map<String,String>> updatePublisher(@PathVariable("id") Long id, @Valid @RequestBody Publisher publisher){
		Publisher publisher1 = publisherService.updatePublisher(id,publisher);
		Map<String,String> map=new HashMap<>();
		map.put("id : ", publisher1.getId().toString());
		map.put("name : ",publisher1.getName());
		return new ResponseEntity<>(map,HttpStatus.CREATED);
	}
	@DeleteMapping("/publishers/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Map<String,String>> deleteById(@PathVariable("id") Long id){
		Publisher publisher= publisherService.deleteById(id);
		Map<String,String> map=new HashMap<>();
		map.put("id : ", publisher.getId().toString());
		map.put("name : ",publisher.getName());
		return new ResponseEntity<>(map,HttpStatus.CREATED);
	}

}
