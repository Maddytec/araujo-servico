package br.com.maddytec.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.maddytec.domain.RequestStage;

@Repository
public interface RequestStageRepository extends JpaRepository<RequestStage, Long> {

	public List<RequestStage> findAllByRequestId(Long requestId);

	public Page<RequestStage> findAllByRequestId(Long requestId, Pageable pageable);

}
