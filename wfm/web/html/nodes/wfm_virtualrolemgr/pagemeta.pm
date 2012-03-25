<?xml version="1.0" encoding='UTF-8'?>
<PageMeta caption="虚拟角色" controllerClazz="nc.uap.wfm.virtualrolemgr.WfmVirtRoleMainCtrl" id="wfm_virtualrolemgr" sourcePackage="wfm/src/public/" windowType="win">
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
         <Widget id="simplequery" refId="../pubview_simplequery">
        </Widget>
        <Widget id="queryplan" refId="../pubview_queryplan">
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
		</Event>
	</Events>
    <Connectors>
      	<Connector id="simpleQuery_to_main" pluginId="simpleQuery_plugin" plugoutId="qryout" source="simplequery" target="main">
            <Maps>
                <Map>                    
                    <inValue>row</inValue>
                </Map>
            </Maps>
        </Connector>
    </Connectors>
</PageMeta>
