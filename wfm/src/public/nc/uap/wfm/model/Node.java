package nc.uap.wfm.model;

public class Node {

	private String id;
	private String pid;
	private boolean isNotPending;
	private boolean isNotExe;
	private boolean isNotPas;
	private boolean isNotReject;
	private boolean isNotAddSign;
	private boolean isNotTran;
	private boolean isNotCntNode;
	private String tooltip;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public boolean isNotPending() {
		return isNotPending;
	}

	public void setNotPending(boolean isNotPending) {
		this.isNotPending = isNotPending;
	}

	public boolean isNotExe() {
		return isNotExe;
	}

	public void setNotExe(boolean isNotExe) {
		this.isNotExe = isNotExe;
	}

	public boolean isNotPas() {
		return isNotPas;
	}

	public void setNotPas(boolean isNotPas) {
		this.isNotPas = isNotPas;
	}

	public boolean isNotReject() {
		return isNotReject;
	}

	public void setNotReject(boolean isNotReject) {
		this.isNotReject = isNotReject;
	}

	public boolean isNotAddSign() {
		return isNotAddSign;
	}

	public void setNotAddSign(boolean isNotAddSign) {
		this.isNotAddSign = isNotAddSign;
	}

	public boolean isNotTran() {
		return isNotTran;
	}

	public void setNotTran(boolean isNotTran) {
		this.isNotTran = isNotTran;
	}

	public boolean isNotCntNode() {
		return isNotCntNode;
	}

	public void setNotCntNode(boolean isNotCntNode) {
		this.isNotCntNode = isNotCntNode;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

}
