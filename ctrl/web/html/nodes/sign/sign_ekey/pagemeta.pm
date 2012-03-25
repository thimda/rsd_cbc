<?xml version="1.0" encoding='UTF-8'?>
<PageMeta caption="加密狗管理" controllerClazz="nc.uap.portal.ctrl.office.controller.EKeyControl" id="sign_ekey" sourcePackage="ctrl/src/public/" windowType="win">
    <Processor>nc.uap.lfw.core.event.AppRequestProcessor</Processor>
    <Widgets>
        <Widget id="main" refId="main">
        </Widget>
        <Widget id="pubview_simplequery" refId="../pubview_simplequery">
        </Widget>
        <Widget id="pubview_queryplan" refId="../pubview_queryplan">
        </Widget>
        <Widget id="edit" refId="edit">
        </Widget>
    </Widgets>
    <Events>
        
    </Events>
    <Connectors>
        <Connector id="plugin2" pluginId="signedit_pluginin" plugoutId="signedit_pluginout" source="edit" target="main">
            <Maps>
                <Map>
                    <outValue>selectedrow</outValue>
                    <inValue>selectedrow</inValue>
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
