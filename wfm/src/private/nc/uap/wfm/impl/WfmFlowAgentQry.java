package nc.uap.wfm.impl;
import java.util.List;
import nc.jdbc.framework.SQLParameter;
import nc.uap.cpb.org.util.CpbUtil;
import nc.uap.wfm.itf.IWfmFlowAgentQry;
import nc.uap.wfm.vo.WfmFlowAgentVO;
import nc.vo.pub.lang.UFDateTime;
public class WfmFlowAgentQry implements IWfmFlowAgentQry {
	public WfmFlowAgentVO getFlowAgentVoByPk(String flowAgentPk) {
		String sql = "select * from " + new WfmFlowAgentVO().getTableName() + " where pk_flowagent='" + flowAgentPk + "'";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(flowAgentPk);
		return CpbUtil.returnValue(this.queryList(sql, null));
	}
	protected List<WfmFlowAgentVO> queryList(String sql, SQLParameter parameter) {
		return CpbUtil.queryList(sql, parameter, WfmFlowAgentVO.class);
	}
	public WfmFlowAgentVO[] getFlowAgentVos() {
		String sql = "select * from " + new WfmFlowAgentVO().getTableName();
		return CpbUtil.returnArray(this.queryList(sql, null));
	}
	public WfmFlowAgentVO[] getFlowAgentVosByWhere(String whereSql) {
		String sql = "select * from " + new WfmFlowAgentVO().getTableName() + " where " + whereSql;
		return CpbUtil.returnArray(this.queryList(sql, null));
	}
	public WfmFlowAgentVO getFlowAgentVoByFlowTypePk(String flowTypePk) {
		String sql = "pk_flowtype='" + flowTypePk + "'";
		sql = sql + " and startdate<='" + new UFDateTime().toString() + "'";
		sql = sql + " and stopdate >='" + new UFDateTime().toString() + "' ";
		sql = sql + " and pk_user='" + CpbUtil.getCntUserPk() + "'";
		WfmFlowAgentVO[] vos = this.getFlowAgentVosByWhere(flowTypePk);
		if (vos == null || vos.length == 1) {
			return vos[0];
		} else {
			return null;
		}
	}
}
