package com.RL.dto.mapper;


import com.RL.domain.Publisher;

import com.RL.dto.PublisherDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PublisherMapper {
    PublisherDTO publisherToPublisherDTO(Publisher publisher);

    @Mapping(target="books",ignore=true)
    Publisher publisherDTOToPublisher(PublisherDTO publisherDTO);

    List<PublisherDTO> map(List<Publisher> publisher);
}
