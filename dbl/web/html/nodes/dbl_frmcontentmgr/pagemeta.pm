<?xml version="1.0" encoding='UTF-8'?>
<PageMeta caption="表单内容管理">
    <Processor>nc.uap.lfw.core.processor.EventRequestProcessor</Processor>
    <PageStates currentState="2">
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
    </Widgets>
    <Listeners>
        <PageListener id="formcate_defaultListener" serverClazz="nc.uap.dbl.frmcontentmgr.FrmcontentmgrPageInitListener">
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
<Menus>
 <MenuBarComp id="dmenu">
            <MenuItem displayHotKey="CTRL+SHIFT+" id="add" modifiers="3" operatorStatusArray="0,1,2" text="新增">
                <Listeners>
                    <MouseListener id="addLis" serverClazz="nc.uap.dbl.frmcontentmgr.FrmDefMgrListener">
                        <Events>
                            <Event async="true" name="onclick" onserver="true">
                                <SubmitRule cardSubmit="false" panelSubmit="false" tabSubmit="false">
                                    <Widget cardSubmit="false" id="main" panelSubmit="false" tabSubmit="false">
                                        <Dataset id="ds_frmdef" type="ds_current_line">
                                        </Dataset>
                                        <Dataset id="ds_frmtmp" type="ds_current_line">
                                        </Dataset>
                                        <Dataset id="ds_group" type="ds_current_line">
                                        </Dataset>
                                        <Dataset id="ds_frmcat" type="ds_current_line">
                                        </Dataset>
                                    </Widget>
                                </SubmitRule>
                                <Params>
                                    <Param>
                                        <Name>mouseEvent</Name>
                                        <Value>
                                        </Value>
                                        <Desc>                                            <![CDATA[]]>
                                        </Desc>
                                    </Param>
                                </Params>
                                <Action>
                                </Action>
                            </Event>
                        </Events>
                    </MouseListener>
                </Listeners>
            </MenuItem>
            <MenuItem displayHotKey="CTRL+SHIFT+" id="edit" modifiers="3" operatorStatusArray="1" text="修改">
                <Listeners>
                    <MouseListener id="edit" serverClazz="nc.uap.dbl.frmcontentmgr.FrmDefMgrListener">
                        <Events>
                            <Event async="true" name="onclick" onserver="true">
                                <SubmitRule cardSubmit="false" panelSubmit="false" tabSubmit="false">
                                    <Widget cardSubmit="false" id="main" panelSubmit="false" tabSubmit="false">
                                        <Dataset id="ds_frmdef" type="ds_current_line">
                                        </Dataset>
                                        <Dataset id="ds_frmtmp" type="ds_current_line">
                                        </Dataset>
                                        <Dataset id="ds_group" type="ds_current_line">
                                        </Dataset>
                                        <Dataset id="ds_frmcat" type="ds_current_line">
                                        </Dataset>
                                    </Widget>
                                </SubmitRule>
                                <Params>
                                    <Param>
                                        <Name>mouseEvent</Name>
                                        <Value>
                                        </Value>
                                        <Desc>                                            <![CDATA[]]>
                                        </Desc>
                                    </Param>
                                </Params>
                                <Action>
                                </Action>
                            </Event>
                        </Events>
                    </MouseListener>
                </Listeners>
            </MenuItem>
            <MenuItem displayHotKey="CTRL+SHIFT+" id="save" modifiers="3" operatorStatusArray="3,4" text="保存">
                <Listeners>
                    <MouseListener id="saveLis" serverClazz="nc.uap.dbl.frmcontentmgr.FrmDefMgrListener">
                        <Events>
                            <Event async="true" name="onclick" onserver="true">
                                <SubmitRule cardSubmit="false" panelSubmit="false" tabSubmit="false">
                                    <Widget cardSubmit="false" id="main" panelSubmit="false" tabSubmit="false">
                                        <Dataset id="ds_frmdef" type="ds_current_line">
                                        </Dataset>
                                        <Dataset id="ds_frmtmp" type="ds_current_line">
                                        </Dataset>
                                        <Dataset id="ds_group" type="ds_current_line">
                                        </Dataset>
                                        <Dataset id="ds_frmcat" type="ds_current_line">
                                        </Dataset>
                                    </Widget>
                                </SubmitRule>
                                <Params>
                                    <Param>
                                        <Name>mouseEvent</Name>
                                        <Value>
                                        </Value>
                                        <Desc>                                            <![CDATA[]]>
                                        </Desc>
                                    </Param>
                                </Params>
                                <Action>
                                </Action>
                            </Event>
                        </Events>
                    </MouseListener>
                </Listeners>
            </MenuItem>
            <MenuItem displayHotKey="CTRL+SHIFT+" id="del" modifiers="3" operatorStatusArray="1,2" text="删除">
                <Listeners>
                    <MouseListener id="delLis" serverClazz="nc.uap.dbl.frmcontentmgr.FrmDefMgrListener">
                        <Events>
                            <Event async="true" name="onclick" onserver="true">
                                <SubmitRule cardSubmit="false" panelSubmit="false" tabSubmit="false">
                                    <Widget cardSubmit="false" id="main" panelSubmit="false" tabSubmit="false">
                                        <Dataset id="ds_frmdef" type="ds_current_line">
                                        </Dataset>
                                        <Dataset id="ds_frmtmp" type="ds_current_line">
                                        </Dataset>
                                        <Dataset id="ds_group" type="ds_current_line">
                                        </Dataset>
                                        <Dataset id="ds_frmcat" type="ds_current_line">
                                        </Dataset>
                                    </Widget>
                                </SubmitRule>
                                <Params>
                                    <Param>
                                        <Name>mouseEvent</Name>
                                        <Value>
                                        </Value>
                                        <Desc>                                            <![CDATA[]]>
                                        </Desc>
                                    </Param>
                                </Params>
                                <Action>
                                </Action>
                            </Event>
                        </Events>
                    </MouseListener>
                </Listeners>
            </MenuItem>
            <MenuItem displayHotKey="CTRL+SHIFT+" id="cancel" modifiers="3" operatorStatusArray="3,4" text="取消">
                <Listeners>
                    <MouseListener id="calcelLis" serverClazz="nc.uap.dbl.frmcontentmgr.FrmDefMgrListener">
                        <Events>
                            <Event async="true" name="onclick" onserver="true">
                                <SubmitRule cardSubmit="false" panelSubmit="false" tabSubmit="false">
                                    <Widget cardSubmit="false" id="main" panelSubmit="false" tabSubmit="false">
                                        <Dataset id="ds_frmdef" type="ds_all_line">
                                        </Dataset>
                                        <Dataset id="ds_frmtmp" type="ds_current_line">
                                        </Dataset>
                                        <Dataset id="ds_group" type="ds_current_line">
                                        </Dataset>
                                        <Dataset id="ds_frmcat" type="ds_current_line">
                                        </Dataset>
                                    </Widget>
                                </SubmitRule>
                                <Params>
                                    <Param>
                                        <Name>mouseEvent</Name>
                                        <Value>
                                        </Value>
                                        <Desc>                                            <![CDATA[]]>
                                        </Desc>
                                    </Param>
                                </Params>
                                <Action>
                                </Action>
                            </Event>
                        </Events>
                    </MouseListener>
                </Listeners>
            </MenuItem>
        </MenuBarComp>
</Menus>
    <Containers>
    </Containers>
</PageMeta>
