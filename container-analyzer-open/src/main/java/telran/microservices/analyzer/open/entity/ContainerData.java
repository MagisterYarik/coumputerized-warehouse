package telran.microservices.analyzer.open.entity;

import org.springframework.data.redis.core.RedisHash;

@RedisHash
public  record ContainerData(long container_id, double current_volume) {

}
