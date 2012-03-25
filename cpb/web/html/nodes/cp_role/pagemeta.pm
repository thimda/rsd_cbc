<?xml version="1.0" encoding='UTF-8'?>
<PageMeta caption="协同角色管理" controllerClazz="nc.uap.cpb.org.role.WinController" id="cp_role" sourcePackage="cpb/src/public/" windowType="win">
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
        <Widget id="qury" refId="qury">
        </Widget>
        <Widget id="relateresp" refId="relateresp">
        </Widget>
        <Widget id="relateorg" refId="../relateorg">
        </Widget>
        <Widget id="user" refId="../user">
        </Widget>
        <Widget id="simplequery" refId="../pubview_simplequery">
        </Widget>
        <Widget id="queryplan" refId="../pubview_queryplan">
        </Widget>
        <Widget id="navorg" refId="../pubview_org">
        </Widget>
       <Widget id="manage_type" refId="../manage_type">
        </Widget>
     <Widget id="enabledate" refId="../enabledate">
        </Widget>
    </Widgets>
    <Containers>
    </Containers>
    <Connectors>
        <Connector id="resp_to_main" pluginId="resp_plugin" plugoutId="resp_plugout" source="relateresp" target="main">
            <Maps>
                <Map>
                    <outValue>ok</outValue>
                    <inValue>row</inValue>
                </Map>
            </Maps>
        </Connector>
        <Connector id="edit_to_add" pluginId="edit_plugin" plugoutId="edit_plugout" source="edit" target="main">
            <Maps>
                <Map>
                    <outValue>ok</outValue>
                    <inValue>row</inValue>
                </Map>
            </Maps>
        </Connector>
        <Connector id="org_to_main" pluginId="org_plugin" plugoutId="org_plugout" source="relateorg" target="main">
            <Maps>
                <Map>
                    <outValue>ok</outValue>
                    <inValue>row</inValue>
                </Map>
            </Maps>
        </Connector>
		<Connector id="user_to_main" pluginId="user_plugin" plugoutId="user_plugout" source="user" target="main">
            <Maps>
                <Map>
                    <outValue>ok</outValue>
                    <inValue>row</inValue>
                </Map>
            </Maps>
        </Connector>
        <Connector id="enabledate_to_main" pluginId="enabledate_plugin" plugoutId="enabledate_plugout" source="enabledate" target="main">
            <Maps>
                <Map>
                    <outValue>ok</outValue>
                    <inValue>row</inValue>
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
