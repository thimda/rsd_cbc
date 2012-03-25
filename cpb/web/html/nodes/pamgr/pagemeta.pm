<?xml version="1.0" encoding='UTF-8'?>
<PageMeta caption="个性化管理" controllerClazz="nc.uap.cpb.pamgr.PaMgrWindowController" id="pamgr" sourcePackage="cpb/src/public/" windowType="win">
    <Processor>nc.uap.lfw.core.event.AppRequestProcessor</Processor>
    <Widgets>
        <Widget id="main" refId="main">
        </Widget>
        <Widget id="leftView" refId="leftView">
        </Widget>
        <Widget id="editView" refId="editView">
        </Widget>
        <Widget id="simplequery" refId="../pubview_simplequery">
        </Widget>
        <Widget id="queryplan" refId="../pubview_queryplan">
        </Widget>
        <Widget id="template_relation" refId="../template_relation">
        </Widget>
    </Widgets>
    <Attributes>
        <Attribute>
            <Key>$MODIFY_TS</Key>
            <Value>1329736365195</Value>
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
        <Connector id="edit2main" pluginId="edit_plugin" plugoutId="edit_plugout" source="editView" target="main">
            <Maps>
                <Map inValue="editRow" outValue="selectRow">
                    <outValue>selectRow</outValue>
                    <inValue>editRow</inValue>
                </Map>
            </Maps>
        </Connector>
        <Connector id="templateToRelation" pluginId="template_plugin" plugoutId="relation_plugout" source="main" target="template_relation">
            <Maps>
                <Map inValue="templateRow" outValue="templateRow">
                    <outValue>templateRow</outValue>
                    <inValue>templateRow</inValue>
                </Map>
            </Maps>
        </Connector>
        <Connector id="nav2main" pluginId="nav_plugin" plugoutId="nav_plugout" source="leftView" target="main">
            <Maps>
                <Map inValue="navRow" outValue="navRow">
                    <outValue>navRow</outValue>
                    <inValue>navRow</inValue>
                </Map>
            </Maps>
        </Connector>
    </Connectors>
</PageMeta>
