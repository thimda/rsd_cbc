<?xml version="1.0" encoding='UTF-8'?>
<PageMeta caption="查询初始化" controllerClazz="nc.uap.ctrl.tpl.print.CpPrintInitCtrl" id="cp_printinit" sourcePackage="ctrl/src/public/" windowType="win">
    <Processor>nc.uap.lfw.core.event.AppRequestProcessor</Processor>
    <Widgets>
        <Widget id="main" refId="main">
        </Widget>
        <Widget id="simplequery" refId="../pubview_simplequery">
        </Widget>
        <Widget id="queryplan" refId="../pubview_queryplan">
        </Widget>
        <Widget id="edit" refId="edit">
        </Widget>
        <Widget id="condition" refId="condition">
        </Widget>
        <Widget id="appscategory" refId="appscategory">
        </Widget>
         <Widget id="template_relation" refId="../template_relation">
        </Widget>
    </Widgets>
    <Attributes>
        <Attribute>
            <Key>$MODIFY_TS</Key>
            <Value>1331107116473</Value>
        </Attribute>
    </Attributes>
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
    <Connectors>
        <Connector id="edit_main_rel" pluginId="addPlugin" plugoutId="addPlugout" source="edit" target="main">
        </Connector>
         <Connector id="appscategory_to_main" pluginId="appscategory_plugin" plugoutId="appscategory_plugout" source="appscategory" target="main">
            <Maps>
                <Map>
                    <outValue>afterselected</outValue>
                    <inValue>appscategory_click</inValue>
                </Map>
            </Maps>
        </Connector>
    </Connectors>
</PageMeta>
