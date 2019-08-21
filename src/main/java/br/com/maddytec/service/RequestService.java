package br.com.maddytec.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.maddytec.domain.Request;
import br.com.maddytec.domain.enums.RequestState;
import br.com.maddytec.repository.RequestRepository;

@Service
public class RequestService {

	@Autowired
	private RequestRepository requestRepository;

	public Request save(Request request) {
		request.setState(RequestState.OPEN);
		request.setCriationDate(new Date());
		return requestRepository.save(request);
	}

	public Request update(Request request) {
		return requestRepository.save(request);
	}

	public Request findById(Long id) {
		Optional<Request> result = requestRepository.findById(id);
		return result.get();
	}

	public List<Request> findAll() {
		List<Request> requests = requestRepository.findAll();
		return requests;
	}

	public List<Request> findAllByOwnerId(Long id) {
		List<Request> requests = requestRepository.findAllByOwnerId(id);
		return requests;
	}
}
