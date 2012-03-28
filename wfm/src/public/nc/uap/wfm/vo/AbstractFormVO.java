package nc.uap.wfm.vo;
import java.lang.reflect.Method;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.constant.WfmConstants;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDateTime;
public abstract class AbstractFormVO extends SuperVO implements WfmFormInfoCtx {
	private static final long serialVersionUID = -7717109260587098415L;
	protected String frmInsPk;
	protected String frmDefPk;
	protected String taskPk;
	public String getFrmInsPk() {
		if (frmInsPk == null) {
			frmInsPk = this.getPrimaryKey();
		}
		return frmInsPk;
	}
	public void setFrmInsPk(String frmInsPk) {
		this.frmInsPk = frmInsPk;
	}
	public void setFrmDefPk(String frmDefPk) {
		this.frmDefPk = frmDefPk;
	}
	public String getTaskPk() {
		return taskPk;
	}
	public void setTaskPk(String taskPk) {
		this.taskPk = taskPk;
	}
	public String getFrmDefPk() {
		return frmDefPk;
	}
	public void setBillMakeDate(UFDateTime billMakDate) {
		try {
			Method m = getMethodByField("set", getBillMakeDateField(), UFDateTime.class);
			m.invoke(this, billMakDate);
		} catch (Exception e) {
			LfwLogger.error(e);
		}
	}
	public String getBillMakeDate() {
		try {
			Method m = getMethodByField("get", getBillMakeDateField(), null);
			return (String) m.invoke(this);
		} catch (Exception e) {
			LfwLogger.error(e);
			return null;
		}
	}
	public void setBillMakeUser(String pk) {
		String field = this.getBillMakeUserField();
		try {
			Method m = getMethodByField("set", field, String.class);
			m.invoke(this, pk);
		} catch (Exception e) {
			LfwLogger.error(e);
		}
	}
	public String getBillMakeUser() {
		String field = this.getBillMakeUserField();
		try {
			Method m = getMethodByField("get", field, null);
			return (String) m.invoke(this);
		} catch (Exception e) {
			LfwLogger.error(e);
			return null;
		}
	}
	public String getBillMakeNumb() {
		String field = this.getBillMakeNumbField();
		try {
			Method m = getMethodByField("get", field, null);
			return (String) m.invoke(this);
		} catch (Exception e) {
			LfwLogger.error(e);
			return null;
		}
	}
	public void setBillMakeNumb(String pk) {
		String field = this.getBillMakeNumbField();
		try {
			Method m = getMethodByField("set", field, String.class);
			m.invoke(this, pk);
		} catch (Exception e) {
			LfwLogger.error(e);
		}
	}
	public void setBillMakeDept(String pk) {
		String field = getBillMakeDeptField();
		try {
			Method m = getMethodByField("set", field, String.class);
			m.invoke(this, pk);
		} catch (Exception e) {
			LfwLogger.error(e);
		}
	}
	public String getBillMakeDept() {
		String field = getBillMakeDeptField();
		try {
			Method m = getMethodByField("get", field, null);
			return (String) m.invoke(this);
		} catch (Exception e) {
			LfwLogger.error(e);
			return null;
		}
	}
	public void setFrmAuditUser(String pk) {
		String field = this.getFrmAuditUserField();
		try {
			Method m = getMethodByField("set", field, String.class);
			m.invoke(this, pk);
		} catch (Exception e) {
			LfwLogger.error(e);
		}
	}
	public String getFrmAuditUser() {
		String field = this.getFrmAuditUserField();
		try {
			Method m = getMethodByField("get", field, null);
			return (String) m.invoke(this);
		} catch (Exception e) {
			LfwLogger.error(e);
			return null;
		}
	}
	public void setFrmAuditDate(UFDateTime frmAuditDate) {
		String field = this.getFrmAuditDateField();
		try {
			Method m = getMethodByField("set", field, UFDateTime.class);
			m.invoke(this, frmAuditDate);
		} catch (Exception e) {
			LfwLogger.error(e);
		}
	}
	public String getFrmAuditDate() {
		String field = this.getFrmAuditDateField();
		try {
			Method m = getMethodByField("get", field, null);
			return (String) m.invoke(this);
		} catch (Exception e) {
			LfwLogger.error(e);
			return null;
		}
	}
	public void setFrmState(Integer frmState) {
		String field = this.getFrmStateField();
		try {
			Method m = getMethodByField("set", field, Integer.class);
			if (m == null) {
				return;
			}
			m.invoke(this, frmState);
		} catch (Exception e) {
			LfwLogger.error(e);
		}
	}
	public String getFrmState() {
		String field = this.getFrmStateField();
		try {
			Method m = getMethodByField("get", field, String.class);
			return (String) m.invoke(this);
		} catch (Exception e) {
			LfwLogger.error(e);
			return null;
		}
	}
	@SuppressWarnings("unchecked") private Method getMethodByField(String pre, String field, Class c) throws SecurityException, NoSuchMethodException {
		if (field == null)
			return null;
		String mName = field.substring(0, 1).toUpperCase() + field.substring(1);
		Class[] type = null;
		if (c != null)
			type = new Class[] { c };
		else
			type = new Class[] {};
		Method m = getClass().getMethod(pre + mName, type);
		return m;
	}
	public void handlerDefaltAttr() {
		if (this.getBillMakeDate() == null) {
			this.setBillMakeDate(new UFDateTime());
		}
		if (this.getBillMakeUser() == null) {
			this.setBillMakeUser(LfwRuntimeEnvironment.getLfwSessionBean().getPk_user());
		}
		if (this.getBillMakeNumb() == null) {
			this.setBillMakeNumb("");
		}
		if (this.getBillMakeDept() == null) {
			this.setBillMakeDept("");
		}
		if (this.getFrmState() == null) {
			String taskPk = LfwRuntimeEnvironment.getWebContext().getParameter(WfmConstants.WfmUrlConst_TaskPk);
			if (taskPk == null) {
				this.setFrmState(0);
			} else {
				this.setFrmState(1);
			}
		}
		this.setFrmAuditUser(LfwRuntimeEnvironment.getLfwSessionBean().getPk_user());
		this.setFrmAuditDate(new UFDateTime());
	}
	public void builder(String taskPk, String frmDefPk, String frmInsPk) {}
	public Object getValueByAttr(String attribute) {
		return this.getAttributeValue(attribute);
	};
}
