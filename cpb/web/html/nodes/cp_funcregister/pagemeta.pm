<?xml version="1.0" encoding='UTF-8'?>
<PageMeta caption="功能节点注册"  controllerClazz="nc.uap.cpb.org.funcregister.WinController"  id="cp_funcregister" sourcePackage="cpb/src/public/">
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
         <Widget id="appscategory" refId="appscategory">
        </Widget>
        <Widget id="edit_category" refId="edit_category">
        </Widget>
         <Widget id="edit_node" refId="edit_node">
        </Widget>
        <Widget id="simplequery" refId="../pubview_simplequery">
        </Widget>
        <Widget id="queryplan" refId="../pubview_queryplan">
        </Widget>
    </Widgets>   
    <Containers>
    </Containers>
    <Connectors>
        <Connector id="edit_node_to_main" pluginId="edit_node_plugin" plugoutId="edit_node_plugout" source="edit_node" target="main">
            <Maps>
                <Map>
                    <outValue>ok</outValue>
                    <inValue>row</inValue>
                </Map>
            </Maps>
        </Connector>
        <Connector id="edit_category_to_main" pluginId="edit_category_plugin" plugoutId="edit_category_plugout" source="edit_category" target="appscategory">
            <Maps>
                <Map>
                    <outValue>ok</outValue>
                    <inValue>row</inValue>
                </Map>
            </Maps>
        </Connector>
        <Connector id="appscategory_to_main" pluginId="appscategory_plugin" plugoutId="appscategory_plugout" source="appscategory" target="main">
            <Maps>
                <Map>
                    <outValue>afterselected</outValue>
                    <inValue>appscategory_click</inValue>
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
