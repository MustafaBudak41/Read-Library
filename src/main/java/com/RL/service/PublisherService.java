package com.RL.service;



import java.util.List;

import com.RL.repository.PublisherRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.RL.domain.Publisher;

import lombok.AllArgsConstructor;
    @Service
    @AllArgsConstructor
    public class PublisherService {


        @Autowired
        private PublisherRepository publisherRepository; // Bu class ta kullanmak istediğimiz için Repository i inject ettik -- Constractor inject type ile



        //http://localhost:8080/publisher
        public Publisher createPublisher(Publisher publisher) {

            Publisher publisher1= publisherRepository.save(publisher);

            return  publisher1;
        }

        //http://localhost:8080/publisher
        public List<Publisher> getAll(){
            return publisherRepository.findAll();
        }

   
        
//        public Publisher getPublisher(Long id) {
//            return publisherRepository.findById(id).orElseThrow(()->
//                    new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));
//
//            //findById ile gelen deger null ise exception dondurur degilse gelen degeri kullanır
//
//        }

//        public void deletePublisher(Long id) throws ResourceNotFoundException {
//            Publisher publisher= getPublisher(id);
//            publisherRepository.delete(publisher);
//
//        }

 //   }
}
