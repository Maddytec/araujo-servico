package br.com.maddytec.dto;

import javax.validation.constraints.NotNull;

import br.com.maddytec.domain.Request;
import br.com.maddytec.domain.RequestStage;
import br.com.maddytec.domain.User;
import br.com.maddytec.domain.enums.RequestState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestStageSaveDTO {

	private String description;

	@NotNull(message = "State must be filled out")
	private RequestState state;

	@NotNull(message = "Request must be filled out")
	private Request request;

	@NotNull(message = "Owner must be filled out")
	private User owner;

	public RequestStage converterToRequestStage(RequestStageSaveDTO requestStageSaveDTO) {
		return RequestStage.builder().description(description).state(state).request(request).owner(owner).build();
	}
}
