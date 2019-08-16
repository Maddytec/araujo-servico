package br.com.maddytec.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Request {

	private Long id;
	private String subject;
	private String description;
	private Date criationDate;

	private RequestStage state;
	private User user;
	private List<RequestStage> stages = new ArrayList<>();

}
