package br.com.maddytec.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.maddytec.domain.enums.RequestState;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity(name = "request")
public class Request {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 75, nullable = false)
	private String subject;

	@Column(columnDefinition = "text")
	private String description;

	@Column(name = "criation_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date criationDate;

	@Column(length = 12, nullable = false)
	@Enumerated(EnumType.STRING)
	private RequestState state;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@OneToMany(mappedBy = "request")
	private List<RequestStage> stages = new ArrayList<>();

}
