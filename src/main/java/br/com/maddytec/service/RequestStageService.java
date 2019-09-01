package br.com.maddytec.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.maddytec.domain.RequestStage;
import br.com.maddytec.domain.enums.RequestState;
import br.com.maddytec.exception.NotFoundException;
import br.com.maddytec.model.PageModel;
import br.com.maddytec.model.PageRequestModel;
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
		return result.orElseThrow(() -> new NotFoundException("Not exist request stage with id: " + id));
	}
	
	public List<RequestStage> findAllByRequestId(Long requestId){
		List<RequestStage> stages = requestStageRepository.findAllByRequestId(requestId);
		return stages;
	}
	
	public PageModel<RequestStage> findAllByRequestId(Long requestId, PageRequestModel pageRequestModel){
		
		Pageable pageable = PageRequest.of(pageRequestModel.getPage(), pageRequestModel.getSize()); 
		
		Page<RequestStage> page = requestStageRepository.findAllByRequestId(requestId, pageable);
				
		PageModel<RequestStage> pageModel = new PageModel<>((int) page.getTotalElements(),page.getTotalPages(), page.getTotalPages(), page.getContent());
		return pageModel;
	}

}
