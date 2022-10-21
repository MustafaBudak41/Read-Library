package com.RL.dto.mapper;

import com.RL.domain.Book;
import com.RL.dto.BookDTO;
import com.RL.dto.response.BookResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "authorId", ignore = true)
    @Mapping(target = "publisherId", ignore = true)
    @Mapping(target = "categoryId", ignore = true)
    @Mapping(target = "image", ignore = true)
    Book bookDTOToBook(BookDTO bookDTO);

    List<BookResponse> map(List<Book> book);

    BookResponse bookToBookResponse(Book book);

}
