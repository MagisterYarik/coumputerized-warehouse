package telran.sensorInfoLog;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.jdbc.Sql;

import telran.coumputerizedWarehouse.dto.ContainerSensorChanged;
import telran.sensorInfoLog.repo.ContainerLog;


@SpringBootTest(properties = {})
@Import(TestChannelBinderConfiguration.class)

public class SensorLogTest {
	@Autowired
	ContainerLog repo;
	@Autowired
	InputDestination producer;
	
	private static final long CONTAINER_ID = 123;

	private static final double VALUE = 0.8;
	ContainerSensorChanged sensor= new ContainerSensorChanged(CONTAINER_ID, VALUE,VALUE-0.1);
	private String consumerBindingName = "containerInfoConsumer-in-0";

	
	@Test
	void Test() {
	
		
		producer.send(new GenericMessage<ContainerSensorChanged>(sensor), consumerBindingName );
		assertEquals(1,repo.findAll().size());
		assertEquals(CONTAINER_ID,repo.findAll().get(0).getContainerId());
		assertEquals(VALUE,repo.findAll().get(0).getSensorValue());
		assertEquals(VALUE-0.1,repo.findAll().get(0).getSensorPrevValue());
	
	}
}
