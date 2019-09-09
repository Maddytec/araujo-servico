package br.com.maddytec.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import br.com.maddytec.domain.Request;
import br.com.maddytec.domain.RequestStage;
import br.com.maddytec.domain.User;
import br.com.maddytec.domain.enums.RequestState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestUpdateDTO {

	@NotNull(message = "Subject must be filled out")
	private String subject;

	private String description;

	@NotNull(message = "Owner must be filled out")
	private User owner;
	
	private List<RequestStage> stages = new ArrayList<>();

	@NotNull(message = "State must be filled out")
	private RequestState state;
	
	public Request converterToRequest(RequestUpdateDTO requestUpdateDTO) {
		return Request.builder()
				.subject(requestUpdateDTO.getSubject())
				.description(requestUpdateDTO.getDescription())
				.owner(requestUpdateDTO.getOwner())
				.state(requestUpdateDTO.getState())
				.build();
	}
}
