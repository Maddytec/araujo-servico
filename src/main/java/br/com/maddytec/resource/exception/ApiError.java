package br.com.maddytec.resource.exception;

import java.io.Serializable;
import java.util.Date;

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
public class ApiError implements Serializable {

	private static final long serialVersionUID = 1L;
	private int code;
	private String message;
	private Date date;
}
