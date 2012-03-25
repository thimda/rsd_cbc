<?xml version="1.0" encoding='UTF-8'?>
<PageMeta caption="编码规则管理" controllerClazz="nc.uap.wfm.frmbillnummgr.WfmFrmNumCtrl" id="wfm_flowsetting" sourcePackage="wfm/src/public/" windowType="win">
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
        <Widget id="code" refId="code">
        </Widget>
         <Widget id="const" refId="const">
        </Widget>
        <Widget id="date" refId="date">
        </Widget>
    </Widgets>
    <Containers>
    </Containers>
     <Connectors>
        <Connector id="plugin1" pluginId="code_pluginin1" plugoutId="main_pluginout1" source="main" target="code">
            <Maps>
                <Map inValue="" outValue="">
                </Map>
            </Maps>
        </Connector>
         <Connector id="plugin2" pluginId="main_pluginin1" plugoutId="code_pluginout1" source="code" target="main">
            <Maps>
                <Map inValue="" outValue="">
                </Map>
            </Maps>
        </Connector>
        
        
         <Connector id="plugin3" pluginId="const_pluginin1" plugoutId="main_pluginout1" source="main" target="const">
            <Maps>
                <Map inValue="" outValue="">
                </Map>
            </Maps>
        </Connector>
         <Connector id="plugin4" pluginId="main_pluginin1" plugoutId="const_pluginout1" source="const" target="main">
            <Maps>
                <Map inValue="" outValue="">
                </Map>
            </Maps>
        </Connector>
        
        
          <Connector id="plugin5" pluginId="date_pluginin1" plugoutId="main_pluginout1" source="main" target="date">
            <Maps>
                <Map inValue="" outValue="">
                </Map>
            </Maps>
        </Connector>
         <Connector id="plugin6" pluginId="main_pluginin1" plugoutId="date_pluginout1" source="date" target="main">
            <Maps>
                <Map inValue="" outValue="">
                </Map>
            </Maps>
        </Connector>
    </Connectors>
</PageMeta>
