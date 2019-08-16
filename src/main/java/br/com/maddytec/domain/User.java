package br.com.maddytec.domain;

import java.util.ArrayList;
import java.util.List;

import br.com.maddytec.domain.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
	private Long id;
	private String name;
	private String email;
	private String password;
	private Role role;
	private List<Request> requests = new ArrayList<>();
	private List<RequestStage> stages = new ArrayList<>();
}
