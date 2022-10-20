package com.RL.dto.mapper;
import java.util.List;
import com.RL.domain.User;
import com.RL.dto.UserDTO;
import com.RL.dto.request.CreateUserRequest;
import com.RL.dto.request.UpdateRequest;
import com.RL.dto.response.RLResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserDTO userToUserDTO(User user);
	RLResponse userToRLResponse(User user);
	User createUserRequestToUser(CreateUserRequest createUserRequest);
	User updateRequestToUser(UpdateRequest updateRequest);
	List<UserDTO> map(List<User> user);

}
