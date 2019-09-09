package br.com.maddytec.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.maddytec.domain.Request;
import br.com.maddytec.domain.RequestStage;
import br.com.maddytec.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestSaveDTO {

	@NotBlank(message = "Subject must be filled out")
	private String subject;

	private String description;

	@NotNull(message = "Owner must be filled out")
	private User owner;
	private List<RequestStage> stages = new ArrayList<>();

	public Request converterToRequest(RequestSaveDTO requestSaveDTO) {
		return Request.builder()
				.subject(requestSaveDTO.getSubject())
				.description(requestSaveDTO.getDescription())
				.owner(requestSaveDTO.getOwner())
				.stages(requestSaveDTO.getStages())
				.build();
	}
}
