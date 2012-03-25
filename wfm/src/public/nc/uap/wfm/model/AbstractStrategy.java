package nc.uap.wfm.model;
public abstract class AbstractStrategy {
	private String value;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public abstract int getStrategyType();
}
