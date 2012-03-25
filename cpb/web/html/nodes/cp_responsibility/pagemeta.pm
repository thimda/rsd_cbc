<?xml version="1.0" encoding='UTF-8'?>
<PageMeta caption="协同职责" controllerClazz="nc.uap.cpb.org.responsibility.WinController" id="cp_responsibility" sourcePackage="cpb/src/public/" windowType="win">
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
        <Widget id="edit" refId="edit">
        </Widget>
        <Widget id="relate" refId="relate">
        </Widget>
        <Widget id="simplequery" refId="../pubview_simplequery">
        </Widget>
        <Widget id="queryplan" refId="../pubview_queryplan">
        </Widget>
        <Widget id="navorg" refId="../pubview_org">
        </Widget>
         <Widget id="manage_type" refId="../manage_type">
        </Widget>
    </Widgets>
    <Events>
        <Event async="true" jsEventClaszz="nc.uap.lfw.core.event.conf.PageListener" methodName="sysWindowClosed" name="onClosed" onserver="true">
            <SubmitRule cardSubmit="false" panelSubmit="false" tabSubmit="false">
            </SubmitRule>
            <Params>
                <Param>
                    <Name>event</Name>
                    <Value>
                    </Value>
                    <Desc>                        <![CDATA[nc.uap.lfw.core.event.PageEvent]]>
                    </Desc>
                </Param>
            </Params>
            <Action>
            </Action>
        </Event>
    </Events>
    <Containers>
    </Containers>
    <Connectors>
        <Connector id="edit_to_add" pluginId="afteradd" plugoutId="afteredit" source="edit" target="main">
            <Maps>
                <Map>
                    <outValue>ok</outValue>
                    <inValue>row</inValue>
                </Map>
            </Maps>
        </Connector>
<Connector id="manage_type_to_main" pluginId="manage_type_plugin" plugoutId="manage_type_plugout" source="manage_type" target="main">
            <Maps>
                <Map>
                    <outValue>afterselected</outValue>
                    <inValue>manage_type_row</inValue>
                </Map>
            </Maps>
        </Connector>
        <Connector id="simpleQuery_to_main" pluginId="simpleQuery_plugin" plugoutId="qryout" source="simplequery" target="main">
            <Maps>
                <Map>                    
                    <inValue>row</inValue>
                </Map>
            </Maps>
        </Connector>
    </Connectors>
</PageMeta>
