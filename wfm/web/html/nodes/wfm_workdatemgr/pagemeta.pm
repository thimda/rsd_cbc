<?xml version="1.0" encoding='UTF-8'?>
<PageMeta caption="工作日管理" id="wfm_workdatemgr">
    <Processor>nc.uap.lfw.core.processor.EventRequestProcessor</Processor>
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
        <Widget id="xd" refId="xd">
        </Widget>
        <Widget id="gd" refId="gd">
        </Widget>
        <Widget id="wd" refId="wd">
        </Widget>
    </Widgets>
    <Listeners>
        <PageListener id="workdate_defaultListener" serverClazz="nc.uap.lfw.core.event.deft.DefaultPageServerListener">
            <Events>
                <Event async="false" name="onClosed" onserver="true">
                    <SubmitRule cardSubmit="false" panelSubmit="false" tabSubmit="false">
                    </SubmitRule>
                    <Params>
                        <Param>
                            <Name>
                            </Name>
                            <Value>
                            </Value>
                            <Desc>                                <![CDATA[]]>
                            </Desc>
                        </Param>
                    </Params>
                    <Action>
                    </Action>
                </Event>
                <Event async="true" name="afterPageInit" onserver="true">
                    <SubmitRule cardSubmit="false" panelSubmit="false" tabSubmit="false">
                        <Widget cardSubmit="false" id="wd" panelSubmit="false" tabSubmit="false">
                        </Widget>
                    </SubmitRule>
                    <Params>
                        <Param>
                            <Name>
                            </Name>
                            <Value>
                            </Value>
                            <Desc>                                <![CDATA[]]>
                            </Desc>
                        </Param>
                    </Params>
                    <Action>
                    </Action>
                </Event>
                <Event async="false" name="onClosing" onserver="false">
                    <SubmitRule cardSubmit="false" panelSubmit="false" tabSubmit="false">
                    </SubmitRule>
                    <Params>
                        <Param>
                            <Name>
                            </Name>
                            <Value>
                            </Value>
                            <Desc>                                <![CDATA[]]>
                            </Desc>
                        </Param>
                    </Params>
                    <Action>                        <![CDATA[pageUI.showCloseConfirm();]]>
                    </Action>
                </Event>
            </Events>
        </PageListener>
    </Listeners>
    <Containers>
        <TabComp id="tab1">
            <Listeners>
                <TabListener id="t1listener" serverClazz="nc.uap.wfm.workdatemgr.DayTabListener">
                    <Events>
                        <Event async="true" name="afterItemInit" onserver="true">
                            <SubmitRule cardSubmit="false" panelSubmit="false" tabSubmit="true">
                                <Widget cardSubmit="true" id="wd" panelSubmit="true" tabSubmit="true">
                                </Widget>
                            </SubmitRule>
                            <Params>
                                <Param>
                                    <Name>tabItemEvent</Name>
                                    <Value>
                                    </Value>
                                    <Desc>                                        <![CDATA[]]>
                                    </Desc>
                                </Param>
                            </Params>
                            <Action>
                            </Action>
                        </Event>
                    </Events>
                </TabListener>
            </Listeners>
        </TabComp>
    </Containers>
</PageMeta>
