package nc.uap.dbl.context;

import nc.uap.dbl.vo.DblFormDefinitionVO;
import nc.uap.dbl.vo.DblFormItemVO;
import nc.uap.dbl.vo.DblFormTemplateVO;

public class DblContext {
	public static final ThreadLocal<FormSession> formContext = new ThreadLocal<FormSession>();
	public static FormSession getCurrentFormSession() {
		return formContext.get();
	}
	public static void setFormSession(FormSession formSession) {
		formContext.set(formSession);
	}
	public static void removeFormSession() {
		formContext.remove();
	}
	public class FormSession {
		private String tableName;
		private DblFormDefinitionVO frmDefVO;
		private DblFormTemplateVO frmTmpVO;
		private DblFormItemVO[] frmItms;
		public String getTableName() {
			return tableName;
		}
		public void setTableName(String tableName) {
			this.tableName = tableName;
		}
		public DblFormDefinitionVO getFrmDefVO() {
			return frmDefVO;
		}
		public void setFrmDefVO(DblFormDefinitionVO frmDefVO) {
			this.frmDefVO = frmDefVO;
		}
		public DblFormTemplateVO getFrmTmpVO() {
			return frmTmpVO;
		}
		public void setFrmTmpVO(DblFormTemplateVO frmTmpVO) {
			this.frmTmpVO = frmTmpVO;
		}
		public DblFormItemVO[] getFrmItms() {
			return frmItms;
		}
		public void setFrmItms(DblFormItemVO[] frmItms) {
			this.frmItms = frmItms;
		}
		public void reset() {
			tableName = null;
			frmDefVO = null;
			frmTmpVO = null;
			frmItms = null;
		}
	}
}
