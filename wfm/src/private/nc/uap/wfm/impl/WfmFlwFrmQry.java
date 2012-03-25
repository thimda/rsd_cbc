package nc.uap.wfm.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nc.bs.dao.DAOException;
import nc.jdbc.framework.processor.ArrayListProcessor;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmFlwFrmQry;
import nc.uap.wfm.vo.WfnFunnodeVO;
import nc.uap.wfm.vo.WfmFlwFrmVO;
import nc.vo.pub.lang.UFBoolean;

public class WfmFlwFrmQry implements IWfmFlwFrmQry {
	@SuppressWarnings( { "unchecked", "serial" })
	public WfmFlwFrmVO[] getFrmItmPrts(String proDefPk, String proDefId, String portId, String devicePk) throws WfmServiceException {
		if (proDefId == null || proDefId.length() == 0 || portId == null || portId.length() == 0) {
			return null;
		}
		PtBaseDAO dao = new PtBaseDAO();
		String sql = "";
		sql = "SELECT a.pk_flwfrm,a.pk_frmitm,a.pk_prodef,a.prodef_id,a.port_id,a.name,a.namezh,a.isnotreadonly,a.isnotrequired,a.isnotwrite,"
		+" isnull(b.isnotdisp,'Y') as isnotdisp "
		+ " FROM (select a.* from wfm_flwfrm a WHERE a.pk_prodef = '" + proDefPk + "'  And a.prodef_id = '" + proDefId + "'" + " And a.port_id = '" + portId
		+ "') a  LEFT JOIN (SELECT * FROM wfm_frmdevice a WHERE a.pk_device = '" + devicePk + "') b  on a.pk_prodef = b.pk_prodef "
		+ " AND a.prodef_id = b.prodef_id  AND a.port_id = b.port_id AND a.pk_frmitm = b.pk_frmitm";
		List<WfnFunnodeVO> list = null;
		try {
			list = (List<WfnFunnodeVO>) dao.executeQuery(sql, new ArrayListProcessor() {
				public Object processResultSet(ResultSet rs) throws SQLException {
					List<WfmFlwFrmVO> result = new ArrayList<WfmFlwFrmVO>();
					while (rs.next()) {
						result.add(toPtFunnodeVO(rs));
					}
					return result;
				}
				public WfmFlwFrmVO toPtFunnodeVO(ResultSet rs) throws SQLException {
					WfmFlwFrmVO vo = new WfmFlwFrmVO();
					vo.setPk_flwfrm(rs.getString("pk_flwfrm"));
					vo.setPk_frmitm(rs.getString("pk_frmitm"));
					vo.setPk_prodef(rs.getString("pk_prodef"));
					vo.setProdef_id(rs.getString("prodef_id"));
					vo.setPort_id(rs.getString("port_id"));
					vo.setName(rs.getString("name"));
					vo.setNamezh(rs.getString("namezh"));
					String flag = rs.getString("isnotreadonly");
					if (flag == null || flag.length() == 0) {
						vo.setIsnotreadonly(UFBoolean.TRUE);
					} else {
						if ("Y".equalsIgnoreCase(flag)) {
							vo.setIsnotreadonly(UFBoolean.TRUE);
						} else {
							vo.setIsnotreadonly(UFBoolean.FALSE);
						}
					}
					flag = rs.getString("isnotrequired");
					if (flag == null || flag.length() == 0) {
						vo.setIsnotrequired(UFBoolean.TRUE);
					} else {
						if ("Y".equalsIgnoreCase(flag)) {
							vo.setIsnotrequired(UFBoolean.TRUE);
						} else {
							vo.setIsnotrequired(UFBoolean.FALSE);
						}
					}
					flag = rs.getString("isnotwrite");
					if (flag == null || flag.length() == 0) {
						vo.setIsnotwrite(UFBoolean.TRUE);
					} else {
						if ("Y".equalsIgnoreCase(flag)) {
							vo.setIsnotwrite(UFBoolean.TRUE);
						} else {
							vo.setIsnotwrite(UFBoolean.FALSE);
						}
					}
					flag = rs.getString("isnotdisp");
					if (flag == null || flag.length() == 0) {
						vo.setIsnotdisp(UFBoolean.TRUE);
					} else {
						if ("Y".equalsIgnoreCase(flag)) {
							vo.setIsnotdisp(UFBoolean.TRUE);
						} else {
							vo.setIsnotdisp(UFBoolean.FALSE);
						}
					}
					return vo;
				}
			});
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
		return list.toArray(new WfmFlwFrmVO[0]);
	}
}
