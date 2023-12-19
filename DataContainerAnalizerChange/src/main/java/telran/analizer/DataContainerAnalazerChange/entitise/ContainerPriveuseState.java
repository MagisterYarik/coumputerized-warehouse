package telran.analizer.DataContainerAnalazerChange.entitise;

import java.util.Objects;

import org.springframework.data.redis.core.RedisHash;

import lombok.Getter;
@RedisHash

public class ContainerPriveuseState {
	long containerId ;
	double currentVolume;
	public ContainerPriveuseState(long containerId, double currentVolume) {
		super();
		this.containerId = containerId;
		this.currentVolume = currentVolume;
	}
	public ContainerPriveuseState() {
		super();
	}
	@Override
	public int hashCode() {
		return Objects.hash(containerId, currentVolume);
	}
	public long getContainerId() {
		return containerId;
	}
	public void setContainerId(long containerId) {
		this.containerId = containerId;
	}
	public double getCurrentVolume() {
		return currentVolume;
	}
	public void setCurrentVolume(double currentVolume) {
		this.currentVolume = currentVolume;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContainerPriveuseState other = (ContainerPriveuseState) obj;
		return containerId == other.containerId
				&& Double.doubleToLongBits(currentVolume) == Double.doubleToLongBits(other.currentVolume);
	}
	
}
