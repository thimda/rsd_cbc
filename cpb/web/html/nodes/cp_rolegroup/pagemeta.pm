<?xml version="1.0" encoding='UTF-8'?>
<PageMeta caption="协同角色组管理"  controllerClazz="nc.uap.cpb.org.rolegroup.WinController"  id="cp_rolegroup" sourcePackage="cpb/src/public/">
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
        <Widget id="simplequery" refId="../pubview_simplequery">
        </Widget>
        <Widget id="queryplan" refId="../pubview_queryplan">
        </Widget>
        <Widget id="navorg" refId="../pubview_org">
        </Widget>
         <Widget id="manage_type" refId="../manage_type">
        </Widget>
    </Widgets>
    <Containers>
    </Containers>
<Connectors>
        <Connector id="edit_to_main" pluginId="edit_plugin" plugoutId="edit_plugout" source="edit" target="main">
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
