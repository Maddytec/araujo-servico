package br.com.maddytec.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.maddytec.domain.enums.RequestState;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity(name = "request_stage")
public class RequestStage implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "realization_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date realizationDate;

	@Column(columnDefinition = "text")
	private String description;

	@Column(name = "request_date", length = 12, nullable = false)
	@Enumerated(EnumType.STRING)
	private RequestState state;

	@ManyToOne
	@JoinColumn(name = "request_id", nullable = false)
	private Request request;

	@ManyToOne
	@JoinColumn(name = "owner_id", nullable = false)
	private User owner;
}
