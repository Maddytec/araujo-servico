package br.com.maddytec.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.maddytec.domain.Request;
import br.com.maddytec.domain.RequestStage;
import br.com.maddytec.domain.User;
import br.com.maddytec.domain.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSaveDTO {

	@NotBlank(message = "Name invalid")
	@Size(min = 3, max = 99, message = "Name must be between 3 and 99 character")
	private String name;

	@NotBlank(message = "Email must be fill")
	@Email(message = "Email invalid")
	private String email;

	@NotBlank(message = "Password must be fill")
	@Size(min = 6, max = 10, message = "Password must be between 6 and 10 character")
	private String password;

	@NotNull(message = "Required profile")
	private Role role;

	private List<Request> requests = new ArrayList<>();

	private List<RequestStage> stages = new ArrayList<>();

	public User converterToUser(UserSaveDTO userSaveDTO) {
		return User.builder()
				.name(userSaveDTO.getName())
				.email(userSaveDTO.getEmail())
				.password(userSaveDTO.getPassword())
				.requests(userSaveDTO.getRequests())
				.role(userSaveDTO.getRole())
				.stages(userSaveDTO.getStages())
				.build();
	}
}
