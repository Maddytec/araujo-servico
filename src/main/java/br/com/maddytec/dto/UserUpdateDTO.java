package br.com.maddytec.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.maddytec.domain.Request;
import br.com.maddytec.domain.RequestStage;
import br.com.maddytec.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDTO {

	@NotBlank(message = "Name invalid")
	@Size(min = 3, max = 99, message = "Name must be between 3 and 99 character")
	private String name;

	@NotBlank(message = "Email must be fill")
	@Email(message = "Email invalid")
	private String email;

	@NotBlank(message = "Password must be fill")
	@Size(min = 6, max = 10, message = "Password must be between 6 and 10 character")
	private String password;

	private List<Request> requests = new ArrayList<>();

	private List<RequestStage> stages = new ArrayList<>();
	
	public User converterToUser(UserUpdateDTO userUpdateDTO) {
		return User.builder()
				.name(userUpdateDTO.getName())
				.email(userUpdateDTO.getEmail())
				.password(userUpdateDTO.getPassword())
				.requests(userUpdateDTO.getRequests())
				.stages(userUpdateDTO.getStages())
				.build();
	}
}
