package telran.sensorInfoLog;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import telran.coumputerizedWarehouse.dto.ContainerSensor;
import telran.sensorInfoLog.entities.ContainerInfo;
import telran.sensorInfoLog.repo.ContainerLog;

@SpringBootTest(properties = {})
@Import(TestChannelBinderConfiguration.class)
public class SensorLogTest {
	@MockBean
	ContainerLog repo;
	@Autowired
	InputDestination producer;
	private static final long CONTAINER_ID = 123;
	private static final Long ID = (long) 123;

	private static final double VALUE = 0.8;
	ContainerSensor sensor= new ContainerSensor(CONTAINER_ID, VALUE);
	private String consumerBindingName = "containerInfoConsumer-in-0";

	static HashMap<Long,ContainerInfo> sqlMap = new HashMap<>();
	
	@Test
	void Test() {
	
			when(repo.save(Mockito.any(ContainerInfo.class))).
					thenAnswer(new Answer<ContainerInfo>() {

				@Override
				public ContainerInfo answer(InvocationOnMock invocation) throws Throwable {
					sqlMap.put(ID, invocation.getArgument(0));
					return invocation.getArgument(0);
				}
				
				
			});
		producer.send(new GenericMessage<ContainerSensor>(sensor), consumerBindingName );
		assertEquals(1,sqlMap.size());
		assertEquals(CONTAINER_ID,sqlMap.get(ID).getContainerId());
		assertEquals( VALUE,sqlMap.get(ID).getSensorValue());

	
	}
}
