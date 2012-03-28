package nc.uap.ctrl.tpl.qry;

import nc.uap.lfw.core.model.IWidgetUIProvider;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.jsp.uimeta.UIButton;
import nc.uap.lfw.jsp.uimeta.UIConstant;
import nc.uap.lfw.jsp.uimeta.UIDiv;
import nc.uap.lfw.jsp.uimeta.UIFlowhLayout;
import nc.uap.lfw.jsp.uimeta.UIFlowhPanel;
import nc.uap.lfw.jsp.uimeta.UIFlowvLayout;
import nc.uap.lfw.jsp.uimeta.UIFlowvPanel;
import nc.uap.lfw.jsp.uimeta.UIMeta;
import nc.uap.lfw.jsp.uimeta.UISplitter;
import nc.uap.lfw.jsp.uimeta.UISplitterOne;
import nc.uap.lfw.jsp.uimeta.UISplitterTwo;
import nc.uap.lfw.jsp.uimeta.UITabComp;
import nc.uap.lfw.jsp.uimeta.UITabItem;
import nc.uap.lfw.jsp.uimeta.UITreeComp;

public class AdvancedQueryWidgetUIProvider implements IWidgetUIProvider {

	@Override
	public UIMeta getDefaultUIMeta(LfwWidget widget) {
		String widgetId = widget.getId();
		UIMeta um = new UIMeta();
		um.setId(widgetId + "_um");
		um.setFlowmode(Boolean.FALSE);
		um.setIncludeId("label,combotext,inputdialog,toolbar");
		
		UIFlowvLayout flowvLayout = new UIFlowvLayout();
		flowvLayout.setId("flowvLayout");
		um.setElement(flowvLayout);
		
		UIFlowvPanel flowvPanelTop = new UIFlowvPanel();
		flowvPanelTop.setId("top");
		flowvLayout.addPanel(flowvPanelTop);
		
		UIFlowvPanel flowvPanelBottom = new UIFlowvPanel();
		flowvPanelBottom.setId("bottom");
		flowvPanelBottom.setHeight("35");
		flowvLayout.addPanel(flowvPanelBottom);
		
		UISplitter hSplitter = new UISplitter();
		hSplitter.setId("hSpliter");
		hSplitter.setBoundMode(UISplitter.BOUNDMODE_PX);
		hSplitter.setDivideSize("220");
		hSplitter.setOneTouch(UIConstant.TRUE);
		flowvPanelTop.setElement(hSplitter);
		
		UISplitterOne sone = new UISplitterOne();
		hSplitter.addPanel(sone);
		
		UISplitterTwo stwo = new UISplitterTwo();
		hSplitter.addPanel(stwo);
		
		UITabComp tab = new UITabComp();
		tab.setId("conditionTab");
		sone.setElement(tab);
		
		UITabItem itemOne = new UITabItem();
		itemOne.setId("nowCondition");
		itemOne.setText("查询方案");
		
		UITabItem itemTwo = new UITabItem();
		itemTwo.setId("savedCondition");
		itemTwo.setText("候选条件");
		
		tab.addPanel(itemTwo);
		tab.addPanel(itemOne);
		
		UIFlowvLayout savedFlowvLayout = new UIFlowvLayout();
		savedFlowvLayout.setId("savedFlowvLayout");
		itemOne.setElement(savedFlowvLayout);
		
		UIFlowvPanel savedPanel1 = new UIFlowvPanel();
		savedPanel1.setId("p1");
		savedPanel1.setHeight("30");
		savedFlowvLayout.addPanel(savedPanel1);
		
		UIDiv savedDiv = new UIDiv();
		savedDiv.setId("favoritDiv");
		savedDiv.setStyle("width:100%;height:100%;overflow-y:auto;");
		savedPanel1.setElement(savedDiv);
		
		UIFlowvPanel savedPanel2 = new UIFlowvPanel();
		savedPanel2.setId("p2");
		savedFlowvLayout.addPanel(savedPanel2);
		
		UITreeComp savedTree = new UITreeComp();
		savedTree.setId(AdvancedQueryWidgetProvider.SAVED_TREE_ID);
		savedPanel2.setElement(savedTree);
		
		UITreeComp qtTree = new UITreeComp();
		qtTree.setId(AdvancedQueryWidgetProvider.QUERY_TEMPLATE_TREE_ID);
		itemTwo.setElement(qtTree);
		
		
		
		UITabComp sqlTab = new UITabComp();
		sqlTab.setId("sqlTab");
		stwo.setElement(sqlTab);
		
		UITabItem sqlTabOne = new UITabItem();
		sqlTabOne.setId("normalTab");
		sqlTabOne.setText("普通");
			
		UITabItem sqlTabTwo = new UITabItem();
		sqlTabTwo.setId("adbTab");
		sqlTabTwo.setText("高级");
		
		sqlTab.addPanel(sqlTabOne);
		sqlTab.addPanel(sqlTabTwo);
		
		sqlTab.setCurrentItem(sqlTabOne.getId());
		
		//<div id="$d_normalPanel" style="width:100%;height:100%;overflow-y:auto;"></div>
		UIDiv div = new UIDiv();
		div.setId("normalPanel");
		div.setStyle("width:100%;height:100%;overflow-y:auto;");
		sqlTabOne.setElement(div);
		
		UITreeComp advTree = new UITreeComp();
		advTree.setId(AdvancedQueryWidgetProvider.ADVANCE_TREE_ID);
		sqlTabTwo.setElement(advTree);
		
		
		UIFlowhLayout bLayout = new UIFlowhLayout();
		bLayout.setId("bottomFlowh");
		flowvPanelBottom.setElement(bLayout);
		
		UIFlowhPanel panel1 = new UIFlowhPanel();
		panel1.setId("panel1");
		bLayout.addPanel(panel1);
		
		UIFlowhPanel panel2 = new UIFlowhPanel();
		panel2.setId("panel2");
		panel2.setWidth("100");
		bLayout.addPanel(panel2);
		
		UIButton button = new UIButton();
		button.setId("okBt");
		panel2.setElement(button);
		
		UIFlowhPanel panel3 = new UIFlowhPanel();
		panel3.setId("panel3");
		panel3.setWidth("100");
		bLayout.addPanel(panel3);
		
		UIButton cancelBt = new UIButton();
		cancelBt.setId("cancelBt");
		panel3.setElement(cancelBt);
		
	//		</lfw:flowhPanel>
//		<lfw:flowhPanel width="100px">
//			<lfw:button id="okBt"/>
//		</lfw:flowhPanel>
//		<lfw:flowhPanel width="100px">
//			<lfw:button id="cancelBt"/>
//		</lfw:flowhPanel>
//		UICanvas canvas = new UICanvas();
//		canvas.setId("favoritCanvas");
//		
//		
//		UICanvasPanel canvasPanel = new UICanvasPanel();
//		canvasPanel.setId("favoritCanvasPanel");
		
		
//		hSplitter.setOrientation("h");
//		<lfw:borderLayout>
//		<lfw:borderPanel position="center">
//			<lfw:spliter divideSize="0.25" id="spliter1" orientation="h">
//				<lfw:spliterPanelOne>
//					<lfw:border width="1">
//					<lfw:tab id="conditionTab">
//						<lfw:tabitem id="nowCondition" text="查询方案">
//							<lfw:tree id="queryTemplateTree" />
//						</lfw:tabitem>
//						<lfw:tabitem id="savedCondition" text="候选条件">
//							<div id="favoritDiv" style="height:24px;background:#F0EFE7"></div>
//							<lfw:tree id="savedTree" />
//						</lfw:tabitem>	
//					</lfw:tab>
//					</lfw:border>
//				</lfw:spliterPanelOne>
//				<lfw:spliterPanelTwo>
//					<lfw:tab id="sqlTab">
//						<lfw:tabitem id="normalTab" text="普通">
//							<div id="$d_normalPanel" style="width:100%;height:100%;overflow-y:scroll;"></div>
//						</lfw:tabitem>
//						<lfw:tabitem id="advTab" text="高级">
//							<lfw:layout type="flowv">
//								<lfw:layoutPanel>
//									<div id="$d_advancePanel" style="width:100%;"></div>
//								</lfw:layoutPanel>
//								<lfw:layoutPanel>
//									<lfw:tree id="advanceTree" />
//								</lfw:layoutPanel>
//							</lfw:layout>
//						</lfw:tabitem>	
//					</lfw:tab>
//				</lfw:spliterPanelTwo>
//			</lfw:spliter>
//		</lfw:borderPanel>
//						
//		<lfw:borderPanel position="bottom" height="35px">
//			<lfw:flowhLayout>
//				<lfw:flowhPanel>
//				</lfw:flowhPanel>
//				<lfw:flowhPanel width="100px">
//					<lfw:button id="okBt"/>
//				</lfw:flowhPanel>
//				<lfw:flowhPanel width="100px">
//					<lfw:button id="cancelBt"/>
//				</lfw:flowhPanel>
//			</lfw:flowhLayout>
//		</lfw:borderPanel>
//	</lfw:borderLayout>
		um.adjustUI(widgetId);
		return um;
	}

}
