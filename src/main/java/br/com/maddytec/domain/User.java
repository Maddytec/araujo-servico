package br.com.maddytec.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.maddytec.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user")
public class User implements Serializable {

	private static final long serialVersionUID = -4709056733071254797L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 75, nullable = false)
	private String name;

	@Column(length = 75, nullable = false, unique = true)
	private String email;

	@Getter(onMethod = @__({@JsonIgnore}))
	@Setter(onMethod = @__({@JsonProperty}))
	@Column(length = 100, nullable = false)
	private String password;

	@Column(length = 20, nullable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	private Role role;

	@Getter(onMethod = @__({@JsonIgnore}))
	@OneToMany(mappedBy = "owner")
	@Builder.Default
	private List<Request> requests = new ArrayList<>();

	@Getter(onMethod = @__({@JsonIgnore}))
	@OneToMany(mappedBy = "owner")
	@Builder.Default
	private List<RequestStage> stages = new ArrayList<>();
}
