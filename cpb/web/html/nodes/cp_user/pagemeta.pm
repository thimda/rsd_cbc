<?xml version="1.0" encoding='UTF-8'?>
<PageMeta caption="协同用户管理" controllerClazz="nc.uap.cpb.org.user.CpbUserWinCtrl" id="cp_user" sourcePackage="cpb/src/public/" windowType="win">
    <Processor>nc.uap.lfw.core.event.AppRequestProcessor</Processor>
    <Widgets>
        <Widget id="main" refId="main">
        </Widget>
        <Widget id="edit" refId="edit">
        </Widget>
        <Widget id="rela" refId="rela">
        </Widget>
        <Widget id="qury" refId="qury">
        </Widget>
        <Widget id="role" refId="../role">
        </Widget>
        <Widget id="enabledate" refId="../enabledate">
        </Widget>
        <Widget id="simplequery" refId="../pubview_simplequery">
        </Widget>
        <Widget id="queryplan" refId="../pubview_queryplan">
        </Widget>
        <Widget id="signpic" refId="signpic">
        </Widget>
    </Widgets>
    <Connectors>
        <Connector id="plugin2" pluginId="usermain_pluginin" plugoutId="useredit_pluginout" source="edit" target="main">
            <Maps>
                <Map inValue="selectedrow" outValue="selectedrow">
                    <outValue>selectedrow</outValue>
                    <inValue>selectedrow</inValue>
                </Map>
            </Maps>
        </Connector>
        <Connector id="plugin1" pluginId="useredit_pluginin" plugoutId="usermain_pluginout" source="main" target="edit">
            <Maps>
                <Map inValue="selectedrow" outValue="selectedrow">
                    <outValue>selectedrow</outValue>
                    <inValue>selectedrow</inValue>
                </Map>
            </Maps>
        </Connector>
        <Connector id="role_to_main" pluginId="role_plugin" plugoutId="role_plugout" source="role" target="main">
            <Maps>
                <Map inValue="selectedrow" outValue="ok">
                    <outValue>ok</outValue>
                    <inValue>selectedrow</inValue>
                </Map>
            </Maps>
        </Connector>
        <Connector id="enabledate_to_main" pluginId="enabledate_plugin" plugoutId="enabledate_plugout" source="enabledate" target="main">
            <Maps>
                <Map inValue="row" outValue="ok">
                    <outValue>ok</outValue>
                    <inValue>row</inValue>
                </Map>
            </Maps>
        </Connector>
        <Connector id="queryplanplugin" pluginId="queryplan_plugin" plugoutId="qryout" source="queryplan" target="main">
            <Maps>
                <Map inValue="whereSql" outValue="whereSql">
                    <outValue>whereSql</outValue>
                    <inValue>whereSql</inValue>
                </Map>
            </Maps>
        </Connector>
        <Connector id="simpleQuery_to_main" pluginId="simpleQuery_plugin" plugoutId="qryout" source="simplequery" target="main">
            <Maps>
                <Map inValue="row" outValue="">
                    <outValue></outValue>
                    <inValue>row</inValue>
                </Map>
            </Maps>
        </Connector>
    </Connectors>
</PageMeta>
