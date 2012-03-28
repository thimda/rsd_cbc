<?xml version="1.0" encoding='UTF-8'?>
<PageMeta caption="流程监控" controllerClazz="nc.uap.wfm.flowmonitor.FlowMonitorCtrl" id="wfm_flowmonitor" sourcePackage="wfm/src/public/" windowType="win">
    <Processor>nc.uap.lfw.core.event.AppRequestProcessor</Processor>
    <Widgets>
        <Widget id="pubview_flowtype" refId="../pubview_flowtype">
        </Widget>
        <Widget id="simplequery" refId="../pubview_simplequery">
        </Widget>
        <Widget id="queryplan" refId="../pubview_queryplan">
        </Widget>
        <Widget id="main" refId="main">
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
                    <Desc>                        <![CDATA[nc.uap.lfw.core.event.PageEvent]]>
                    </Desc>
                </Param>
            </Params>
            <Action>
            </Action>
        </Event>
    </Events>
     <Connectors>
        <Connector id="flowtype_main" pluginId="plugin_flowtype2main" plugoutId="out_flowtype" source="pubview_flowtype" target="main">
        </Connector>
    </Connectors>
</PageMeta>
