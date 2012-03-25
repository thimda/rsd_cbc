package nc.uap.wfm.exetask;
import nc.uap.lfw.core.comp.IWebPartContentFetcher;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.jsp.uimeta.UIMeta;
public class WebPartContentFetcherImpl implements IWebPartContentFetcher {
	public String fetchBodyScript(UIMeta um, PageMeta pm, LfwWidget view) {
		return "";
	}
	public String fetchHtml(UIMeta um, PageMeta pm, LfwWidget view) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<div style='border:1px solid #A0B3C6;width:300px;height:240px;top:5px;overflow-y:auto'>");
		buffer.append("</div>");
		return buffer.toString();
	}
}
