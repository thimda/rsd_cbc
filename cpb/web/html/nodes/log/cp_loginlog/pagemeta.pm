<?xml version="1.0" encoding='UTF-8'?>
<PageMeta caption="登陆日志" controllerClazz="nc.uap.cpb.log.loginlog.LoginLogCtrl" id="cp_loginlog" sourcePackage="cpb/src/public/" windowType="win">
    <Processor>nc.uap.lfw.core.event.AppRequestProcessor</Processor>
    <Widgets>
        <Widget id="main" refId="main">
        </Widget>
        <Widget id="pubview_queryplan" refId="../pubview_queryplan">
        </Widget>
        <Widget id="pubview_simplequery" refId="../pubview_simplequery">
        </Widget>
        <Widget id="showLinkContent" refId="showLinkContent">
        </Widget>
    </Widgets>
    <Attributes>
        <Attribute>
            <Key>$MODIFY_TS</Key>
            <Value>1331195595462</Value>
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
      	<Connector id="simpleQuery_to_main" pluginId="simpleQuery_plugin" plugoutId="qryout" source="pubview_simplequery" target="main">
            <Maps>
                <Map>                    
                    <inValue>row</inValue>
                </Map>
            </Maps>
        </Connector>
    </Connectors>
</PageMeta>
