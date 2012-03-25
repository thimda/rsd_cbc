<?xml version="1.0" encoding='UTF-8'?>
<PageMeta caption="流程回退步" controllerClazz="nc.uap.wfm.reject.WfmRejectCtrl" id="wfm_workflowreject" sourcePackage="wfm/src/public/">
	<Processor>nc.uap.lfw.core.event.AppRequestProcessor</Processor>
	<PageStates currentState="1">
		<PageState>
			<Key>1</Key>
			<Name>卡片显示</Name>
		</PageState>
		<PageState>
			<Key>2</Key>
			<Name>列表显示</Name>
		</PageState>
	</PageStates>
	<Widgets>
		<Widget id="main" refId="main">
		</Widget>
		<Widget id="assign" refId="assign">
		</Widget>
	</Widgets>
	<Events>
		<Event async="true" jsEventClaszz="nc.uap.lfw.core.event.conf.PageListener" methodName="sysWindowClosed" name="onClosed" onserver="true">
			<SubmitRule cardSubmit="false" panelSubmit="false" tabSubmit="false">
			</SubmitRule>
			<Params>
				<Param>
					<Name>event</Name>
					<Value></Value>
					<Desc>                        <![CDATA[]]>
					</Desc>
				</Param>
			</Params>
			<Action>
			</Action>
		</Event>
		<Event async="true" name="afterPageInit" onserver="false">
			<Params>
				<Param>
					<Name>
					</Name>
					<Value>
					</Value>
					<Desc>                                <![CDATA[]]>
					</Desc>
				</Param>
			</Params>
			<Action>               
			</Action>
		</Event>
	</Events>
	<Containers>
	</Containers>
</PageMeta>
