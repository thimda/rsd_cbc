package nc.uap.wfm.model;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.vo.pub.lang.UFBoolean;

public class HumActIns extends AbstractObservable implements Cloneable, Serializable {
	public static final String End = "End";// 已办
	public static final String Exe = "Exe";// 在办
	public static final String Run = "Run";// 待办
	//public static final String Reject = "Reject";// 驳回
	public static final String AddSign = "AddSign";// 加签
	public static final String Suspended = "Suspended";// 挂起
	private static final long serialVersionUID = 6735897774357946748L;
	private ProIns proIns;
	private ProIns rootProIns;
	private Set<Task> tasks;
	private HumActIns parent;
	private IPort port;
	private IPort pPort;
	private Set<HumActIns> subHumActIns;
	private String state;
	private UFBoolean isNotExe;
	private UFBoolean isNotPas;
	private UFBoolean isNotReject;
	private String pk_humactins;

	public String getPk_humactins() {
		return pk_humactins;
	}

	public void setPk_humactins(String pk_humactins) {
		this.pk_humactins = pk_humactins;
	}

	public ProIns getProIns() {
		return proIns;
	}

	public void setProIns(ProIns proIns) {
		this.proIns = proIns;
	}

	public HumActIns getParent() {
		return parent;
	}

	public void setParent(HumActIns parent) {
		this.parent = parent;
	}

	public IPort getPort() {
		return port;
	}

	public void setPort(IPort port) {
		this.port = port;
	}

	public HumAct getHumAct() {
		if (port instanceof HumAct) {
			return (HumAct) port;
		}
		return null;
	}

	public void setHumAct(HumAct humAct) {
		this.setPort(humAct);
	}

	public UFBoolean getIsNotExe() {
		return isNotExe;
	}

	public void setIsNotExe(UFBoolean isNotExe) {
		this.isNotExe = isNotExe;
	}

	public UFBoolean getIsNotPas() {
		return isNotPas;
	}

	public void setIsNotPas(UFBoolean isNotPas) {
		this.isNotPas = isNotPas;
	}

	public Set<HumActIns> getSubHumActIns() {
		return subHumActIns;
	}

	public void setSubHumActIns(Set<HumActIns> subHumActIns) {
		this.subHumActIns = subHumActIns;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Set<Task> getTasks() {
		return tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}

	public ProIns getRootProIns() {
		return rootProIns;
	}

	public void setRootProIns(ProIns rootProIns) {
		this.rootProIns = rootProIns;
	}

	public IPort getPPort() {
		return pPort;
	}

	public void setPPort(IPort port) {
		pPort = port;
	}

	public UFBoolean getIsNotReject() {
		return isNotReject;
	}

	public void setIsNotReject(UFBoolean isNotReject) {
		this.isNotReject = isNotReject;
	}

	public HumActIns clone() {
		HumActIns humActIns = new HumActIns();
		try {
			BeanUtils.copyProperties(humActIns, this);
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException("克隆失败");
		}
		return humActIns;
	}
}
