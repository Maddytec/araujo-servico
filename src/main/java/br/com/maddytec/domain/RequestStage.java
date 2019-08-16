package br.com.maddytec.domain;

import java.util.Date;

import br.com.maddytec.domain.enums.RequestState;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestStage {
	private Long id;
	private Date realizaionDate;
	private String description;
	private RequestState state;
	private Request request;
	private User user;
}
