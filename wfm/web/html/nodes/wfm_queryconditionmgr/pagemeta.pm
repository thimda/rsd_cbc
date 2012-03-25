<?xml version="1.0" encoding='UTF-8'?>
<PageMeta caption="模板明细管理">
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
        <Widget id="main" refId="main">
        </Widget>
    </Widgets>
    <Listeners>
        <PageListener id="templatedetailmgr_defaultListener" serverClazz="nc.uap.lfw.core.event.deft.DefaultPageServerListener">
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
        <MenuBarComp id="menu_querycondition">
            <MenuItem id="add" modifiers="3" operatorStatusArray="0,1,2" text="新建">
                <Listeners>
                    <MouseListener id="menu_item_add_listener" serverClazz="nc.uap.wfm.querycondition.QueryConditionUifAddMouseListener">
                        <Events>
                            <Event async="true" name="onclick" onserver="true">
                                <SubmitRule cardSubmit="false" panelSubmit="false" tabSubmit="false">
                                    <Widget cardSubmit="true" id="main" panelSubmit="false" tabSubmit="false">
                                        <Dataset id="ds_querycondition" type="ds_current_page">
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
            <MenuItem id="delete" modifiers="3" operatorStatusArray="1,2" text="删除">
                <Listeners>
                    <MouseListener id="menu_item_delete_listener" serverClazz="nc.uap.wfm.querycondition.QueryConditionUifDelMouseListener">
                        <Events>
                            <Event async="true" name="onclick" onserver="true">
                                <SubmitRule cardSubmit="false" panelSubmit="false" tabSubmit="false">
                                    <Widget cardSubmit="false" id="main" panelSubmit="false" tabSubmit="false">
                                        <Dataset id="ds_formitem" type="ds_all_line">
                                        </Dataset>
                                        <Dataset id="ds_querycondition" type="ds_all_line">
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
            <MenuItem id="edit" modifiers="3" operatorStatusArray="1" text="修改">
                <Listeners>
                    <MouseListener id="menu_item_edit_listener" serverClazz="nc.uap.wfm.querycondition.QueryConditionUifEditMouseListener">
                        <Events>
                            <Event async="true" name="onclick" onserver="true">
                                <SubmitRule cardSubmit="false" panelSubmit="false" tabSubmit="false">
                                    <Widget cardSubmit="true" id="main" panelSubmit="false" tabSubmit="false">
                                        <Dataset id="ds_querycondition" type="ds_current_page">
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
            <MenuItem id="cancel" modifiers="3" operatorStatusArray="3,4" text="取消">
                <Listeners>
                    <MouseListener id="menu_item_cancel_listener" serverClazz="nc.uap.wfm.querycondition.QueryConditionUifCancelMouseListener">
                        <Events>
                            <Event async="true" name="onclick" onserver="true">
                                <SubmitRule cardSubmit="false" panelSubmit="false" tabSubmit="false">
                                    <Widget cardSubmit="false" id="main" panelSubmit="false" tabSubmit="false">
                                        <Dataset id="ds_querycondition" type="ds_current_page">
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
            <MenuItem id="save" modifiers="3" operatorStatusArray="4,3" text="保存">
                <Listeners>
                    <MouseListener id="menu_item_save_listener" serverClazz="nc.uap.wfm.querycondition.QueryConditionUifSaveMouseListener">
                        <Events>
                            <Event async="true" name="onclick" onserver="true">
                                <SubmitRule cardSubmit="false" panelSubmit="false" tabSubmit="false">
                                    <Widget cardSubmit="false" id="main" panelSubmit="false" tabSubmit="false">
                                        <Dataset id="ds_formitem" type="ds_all_line">
                                        </Dataset>
                                        <Dataset id="ds_querycondition" type="ds_all_line">
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
