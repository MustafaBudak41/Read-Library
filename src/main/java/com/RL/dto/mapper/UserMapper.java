package com.RL.dto.mapper;

import java.util.List;

import com.RL.domain.User;
import com.RL.dto.UserDTO;
import com.RL.dto.request.RegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UserMapper {

	UserDTO userToUserDTO(User user);

	@Mapping(target="roles",ignore=true)
	User userDTOToUser(UserDTO userDTO);

	User userDTOToUser(RegisterRequest  registerRequest);

	List<UserDTO> map(List<User> user);

}
