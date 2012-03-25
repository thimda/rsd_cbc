package nc.uap.wfm.runtime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.vos.CpUserVO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.util.StringUtil;
import nc.uap.wfm.actorsgy.ActorSgyManager;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.contanier.ProDefsContainer;
import nc.uap.wfm.context.HumActInfoPageCtx;
import nc.uap.wfm.engine.IHumActHandler;
import nc.uap.wfm.engine.ILogicDecision;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.handler.PortAndEdgeHandler;
import nc.uap.wfm.model.Activity;
import nc.uap.wfm.model.EndEvent;
import nc.uap.wfm.model.Event;
import nc.uap.wfm.model.ExcGat;
import nc.uap.wfm.model.GateWay;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.IEdge;
import nc.uap.wfm.model.IGraphElement;
import nc.uap.wfm.model.IPort;
import nc.uap.wfm.model.IncGat;
import nc.uap.wfm.model.ManAct;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.model.RecAct;
import nc.uap.wfm.model.ScrAct;
import nc.uap.wfm.model.SequenceFlow;
import nc.uap.wfm.model.StartEvent;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.next.NextCalculator;
import nc.uap.wfm.trans.WfmTransUtil;
import nc.uap.wfm.utils.WfmTaskUtil;
import nc.uap.wfm.utils.WfmClassUtil;
import nc.uap.wfm.vo.WfmFormInfoCtx;
public class NextHumActInfoUtil {
	/**
	 * 获取流程开始下一步节点信息
	 * 
	 * @param formVo
	 * @param proDef
	 * @return
	 */
	public List<HumActInfoPageCtx> getStartNextHumActInfo(String flowTypePk, WfmFormInfoCtx formVo) {
		try {
			return getHumActInfoPageCtx(null, ProDefsContainer.getProDefByFlowTypePk(flowTypePk), formVo, false);
		} catch (WfmServiceException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
	/**
	 * 获取流程开始下一步节点信息
	 * 
	 * @param formVo
	 * @param proDef
	 * @return
	 */
	public List<HumActInfoPageCtx> getStartNextHumActInfo(String flowTypePk, WfmFormInfoCtx formVo, boolean isChannel) {
		try {
			return getHumActInfoPageCtx(null, ProDefsContainer.getProDefByFlowTypePk(flowTypePk), formVo, isChannel);
		} catch (WfmServiceException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
	/**
	 * 获取下一步活动信息
	 * 
	 * @param pktask
	 * @param formVo
	 * @return
	 */
	public List<HumActInfoPageCtx> getNextHumActInfo(String taskPk, WfmFormInfoCtx formVo) {
		return getNextHumActInfo(WfmTaskUtil.getTaskFromSessionCache(taskPk), formVo, false);
	}
	/**
	 * 获取流程下一步节点信息
	 * 
	 * @param formVo
	 * @param proDef
	 * @return
	 */
	public List<HumActInfoPageCtx> getNextHumActInfo(Task task, WfmFormInfoCtx formVo) {
		return this.getNextHumActInfo(task, formVo, false);
	}
	/**
	 * 获取下一步活动信息
	 * 
	 * @param pktask
	 * @param formVo
	 * @return
	 */
	public List<HumActInfoPageCtx> getNextHumActInfo(String taskPk, WfmFormInfoCtx formVo, boolean isChannel) {
		return getNextHumActInfo(WfmTaskUtil.getTaskFromSessionCache(taskPk), formVo, isChannel);
	}
	/**
	 * 获取流程下一步节点信息
	 * 
	 * @param formVo
	 * @param proDef
	 * @return
	 */
	public List<HumActInfoPageCtx> getNextHumActInfo(Task task, WfmFormInfoCtx formVo, boolean isChannel) {
		List<HumActInfoPageCtx> list = null;
		try {
			if (NextCalculator.isNeedNextHumAct(task)) {
				list = getHumActInfoPageCtx(task, null, formVo, isChannel);
			}
		} catch (WfmServiceException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		if (list == null) {
			list = new ArrayList<HumActInfoPageCtx>();
		}
		if (Task.CreateType_BeforeAddSign.equalsIgnoreCase(task.getCreateType())) {
			HumActInfoPageCtx humActPageCtx = getAddSignHumActInfo(task);
			if (humActPageCtx == null) {
				return list;
			} else {
				list.add(getAddSignHumActInfo(task));
			}
		}
		return list;
	}
	/**
	 * 获取加签返回的活动信息
	 * 
	 * @param task
	 * @return
	 */
	private HumActInfoPageCtx getAddSignHumActInfo(Task task) {
		if (Task.CreateType_BeforeAddSign.equalsIgnoreCase(task.getCreateType())) {
			HumAct humAct = task.getHumActIns().getHumAct();
			HumActInfoPageCtx pageInfo = new HumActInfoPageCtx();
			pageInfo.setPortId(humAct.getId());
			pageInfo.setPortName(humAct.getName());
			Task parentTask = task.getParent();
			String userPk = parentTask.getPk_owner();
			pageInfo.setUserPks(userPk);
			pageInfo.setUserNames(WfmTransUtil.getUserNameByUserPk(userPk));
			pageInfo.setAssign(false);
			return pageInfo;
		}
		return null;
	}
	private List<HumActInfoPageCtx> getHumActInfoPageCtx(Task task, ProDef proDef, WfmFormInfoCtx formVo, boolean isChannel) throws WfmServiceException {
		List<IPort> nextPorts = new ArrayList<IPort>();
		HumAct cntHumAct = null;
		if (task == null) {
			cntHumAct = (HumAct) WfmTaskUtil.getStratPort(proDef);
		} else {
			cntHumAct = task.getHumActIns().getHumAct();
		}
		this.getNextHumActs(task, nextPorts, formVo, PortAndEdgeHandler.getOutEdges(cntHumAct));
		List<HumActInfoPageCtx> list = new ArrayList<HumActInfoPageCtx>();
		/**
		 * 当前活动节点允许快速通道，如果当钱活动节点类型是领导审核,并且前台控制是需要快速通道
		 */
		if (cntHumAct.isNotChannel() && isChannel && (nextPorts.size() > 1) && WfmConstants.ActionType_LeaderAudit.equalsIgnoreCase(cntHumAct.getActionType())) {
			IPort port = null;
			for (Iterator<IPort> iter = nextPorts.iterator(); iter.hasNext();) {
				port = iter.next();
				if (port instanceof HumAct) {
					HumAct humAct = (HumAct) port;
					if (WfmConstants.ActionType_LeaderAudit.equalsIgnoreCase(humAct.getActionType())) {
						list.add(this.getHumActInfoPageVo(task, formVo, port));
					} else {
						continue;
					}
				} else {
					list.add(this.getHumActInfoPageVo(task, formVo, port));
				}
			}
		} else if (cntHumAct.isNotChannel() && !isChannel && (nextPorts.size() > 1) && WfmConstants.ActionType_LeaderAudit.equalsIgnoreCase(cntHumAct.getActionType())) {
			IPort port = null;
			for (Iterator<IPort> iter = nextPorts.iterator(); iter.hasNext();) {
				port = iter.next();
				if (port instanceof HumAct) {
					HumAct humAct = (HumAct) port;
					if (WfmConstants.ActionType_LeaderAudit.equalsIgnoreCase(humAct.getActionType())) {
						continue;
					} else {
						list.add(this.getHumActInfoPageVo(task, formVo, port));
					}
				} else {
					list.add(this.getHumActInfoPageVo(task, formVo, port));
				}
			}
		} else {
			for (Iterator<IPort> iter = nextPorts.iterator(); iter.hasNext();) {
				list.add(this.getHumActInfoPageVo(task, formVo, iter.next()));
			}
		}
		return list;
	}
	public void getNextHumActs(Task task, List<IPort> nextInfo, WfmFormInfoCtx formVo, IGraphElement[] ges) throws WfmServiceException {
		for (int i = 0; i < ges.length; i++) {
			IGraphElement o = ges[i];
			if (o instanceof IPort) {
				IPort port = (IPort) o;
				if (port instanceof GateWay) {
					/**
					 * 唯一网关
					 */
					if (port instanceof ExcGat) {
						IEdge[] outEdges = PortAndEdgeHandler.getOutEdges(port);
						this.getNextHumActs(task, nextInfo, formVo, outEdges);
						continue;
					}
					/**
					 * 包含网关
					 */
					if (port instanceof IncGat) {
						this.getNextHumActs(task, nextInfo, formVo, PortAndEdgeHandler.getOutEdges(port));
						continue;
					}
				}
				if (port instanceof Activity) {
					/**
					 * 人工活动
					 */
					if (port instanceof HumAct) {
						nextInfo.add(port);
					}
					/**
					 * 手工活动
					 */
					if (port instanceof ManAct) {
						IEdge[] outEdges = PortAndEdgeHandler.getOutEdges(port);
						this.getNextHumActs(task, nextInfo, formVo, outEdges);
						continue;
					}
					/**
					 * 脚本活动
					 */
					if (port instanceof ScrAct) {
						IEdge[] outEdges = PortAndEdgeHandler.getOutEdges(port);
						this.getNextHumActs(task, nextInfo, formVo, outEdges);
						continue;
					}
					/**
					 * 接收活动
					 */
					if (port instanceof RecAct) {
						IEdge[] outEdges = PortAndEdgeHandler.getOutEdges(port);
						this.getNextHumActs(task, nextInfo, formVo, outEdges);
						continue;
					}
				}
				if (port instanceof Event) {
					/**
					 * 开始节点
					 */
					if (port instanceof StartEvent) {
						IEdge[] outEdges = PortAndEdgeHandler.getOutEdges(port);
						this.getNextHumActs(task, nextInfo, formVo, outEdges);
						continue;
					}
					/**
					 * 结束节点
					 */
					if (port instanceof EndEvent) {
						IEdge[] outEdges = PortAndEdgeHandler.getOutEdges(port);
						nextInfo.add(port);
						this.getNextHumActs(task, nextInfo, formVo, outEdges);
					}
				}
				if (port instanceof ProDef) {
					nextInfo.add(port);
				}
			}
			if (o instanceof IEdge) {
				/**
				 * 连线处理
				 */
				SequenceFlow sf = (SequenceFlow) o;
				if (formVo == null) {
					this.getNextHumActs(task, nextInfo, formVo, PortAndEdgeHandler.getTargetPorts(sf));
				} else {
					ILogicDecision logic = (ILogicDecision) WfmClassUtil.loadClass(sf.getSelfDefClass());
					if (logic.judge(task, sf, formVo)) {
						this.getNextHumActs(task, nextInfo, formVo, PortAndEdgeHandler.getTargetPorts(sf));
					}
				}
			}
		}
	}
	public HumActInfoPageCtx getHumActInfoPageVo(Task task, WfmFormInfoCtx formVo, IPort port) {
		HumActInfoPageCtx vo = new HumActInfoPageCtx();
		vo.setPortId(port.getId());
		if (port instanceof HumAct) {
			HumAct humAct = (HumAct) port;
			vo.setPortName(humAct.getName());
			String[] userPks = this.getUserPks(task, humAct, formVo);
			String userPkStr = StringUtil.merge(userPks);
			vo.setUserPks(userPkStr);
			vo.setUserNames(this.getUserNamesByUserPks(userPks));
			boolean isNotPreAssign = humAct.isNotPreAssign();
			if (userPks == null || userPks.length == 0) {
				IHumActHandler humActHandler = (IHumActHandler) WfmClassUtil.loadClass(humAct.getDelegatorClass());
				vo.setAssign(humActHandler.isAssign(task, humAct));
			} else {
				if (isNotPreAssign) {
					if (userPks.length == 1) {
						vo.setAssign(false);
					} else {
						vo.setAssign(true);
					}
				} else {
					IHumActHandler humActHandler = (IHumActHandler) WfmClassUtil.loadClass(humAct.getDelegatorClass());
					vo.setAssign(humActHandler.isAssign(task, humAct));
				}
			}
		}
		if (port instanceof EndEvent) {
			EndEvent endEvent = (EndEvent) port;
			vo.setPortName(endEvent.getName());
			vo.setAssign(false);
		}
		return vo;
	}
	public String getUserNamesByUserPks(String[] userPks) {
		CpUserVO[] userVos = null;
		try {
			userVos = CpbServiceFacility.getCpUserQry().getUserByPkS(userPks);
		} catch (CpbBusinessException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		if (userVos == null || userVos.length == 0) {
			return "";
		}
		CpUserVO userVo = null;
		StringBuffer sb = new StringBuffer();
		int length = userVos.length;
		for (int i = 0; i < length; i++) {
			userVo = userVos[i];
			sb.append(userVo.getUser_name()).append(",");
		}
		if (sb.length() > 1) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}
	public String[] getUserPks(Task task, HumAct humAct, WfmFormInfoCtx formVo) {
		String[] userPks = ActorSgyManager.getInstance().getActorsRange(formVo, task == null ? null : task.getProIns(), humAct, task);
		return userPks;
	}
}
