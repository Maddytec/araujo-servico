package br.com.maddytec.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.maddytec.domain.Request;
import br.com.maddytec.domain.RequestStage;
import br.com.maddytec.service.RequestService;
import br.com.maddytec.service.RequestStageService;

@RestController
@RequestMapping(value = "requests")
public class RequestResource {

	@Autowired
	private RequestService requestService;
	
	@Autowired
	RequestStageService requestStageService;

	@PostMapping
	public ResponseEntity<Request> save(@RequestBody Request request) {
		Request createdRequest = requestService.save(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Request> update(@PathVariable(name = "id") Long id, @RequestBody Request request) {
		request.setId(id);
		Request createdRequest = requestService.update(request);
		return ResponseEntity.ok(createdRequest);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Request> findById(@PathVariable(name = "id") Long id) {
		Request request = requestService.findById(id);
		return ResponseEntity.ok(request);
	}

	@GetMapping
	public ResponseEntity<List<Request>> findAll() {
		List<Request> requests = requestService.findAll();
		return ResponseEntity.ok(requests);
	}

	@GetMapping("/{id}/request-stages")
	public ResponseEntity<List<RequestStage>> findRequestStageByRequestId(@PathVariable(name = "requestId") Long requestId) {
		List<RequestStage> requestStages = requestStageService.findAllByRequestId(requestId);
		return ResponseEntity.ok(requestStages);
	}

}
