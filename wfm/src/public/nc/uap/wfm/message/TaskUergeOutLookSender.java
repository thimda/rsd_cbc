package nc.uap.wfm.message;
import java.util.Map;

import nc.uap.lfw.core.exception.LfwRuntimeException;
/**
 * 超时任务的outlook信息实现
 * @author zhangxya
 *
 */
public class TaskUergeOutLookSender implements TaskUregeMessageSender {
	@Override
	public void sendTaskUregeMessage(Map<String, Object> messageMap) {
		if (1 == 1)
			throw new LfwRuntimeException("asdfsad");
	}
}
