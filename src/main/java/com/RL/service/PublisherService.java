package com.RL.service;



import java.util.List;
import java.util.Optional;

import com.RL.exception.message.ErrorMessage;
import com.RL.repository.PublisherRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.RL.domain.Publisher;
import com.RL.exception.ResourceNotFoundException;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@Service
    public class PublisherService {


        @Autowired
        private static PublisherRepository publisherRepository; // Bu class ta kullanmak istediğimiz için Repository i inject ettik -- Constractor inject type ile


        //http://localhost:8080/publisher
        public void createPublisher(Publisher publisher) {
            publisherRepository.save(publisher);

        }

        //http://localhost:8080/publisher
        public List<Publisher> getAll() {
            return publisherRepository.findAll();
        }

        //http://localhost:8080/publisher/97

        public static Publisher getPublisher(Long id)throws ResourceNotFoundException{
            return publisherRepository.findById(id).orElseThrow(() ->
                  new ResourceNotFoundException(String.format(ErrorMessage.PUBLISHER_NOT_FOUND_MESSAGE, id)));

            //findById ile gelen deger null ise exception dondurur degilse gelen degeri kullanır

        }

        //http://localhost:8080/publisher/97
        public void deletePublisher(Long id) throws ResourceNotFoundException {
            Publisher publisher = getPublisher(id);
            publisherRepository.delete(publisher);

        }
//       public static void updatePublisher(Long id, Publisher newPublisher){
//           Publisher foundPublisher = getPublisher(id);

//           foundPublisher.setName(newPublisher.getName());

//           publisherRepository.save(foundPublisher);
//       }
    }


