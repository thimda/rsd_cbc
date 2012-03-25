﻿<?xml version="1.0" encoding='UTF-8'?>
<PageMeta caption="协同用户组管理"  controllerClazz="nc.uap.cpb.org.usergroup.WinController"  id="cp_usergroup" sourcePackage="cpb/src/public/" windowType="win">
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
         <Widget id="user" refId="../user">
        </Widget>
        <Widget id="role" refId="../role">
        </Widget>
        <Widget id="simplequery" refId="../pubview_simplequery">
        </Widget>
        <Widget id="queryplan" refId="../pubview_queryplan">
        </Widget>
        <Widget id="navorg" refId="../pubview_org">
        </Widget>
    </Widgets>
    <Menus>
    </Menus>
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
<Connector id="user_to_main" pluginId="user_plugin" plugoutId="user_plugout" source="user" target="main">
            <Maps>
                <Map>
                    <outValue>ok</outValue>
                    <inValue>row</inValue>
                </Map>
            </Maps>
        </Connector>
<Connector id="role_to_main" pluginId="role_plugin" plugoutId="role_plugout" source="role" target="main">
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
