package nc.uap.wfm.vo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nc.bs.framework.common.NCLocator;
import nc.uap.dbl.constant.DblConstants;
import nc.uap.dbl.itf.IFrmItmQry;
import nc.uap.dbl.itf.IFrmTmpQry;
import nc.uap.dbl.vo.DblFormItemVO;
import nc.uap.dbl.vo.DblFormTemplateVO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.file.vo.LfwFileVO;
import nc.uap.wfm.model.FrmFieldDesc;
public class DefaultFormVO extends AbstractFormVO {
	private static final long serialVersionUID = 616754018132989336L;
	protected Map<String, String> prop = new HashMap<String, String>();
	private LfwFileVO[] attachFile;
	private WfmPageAttachVO[] attachList;
	private DefaultFormVO[] dynaRowData;
	private String tabName;
	private String tabPk;
	public DefaultFormVO() {}
	public String getTabName() {
		return tabName;
	}
	public void setTabName(String tabName) {
		this.tabName = tabName;
	}
	public String getTabPk() {
		return tabPk;
	}
	public void setTabPk(String tabPk) {
		this.tabPk = tabPk;
	}
	public DefaultFormVO[] getDynaRowData() {
		return dynaRowData;
	}
	public void setDynaRowData(DefaultFormVO[] dynaRowData) {
		this.dynaRowData = dynaRowData;
	}
	public LfwFileVO[] getAttachFile() {
		return attachFile;
	}
	public void setAttachFile(LfwFileVO[] attachFile) {
		this.attachFile = attachFile;
	}
	public WfmPageAttachVO[] getAttachList() {
		return attachList;
	}
	public void setAttachList(WfmPageAttachVO[] attachList) {
		this.attachList = attachList;
	}
	public String getBillMakeDateField() {
		return DblConstants.MakeFormDate;
	}
	public String getBillMakeUserField() {
		return DblConstants.MakeFormUser;
	}
	public String getBillMakeNumbField() {
		return DblConstants.MakeFormNumb;
	}
	public String getBillMakeDeptField() {
		return DblConstants.MakeBillDept;
	}
	public String getFrmStateField() {
		return DblConstants.FormState;
	}
	public String getFrmAuditDateField() {
		return DblConstants.FrmAuditDate;
	}
	public String getFrmAuditUserField() {
		return DblConstants.FrmAuditUser;
	}
	public String getBillMakeDept() {
		return this.getProp().get(this.getBillMakeDeptField());
	}
	public Object getValueByAttr(String attribute) {
		return this.getProp().get(attribute);
	}
	public Map<String, String> getProp() {
		return prop;
	}
	public void setProp(Map<String, String> prop) {
		this.prop = prop;
	}
	public List<FrmFieldDesc> getFormFields() {
		List<FrmFieldDesc> list = new ArrayList<FrmFieldDesc>();
		try {
			DblFormTemplateVO frmTmpVo = NCLocator.getInstance().lookup(IFrmTmpQry.class).getFrmTmpByFrmDefPk(frmDefPk);
			DblFormItemVO[] frmItmVos = NCLocator.getInstance().lookup(IFrmItmQry.class).getCurrentFrmItms(frmTmpVo);
			frmItmVos = NCLocator.getInstance().lookup(IFrmItmQry.class).getNormalFrmItms(frmItmVos);
			if (frmItmVos == null || frmItmVos.length == 0) {
				return null;
			}
			DblFormItemVO tmpFormItemVo = null;
			FrmFieldDesc frmFieldDesc = null;
			for (int i = 0; i < frmItmVos.length; i++) {
				tmpFormItemVo = frmItmVos[i];
				frmFieldDesc = new FrmFieldDesc();
				frmFieldDesc.setName(tmpFormItemVo.getOccupydomain());
				frmFieldDesc.setNameZH(tmpFormItemVo.getNamezh());
				frmFieldDesc.setDataType(tmpFormItemVo.getFieldtype());
				frmFieldDesc.setCompType(tmpFormItemVo.getComponenttype());
				frmFieldDesc.setRefType(tmpFormItemVo.getReftype());
				frmFieldDesc.setServerClass(tmpFormItemVo.getServerclass());
				String lableAndValue = tmpFormItemVo.getLabelandvalue();
				if (lableAndValue != null && lableAndValue.length() != 0) {
					Map<String, String> comboxData = new HashMap<String, String>();
					String[] rows = lableAndValue.split("\r\n");
					if (rows != null) {
						for (int j = 0; j < rows.length; j++) {
							String column[] = rows[j].split("\t");
							comboxData.put(column[0], column[1]);
						}
						frmFieldDesc.setComboxData(comboxData);
					}
				}
				list.add(frmFieldDesc);
			}
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		return list;
	}
	public void setAttributeValue(String attrValue) {}
}
