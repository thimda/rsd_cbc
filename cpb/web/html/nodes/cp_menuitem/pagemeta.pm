<?xml version="1.0" encoding='UTF-8'?>
<PageMeta caption="菜单项管理" controllerClazz="nc.uap.cpb.org.menuitem.WinController" id="cp_menuitem" sourcePackage="cpb/src/public/">
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
        <Widget id="menucategory" refId="menucategory">
        </Widget>
        <Widget id="simplequery" refId="../pubview_simplequery">
        </Widget>
        <Widget id="queryplan" refId="../pubview_queryplan">
        </Widget>
    </Widgets>
    <Containers>
    </Containers>
    <Connectors>
        <Connector id="menucategory_to_main" pluginId="menucategory_plugin" plugoutId="menucategory_plugout" source="menucategory" target="main">
            <Maps>
                <Map>
                    <outValue>afterselected</outValue>
                    <inValue>menucategory_click</inValue>
                </Map>
            </Maps>
        </Connector>
        <Connector id="edit_to_main" pluginId="edit_plugin" plugoutId="edit_plugout" source="edit" target="main">
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
