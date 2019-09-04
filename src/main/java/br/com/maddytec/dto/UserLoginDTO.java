package br.com.maddytec.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserLoginDTO {

	@NotBlank(message = "Email must be fill")
	@Email(message = "Email invalid")
	private String email;
	
	@NotBlank(message = "Password must be fill")
	@Size(min = 6, max = 10, message = "Password must be between 6 and 10 character")
	private String password;
}
