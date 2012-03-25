package nc.uap.wfm.model;

public class Route {
	private String src;
	private String target;
	private boolean isNotBack;
	private int rejectCount;
	private int submitCount;

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public int getRejectCount() {
		return rejectCount;
	}

	public void setRejectCount(int rejectCount) {
		this.rejectCount = rejectCount;
	}

	public int getSubmitCount() {
		return submitCount;
	}

	public void setSubmitCount(int submitCount) {
		this.submitCount = submitCount;
	}

	public boolean isNotBack() {
		return isNotBack;
	}

	public void setNotBack(boolean isNotBack) {
		this.isNotBack = isNotBack;
	}

	public String getTooltip() {
		StringBuffer sb = new StringBuffer();
		if (this.submitCount > 0 && this.submitCount != 1) {
			sb.append("Ìב" + this.submitCount);
		}

		if (this.rejectCount != 0) {
			sb.append("ÍË" + this.rejectCount);
		}
		return sb.toString();
	}

	public void addReject(int count) {
		this.rejectCount = this.rejectCount + count;
	}

	public void addSubmit(int count) {
		this.submitCount = this.submitCount + count;
	}

}
