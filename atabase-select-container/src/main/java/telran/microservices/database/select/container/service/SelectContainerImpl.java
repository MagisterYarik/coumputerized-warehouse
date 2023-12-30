package telran.microservices.database.select.container.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.coumputerizedWarehouse.dto.ContainerData;
import telran.coumputerizedWarehouse.entity.Container;
import telran.microservices.database.select.container.repo.ContainerRepo;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SelectContainerImpl implements SelectContainer {
	final ContainerRepo containerRepo;

	@Override
	public ContainerData getContainerById(long id) {
		Container container = containerRepo.findByContainerId(id);
		if (container == null) {
			throw new IllegalArgumentException("No container for id " + id);
		}
		ContainerData result = new ContainerData(container);
		return result;
	}

}
