package nc.uap.wfm.context;
import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.contanier.ProDefsContainer;
import nc.uap.wfm.engine.IFlowRequest;
import nc.uap.wfm.engine.IFlowResponse;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmMyVisaQry;
import nc.uap.wfm.itf.IWfmProInsQry;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.model.ProIns;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.utils.WfmTaskUtil;
import nc.uap.wfm.vo.WfmFormInfoCtx;
import nc.uap.wfm.vo.WfmMyVisaVO;
public class PwfmContext {
	public static final ThreadLocal<BpmnSession> bpmnContext = new ThreadLocal<BpmnSession>();
	public static BpmnSession getCurrentBpmnSession() {
		return bpmnContext.get();
	}
	public static void seBpmnSession(BpmnSession bpmnSession) {
		bpmnContext.set(bpmnSession);
	}
	public static void removeFormSession() {
		bpmnContext.remove();
	}
	public class BpmnSession {
		private IFlowRequest request;
		private IFlowResponse response;
		private WfmFlowInfoCtx flwInfoCtx;
		private WfmFormInfoCtx formVo;
		private Task task;
		private HumAct currentHumAct;
		private ProDef proDef;
		private ProIns proIns;
		private ProIns pProIns;
		private HumActInfoEngCtx[] nextInfo;
		private HumActInfoEngCtx rejectInfo;
		private WfmMyVisaVO myVisa;
		private String opinion;
		private String scratchpad;
		private String currentUserPk;
		private AddSignUserInfoCtx[] addSignUserInfo;
		private DeliverUserInfoCtx[] deliverUserInfo;
		private String frmInsPk;
		private String[] msgType;
		private String startTaskPk;
		private int priority = 0;
		public HumAct getCurrentHumAct() {
			return currentHumAct;
		}
		public WfmFlowInfoCtx getFlwInfoCtx() {
			return flwInfoCtx;
		}
		public void setFlwInfoCtx(WfmFlowInfoCtx flwInfoCtx) {
			this.flwInfoCtx = flwInfoCtx;
		}
		public WfmFormInfoCtx getFormVo() {
			return formVo;
		}
		public void setFormVo(WfmFormInfoCtx formVo) {
			this.formVo = formVo;
		}
		public Task getTask() {
			if (task == null) {
				if (flwInfoCtx instanceof StartedProInsInfoCtx) {
					task = WfmTaskUtil.getTaskByTaskPk(((StartedProInsInfoCtx) flwInfoCtx).getTaskPk());
				} else {
					if (flwInfoCtx instanceof InterimProInsInfoCtx) {
						task = WfmTaskUtil.getTaskByTaskPk(((InterimProInsInfoCtx) flwInfoCtx).getTaskPk());
					}
				}
			}
			return task;
		}
		public void setTask(Task task) {
			this.task = task;
		}
		public ProDef getProDef() {
			if (proDef == null) {
				if (flwInfoCtx instanceof StartedProInsInfoCtx) {
					if (task == null) {
						task = WfmTaskUtil.getTaskByTaskPk(((StartedProInsInfoCtx) flwInfoCtx).getTaskPk());
					}
					proDef = task.getProDef();
				} else {
					if (flwInfoCtx instanceof UnStartProInsInfoCtx) {
						proDef = ProDefsContainer.getProDefByFlowTypePk(((UnStartProInsInfoCtx) flwInfoCtx).getFlowTypePk());
					} else {
						if (task == null) {
							proDef = ProDefsContainer.getProDefByFlowTypePk(((InterimProInsInfoCtx) flwInfoCtx).getFrmDefPk());
						} else {
							proDef = task.getProDef();
						}
					}
				}
			}
			return proDef;
		}
		public void setProDef(ProDef proDef) {
			this.proDef = proDef;
		}
		public ProIns getProIns() {
			if (proIns == null) {
				if (task == null) {
					if (flwInfoCtx instanceof StartedProInsInfoCtx) {
						if (task == null) {
							task = WfmTaskUtil.getTaskByTaskPk(((StartedProInsInfoCtx) flwInfoCtx).getTaskPk());
							proIns = task.getProIns();
						}
					}
				} else {
					proIns = task.getProIns();
				}
			}
			return proIns;
		}
		public void setProIns(ProIns proIns) {
			this.proIns = proIns;
		}
		public ProIns getPProIns() {
			if (pProIns == null) {
				String pProInsPk = null;
				if (flwInfoCtx instanceof CommitInfoCtx) {
					pProInsPk = ((CommitInfoCtx) flwInfoCtx).getPProInsPk();
				}
				if (flwInfoCtx instanceof InterimProInsInfoCtx) {
					pProInsPk = ((InterimProInsInfoCtx) flwInfoCtx).getPProInsPk();
				}
				try {
					pProIns = NCLocator.getInstance().lookup(IWfmProInsQry.class).getProInsByPk(pProInsPk);
				} catch (WfmServiceException e) {
					LfwLogger.error(e.getMessage(), e);
					throw new LfwRuntimeException(e.getMessage());
				}
			}
			return pProIns;
		}
		public void setPProIns(ProIns proIns) {
			pProIns = proIns;
		}
		public HumActInfoEngCtx[] getNextInfo() {
			if (nextInfo == null) {
				if (flwInfoCtx instanceof CommitInfoCtx) {
					nextInfo = ((CommitInfoCtx) flwInfoCtx).getNextInfo();
				}
				if (flwInfoCtx instanceof NextTaskInfoCtx) {
					nextInfo = ((NextTaskInfoCtx) flwInfoCtx).getNextInfo();
				}
			}
			return nextInfo;
		}
		public void setNextInfo(HumActInfoEngCtx[] nextInfo) {
			this.nextInfo = nextInfo;
		}
		public HumActInfoEngCtx getRejectInfo() {
			if (rejectInfo == null) {
				if (flwInfoCtx instanceof RejectTaskInfoCtx) {
					rejectInfo = ((RejectTaskInfoCtx) flwInfoCtx).getRejectInfo();
				}
			}
			return rejectInfo;
		}
		public void setRejectInfo(HumActInfoEngCtx rejectInfo) {
			this.rejectInfo = rejectInfo;
		}
		public WfmMyVisaVO getMyVisa() {
			if (myVisa == null) {
				myVisa = NCLocator.getInstance().lookup(IWfmMyVisaQry.class).getMyVisaByMyVisaPk(flwInfoCtx.getMyVisaPk());
			}
			return myVisa;
		}
		public void setMyVisa(WfmMyVisaVO myVisa) {
			this.myVisa = myVisa;
		}
		public String getOpinion() {
			if (opinion == null) {
				opinion = flwInfoCtx.getOpinion();
			}
			return opinion;
		}
		public void setOpinion(String opinion) {
			this.opinion = opinion;
		}
		public String getScratchpad() {
			if (scratchpad == null) {
				scratchpad = flwInfoCtx.getScratchpad();
			}
			return scratchpad;
		}
		public void setScratchpad(String scratchpad) {
			this.scratchpad = scratchpad;
		}
		public String getCurrentUserPk() {
			if (currentUserPk == null) {
				currentUserPk = flwInfoCtx.getCntUserPk();
			}
			return currentUserPk;
		}
		public void setCurrentUserPk(String currentUserPk) {
			this.currentUserPk = currentUserPk;
		}
		public void setCurrentHumAct(HumAct currentHumAct) {
			this.currentHumAct = currentHumAct;
		}
		public String getTransmitUserPk() {
			if (flwInfoCtx instanceof TransmitTaskInfoCtx) {
				TransmitTaskInfoCtx ctx = (TransmitTaskInfoCtx) flwInfoCtx;
				return ctx.getTransimgUserPk();
			}
			return null;
		}
		public String getFrmInsPk() {
			return frmInsPk;
		}
		public void setFrmInsPk(String frmInsPk) {
			this.frmInsPk = frmInsPk;
		}
		public AddSignUserInfoCtx[] getAddSignUserInfo() {
			if (addSignUserInfo == null) {
				if (flwInfoCtx instanceof AddSignTaskInfoCtx) {
					AddSignTaskInfoCtx flwInfoCtx = (AddSignTaskInfoCtx) this.getFlwInfoCtx();
					addSignUserInfo = flwInfoCtx.getAddSingUsers();
				}
			}
			return addSignUserInfo;
		}
		public void setAddSignUserInfo(AddSignUserInfoCtx[] addSignUserInfo) {
			this.addSignUserInfo = addSignUserInfo;
		}
		public DeliverUserInfoCtx[] getDeliverUserInfo() {
			if (deliverUserInfo == null) {
				if (flwInfoCtx instanceof DeliverInfoCtx) {
					DeliverInfoCtx flwInfoCtx = (DeliverInfoCtx) this.getFlwInfoCtx();
					deliverUserInfo = flwInfoCtx.getDeliverUserInfo();
				}
			}
			return deliverUserInfo;
		}
		public void setDeliverUserInfo(DeliverUserInfoCtx[] deliverUserInfo) {
			this.deliverUserInfo = deliverUserInfo;
		}
		public String[] getMsgType() {
			if (msgType == null || msgType.length == 0) {
				if (flwInfoCtx instanceof CommitInfoCtx) {
					msgType = ((CommitInfoCtx) flwInfoCtx).getMsgType();
				}
				if (flwInfoCtx instanceof NextTaskInfoCtx) {
					msgType = ((NextTaskInfoCtx) flwInfoCtx).getMsgType();
				}
			}
			return msgType;
		}
		public void setMsgType(String[] msgType) {
			this.msgType = msgType;
		}
		public String getStartTaskPk() {
			if (startTaskPk == null) {
				if (flwInfoCtx instanceof CommitInfoCtx) {
					startTaskPk = ((CommitInfoCtx) flwInfoCtx).getStartTaskPk();
				}
				if (flwInfoCtx instanceof InterimProInsInfoCtx) {
					startTaskPk = ((InterimProInsInfoCtx) flwInfoCtx).getStartTaskPk();
				}
			}
			return startTaskPk;
		}
		public IFlowRequest getRequest() {
			return request;
		}
		public void setRequest(IFlowRequest request) {
			this.request = request;
		}
		public IFlowResponse getResponse() {
			return response;
		}
		public void setResponse(IFlowResponse response) {
			this.response = response;
		}
		public void reset() {
			task = null;
			proDef = null;
			proIns = null;
			pProIns = null;
			nextInfo = null;
			rejectInfo = null;
			opinion = null;
			scratchpad = null;
			myVisa = null;
			flwInfoCtx = null;
			formVo = null;
			currentHumAct = null;
			currentUserPk = null;
			frmInsPk = null;
			addSignUserInfo = null;
			deliverUserInfo = null;
			startTaskPk = null;
			request = null;
			response = null;
		}
		public int getPriority() {
			return priority;
		}
		public void setPriority(int priority) {
			this.priority = priority;
		}
	}
}
