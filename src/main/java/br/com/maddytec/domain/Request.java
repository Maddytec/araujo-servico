package br.com.maddytec.domain;

import java.io.Serializable;
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

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.maddytec.domain.enums.RequestState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "request")
public class Request implements Serializable {

	private static final long serialVersionUID = -1967396941273349952L;

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
	@JoinColumn(name = "owner_id", nullable = false)
	private User owner;

	@Getter(onMethod = @__({@JsonIgnore}))
	@OneToMany(mappedBy = "request")
	private List<RequestStage> stages = new ArrayList<>();

}
