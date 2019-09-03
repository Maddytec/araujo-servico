package br.com.maddytec.dto;

import br.com.maddytec.domain.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRoleDTO {

	private Role role;
}
