package br.com.maddytec.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.maddytec.domain.Request;
import br.com.maddytec.domain.enums.RequestState;
import br.com.maddytec.exception.NotFoundException;
import br.com.maddytec.model.PageModel;
import br.com.maddytec.model.PageRequestModel;
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
		return result.orElseThrow(() -> new NotFoundException("Not exist request with id: " + id));
	}

	public List<Request> findAll() {
		List<Request> requests = requestRepository.findAll();
		return requests;
	}

	public List<Request> findAllByOwnerId(Long id) {
		List<Request> requests = requestRepository.findAllByOwnerId(id);
		return requests;
	}

	public PageModel<Request> findAllByOwnerIdOnLazyModel(Long ownerId, PageRequestModel pageRequestModel) {
		Pageable pageable = PageRequest.of(pageRequestModel.getPage(), pageRequestModel.getSize());
		Page<Request> pageRequest = requestRepository.findAllByOwnerId(ownerId, pageable);

		PageModel<Request> pageModel = new PageModel<>(
				(int) pageRequest.getTotalElements(),
				pageRequest.getSize(),
				pageRequest.getTotalPages(),
				pageRequest.getContent());
		return pageModel;
	}
	
	public PageModel<Request> findAllOnLazyModel(PageRequestModel pageRequestModel) {
		Pageable pageable = PageRequest.of(pageRequestModel.getPage(), pageRequestModel.getSize());
		Page<Request> pageRequest = requestRepository.findAll(pageable);

		PageModel<Request> pageModel = new PageModel<>(
				(int) pageRequest.getTotalElements(),
				pageRequest.getSize(),
				pageRequest.getTotalPages(),
				pageRequest.getContent());
		return pageModel;
	}

}
