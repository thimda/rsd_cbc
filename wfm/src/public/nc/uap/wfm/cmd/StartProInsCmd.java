package nc.uap.wfm.cmd;
import nc.uap.wfm.exception.WfmServiceException;
public class StartProInsCmd implements ICommand<Void> {
	public Void execute() throws WfmServiceException {
		ICommandService commandService = new CommandService();
		/**
		 * 流水号生成
		 */
		commandService.execute(new GenFrmNumBillCmd());
		/**
		 * 创建流程实例
		 */
		commandService.execute(new CreateProInsCmd());
		/**
		 * 保存指派的信息
		 */
		commandService.execute(new SaveAssignInfoCmd());
		/**
		 * 创建启动任务
		 */
		commandService.execute(new CreateStartTaskCmd());
		/**
		 *触发流程走到第一个活动节点
		 */
		commandService.execute(new SignalCmd());
		return null;
	}
}
