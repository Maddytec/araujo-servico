package br.com.maddytec.dto;

import javax.validation.constraints.NotBlank;

import br.com.maddytec.domain.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRoleDTO {

	@NotBlank(message = "Perfil must be fill")
	private Role role;
}
