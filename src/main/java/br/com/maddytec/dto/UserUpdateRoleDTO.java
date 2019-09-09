package br.com.maddytec.dto;

import javax.validation.constraints.NotNull;

import br.com.maddytec.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateRoleDTO {

	@NotNull(message = "Perfil must be fill")
	private Role role;
}
