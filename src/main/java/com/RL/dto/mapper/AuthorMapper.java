package com.RL.dto.mapper;

import com.RL.domain.Author;
import com.RL.domain.User;
import com.RL.dto.AuthorDTO;
import com.RL.dto.UserDTO;
import com.RL.dto.request.RegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorDTO authorToAuthorDTO(Author author);

    @Mapping(target="books",ignore=true)
    Author authorDTOToAuthor(AuthorDTO authorDTO);

    List<AuthorDTO> map(List<Author> author);


}
