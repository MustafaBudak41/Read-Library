package com.RL.service;



import java.util.List;
import java.util.function.Function;

import com.RL.domain.Author;
import com.RL.dto.AuthorDTO;
import com.RL.dto.PublisherDTO;
import com.RL.dto.mapper.AuthorMapper;
import com.RL.dto.mapper.PublisherMapper;

import com.RL.exception.BadRequestException;
import com.RL.exception.ResourceNotFoundException;
import com.RL.exception.message.ErrorMessage;
import com.RL.repository.PublisherRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.RL.domain.Publisher;

import lombok.AllArgsConstructor;
    @Service
    @AllArgsConstructor
    public class PublisherService {

        private PublisherRepository repository;
        private PublisherMapper publisherMapper;



        //http://localhost:8080/publisher
        public Publisher createPublisher(PublisherDTO publisherDTO) {

            Publisher publisher1= publisherMapper.publisherDTOToPublisher(publisherDTO);
            repository.save(publisher1);
            return  publisher1;
        }

        public Page<PublisherDTO> getPublisherPage(Pageable pageable) {
            Page<Publisher> publishers = repository.findAll(pageable);
            Page<PublisherDTO> dtoPage = publishers.map(new Function<Publisher, PublisherDTO>() {
                @Override
                public PublisherDTO apply(Publisher publisher) {
                    return publisherMapper.publisherToPublisherDTO(publisher);
                }
            });

            return dtoPage;
        }


        public PublisherDTO findById(Long id) {
            Publisher publisher = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Publisher not found"));
            return publisherMapper.publisherToPublisherDTO(publisher);
        }

        public Publisher updatePublisher(Long id, Publisher publisher) {
            Publisher foundPublisher = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Publisher not found"));
            if(foundPublisher.getBuiltIn()) {
                throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
            }
            foundPublisher.setId(id);
            foundPublisher.setName(publisher.getName());
            repository.save(foundPublisher);
            return foundPublisher;
        }


        public Publisher deleteById(Long id) {
            Publisher publisher = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Publisher not found"));
            if(publisher.getBuiltIn()) {
                throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
            }
            if(!publisher.getBooks().isEmpty()) {
                throw  new ResourceNotFoundException("You cannot delete an publisher who has a book");
            }
            repository.deleteById(id);
            return publisher;
        }




}
