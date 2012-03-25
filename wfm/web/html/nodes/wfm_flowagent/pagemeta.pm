<?xml version="1.0" encoding='UTF-8'?>
<PageMeta caption="流程代理" controllerClazz="nc.uap.wfm.flowagent.FlowAgentCtrl" id="wfm_flowagent" sourcePackage="wfm/src/public/" windowType="win">
    <Processor>nc.uap.lfw.core.event.AppRequestProcessor</Processor>
    <Widgets>
        <Widget id="main" refId="main">
        </Widget>
        <Widget id="pubview_queryplan" refId="../pubview_queryplan">
        </Widget>
        <Widget id="pubview_simplequery" refId="../pubview_simplequery">
        </Widget>
        <Widget id="pubview_flowtype" refId="../pubview_flowtype">
        </Widget>
        <Widget id="edit" refId="edit">
        </Widget>
    </Widgets>
    <Attributes>
        <Attribute>
            <Key>$MODIFY_TS</Key>
            <Value>1330934339000</Value>
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
        <Connector id="main_to_edit" pluginId="eidt_in_flowagent" plugoutId="main_out_flowagent" source="main" target="edit">
        </Connector>
        <Connector id="edit_to_main" pluginId="main_in_flowagent" plugoutId="edit_out_flowagent" source="edit" target="main">
        </Connector>
        <Connector id="out_in_1" pluginId="main_in_flowagent" plugoutId="out_flowtype" source="pubview_flowtype" target="main">
        </Connector>
    </Connectors>
</PageMeta>
