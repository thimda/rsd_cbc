<?xml version="1.0" encoding='UTF-8'?>
<PageMeta caption="流程配置管理" controllerClazz="nc.uap.wfm.flowsetting.WfmFlowCtrl" id="wfm_flowsetting" sourcePackage="wfm/src/client/" windowType="win">
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
        <Widget id="navg" refId="navg">
        </Widget>
        <Widget id="simplequery" refId="../pubview_simplequery">
        </Widget>
        <Widget id="queryplan" refId="../pubview_queryplan">
        </Widget>
        <Widget id="edit_flowtype" refId="edit_flowtype">
        </Widget>
        <Widget id="edit_prodef" refId="edit_prodef">
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
        <Connector id="plugin2" pluginId="flowedit_pluginin" plugoutId="flowmain_pluginout" source="main" target="edit_flowtype">
            <Maps>
                <Map inValue="" outValue="">
                </Map>
            </Maps>
        </Connector>
        <Connector id="plugin1" pluginId="flowmain_pluginin" plugoutId="flownavg_pluginout" source="navg" target="main">
            <Maps>
                <Map inValue="" outValue="">
                </Map>
            </Maps>
        </Connector>
        <Connector id="plugin3" pluginId="prodef_pluginin" plugoutId="prodef_pluginout" source="main" target="edit_prodef">
            <Maps>
                <Map inValue="" outValue="">
                </Map>
            </Maps>
        </Connector>
         <Connector id="plugin4" pluginId="flowmain_pluginin" plugoutId="flowedit_pluginout" source="edit_flowtype" target="main">
            <Maps>
                <Map inValue="" outValue="">
                </Map>
            </Maps>
        </Connector>
        <Connector id="plugin5" pluginId="flowmain_pluginin" plugoutId="prodef_pluginout" source="edit_prodef" target="main">
            <Maps>
                <Map inValue="" outValue="">
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
