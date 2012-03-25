<?xml version="1.0" encoding='UTF-8'?>
<PageMeta caption="印章编辑" controllerClazz="nc.uap.portal.ctrl.office.controller.SignEditControl" id="sign_editor" sourcePackage="ctrl/src/public/" windowType="win">
    <Processor>nc.uap.lfw.core.event.AppRequestProcessor</Processor>
    <Widgets>
        <Widget id="main" refId="main">
        </Widget>
    </Widgets>
    <Attributes>
        <Attribute>
            <Key>$MODIFY_TS</Key>
            <Value>1330498039142</Value>
        </Attribute>
    </Attributes>
    <Events>       
        <Event async="true" jsEventClaszz="nc.uap.lfw.core.event.conf.PageListener" methodName="signafterPageInit" name="afterPageInit" onserver="true">
            <SubmitRule cardSubmit="false" panelSubmit="false" tabSubmit="false">
            </SubmitRule>
            <Params>
                <Param>
                    <Name>pageEvent</Name>
                    <Desc>                        <![CDATA[nc.uap.lfw.core.event.PageEvent]]>
                    </Desc>
                </Param>
            </Params>
            <Action>
            </Action>
        </Event>
        
    </Events>
    <Containers>
    </Containers>
</PageMeta>
