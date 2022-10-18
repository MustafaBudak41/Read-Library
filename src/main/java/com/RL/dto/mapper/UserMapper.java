package com.RL.dto.mapper;

import java.util.List;

import com.RL.domain.User;
import com.RL.dto.UserDTO;
import com.RL.dto.request.CreateUserRequest;
import com.RL.dto.request.RegisterRequest;
import com.RL.dto.request.SignInRequest;
import com.RL.dto.response.PageResponse;
import com.RL.dto.response.RLResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UserMapper {

	UserDTO userToUserDTO(User user);
	PageResponse userToPageResponse(User user);
	RLResponse userToRLResponse(User user);

	@Mapping(target="roles",ignore=true)
	User userDTOToUser(UserDTO userDTO);

	User createUserRequestToUser(CreateUserRequest createUserRequest);
	User registerRequestToUser(RegisterRequest registerRequest);
	User signInRequestToUser(SignInRequest signInRequest);


	List<UserDTO> map(List<User> user);

}
