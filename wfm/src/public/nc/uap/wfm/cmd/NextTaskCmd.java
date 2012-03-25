package nc.uap.wfm.cmd;
import nc.uap.wfm.exception.WfmServiceException;
public class NextTaskCmd implements ICommand<Void> {
	public Void execute() throws WfmServiceException {
		ICommandService commandService = new CommandService();
		/**
		 * 流水号生成
		 */
		commandService.execute(new GenFrmNumBillCmd());
		/**
		 * 检测流程实例状态
		 */
		commandService.execute(new CheckProInsStateCmd());
		/**
		 * 保存指派的信息
		 */
		commandService.execute(new SaveAssignInfoCmd());
		/**
		 * 完成本步任务
		 */
		commandService.execute(new CompleteTaskCmd());
		/**
		 * 活动实例检测
		 */
		if (commandService.execute(new CheckHumActInsStateCmd()).booleanValue()) {
			/**
			 * 完成本步活动
			 */
			commandService.execute(new CompleteHumActInsCmd());
			/**
			 *触发流程走下一步
			 */
			commandService.execute(new SignalCmd());
		}
		return null;
	}
}
