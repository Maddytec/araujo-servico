package br.com.maddytec.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.maddytec.domain.RequestStage;
import br.com.maddytec.model.PageModel;
import br.com.maddytec.model.PageRequestModel;
import br.com.maddytec.service.RequestStageService;

@RestController
@RequestMapping(value = "request-stages")
public class RequestStageResource {

	@Autowired
	RequestStageService requestStageService;

	@PostMapping
	public ResponseEntity<RequestStage> save(@RequestBody RequestStage requestStage) {
		RequestStage createdRequestStage = requestStageService.save(requestStage);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdRequestStage);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<RequestStage> findById(@PathVariable(name = "id") Long id){
		RequestStage requestStage = requestStageService.findById(id);
		return ResponseEntity.ok(requestStage);
	}
	
	@GetMapping("/v2/{idRequest}") //Lazy Loading
	public ResponseEntity<PageModel<RequestStage>> findAllByRequestId(
			@PathVariable(name = "idRequest") Long requestId,
			@RequestParam(value = "page", defaultValue = "0" ) int page,
			@RequestParam(value = "size", defaultValue = "10") int size){
		
		PageRequestModel pageRequestModel = new PageRequestModel(page, size);
				
		PageModel<RequestStage> pageModel = requestStageService.findAllByRequestId(requestId, pageRequestModel);
		
		return ResponseEntity.ok(pageModel);
	}
}
