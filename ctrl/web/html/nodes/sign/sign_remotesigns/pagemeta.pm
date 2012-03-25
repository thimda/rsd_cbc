<?xml version="1.0" encoding='UTF-8'?>
<PageMeta caption="服务器印章" controllerClazz="nc.uap.portal.ctrl.office.controller.SignListControl" id="sign_remotesigns" sourcePackage="ctrl/src/public/" windowType="win">
    <Processor>nc.uap.lfw.core.event.AppRequestProcessor</Processor>
    <Widgets>
        <Widget id="main" refId="main">
        </Widget>
        <Widget id="pubview_simplequery" refId="../pubview_simplequery">
        </Widget>
        <Widget id="pubview_queryplan" refId="../pubview_queryplan">
        </Widget>
        <Widget id="user" refId="../user">
        </Widget>        
    </Widgets>
    <Attributes>
        <Attribute>
            <Key>$MODIFY_TS</Key>
            <Value>1330664923148</Value>
        </Attribute>
    </Attributes>
    <Events>       
    </Events>
    <Connectors>
        <Connector id="user_to_main" pluginId="user_plugin" plugoutId="user_plugout" source="user" target="main">
            <Maps>
                <Map inValue="row" outValue="ok">
                    <outValue>ok</outValue>
                    <inValue>row</inValue>
                </Map>
            </Maps>
        </Connector>
        <Connector id="simpleQuery_to_main" pluginId="simpleQuery_plugin" plugoutId="qryout" source="pubview_simplequery" target="main">
            <Maps>
                <Map>                    
                    <inValue>row</inValue>
                </Map>
            </Maps>
        </Connector>
    </Connectors>
</PageMeta>
