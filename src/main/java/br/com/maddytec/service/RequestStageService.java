package br.com.maddytec.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.maddytec.domain.Request;
import br.com.maddytec.domain.RequestStage;
import br.com.maddytec.domain.enums.RequestState;
import br.com.maddytec.repository.RequestRepository;
import br.com.maddytec.repository.RequestStageRepository;

@Service
public class RequestStageService {

	@Autowired
	private RequestStageRepository requestStageRepository;

	@Autowired
	RequestRepository requestRepository;

	public RequestStage save(RequestStage requestStage) {
		requestStage.setRealizationDate(new Date());
		
		RequestStage createdStage = requestStageRepository.save(requestStage);
		
		Long requestId = createdStage.getRequest().getId();
		
		RequestState state = requestStage.getState(); 
		
		requestRepository.updateStatus(requestId, state);
		
		return createdStage;
		
	}

	public RequestStage findById(Long id) {
		Optional<RequestStage> result = requestStageRepository.findById(id);
		return result.get();
	}
	
	public List<RequestStage> findAllByRequestId(Long requestId){
		List<RequestStage> stages = requestStageRepository.findAllByRequestId(requestId);
		return stages;
	}

}
