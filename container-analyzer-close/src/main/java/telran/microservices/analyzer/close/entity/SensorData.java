package telran.microservices.analyzer.close.entity;

import org.springframework.data.redis.core.RedisHash;

@RedisHash
public record SensorData(long containerId ,double currentVolume ) {

}
