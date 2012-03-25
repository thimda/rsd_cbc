<?xml version="1.0" encoding='UTF-8'?>
<PageMeta caption="paswordssecurity">
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
    <Menus>
        <MenuBarComp id="menu">
            <MenuItem id="add" operatorStatusArray="0,1,2" text="新建">
                <Listeners>
                    <MouseListener id="menu_item_add_listener" serverClazz="nc.uap.cpb.org.pwdsgy.PwdsecUifAddMouseListener">
                        <Events>
                            <Event async="true" name="onclick" onserver="true">
                                <SubmitRule cardSubmit="false" tabSubmit="false">
                                    <Widget cardSubmit="true" id="main" tabSubmit="false">
                                        <Dataset id="ds_pwdsgy" type="ds_current_page">
                                        </Dataset>
                                    </Widget>
                                </SubmitRule>
                                <Params>
                                    <Param>
                                        <Name>mouseEvent</Name>
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
            <MenuItem id="delete" operatorStatusArray="1,2" text="删除">
                <Listeners>
                    <MouseListener id="menu_item_delete_listener" serverClazz="nc.uap.cpb.org.pwdsgy.PwdsecUifDelMouseListener">
                        <Events>
                            <Event async="true" name="onclick" onserver="true">
                                <SubmitRule cardSubmit="true" tabSubmit="false">
                                    <Widget cardSubmit="false" id="main" tabSubmit="false">
                                        <Dataset id="ds_pwdsgy" type="ds_current_page">
                                        </Dataset>
                                        </Widget>
                                </SubmitRule>
                                <Params>
                                    <Param>
                                        <Name>mouseEvent</Name>
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
            <MenuItem id="edit" operatorStatusArray="1" text="修改">
                <Listeners>
                    <MouseListener id="menu_item_edit_listener" serverClazz="nc.uap.cpb.org.pwdsgy.PwdsecUifEditMouseListener">
                        <Events>
                            <Event async="true" name="onclick" onserver="true">
                                <SubmitRule cardSubmit="false" tabSubmit="false">
                                    <Widget cardSubmit="false" id="main" tabSubmit="false">
                                        <Dataset id="ds_pwdsgy" type="ds_current_line">
                                        </Dataset>
                                    </Widget>
                                </SubmitRule>
                                <Params>
                                    <Param>
                                        <Name>mouseEvent</Name>
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

            <MenuItem id="save" operatorStatusArray="4,3" text="保存">
                <Listeners>
                    <MouseListener id="menu_item_save_listener" serverClazz="nc.uap.cpb.org.pwdsgy.PwdsecUifSaveMouseListener">
                        <Events>
                            <Event async="true" name="onclick" onserver="true">
                                <SubmitRule cardSubmit="false" tabSubmit="false">
                                    <Widget cardSubmit="false" id="main" tabSubmit="false">
                                        <Dataset id="ds_pwdsgy" type="ds_all_line">
                                        </Dataset>
                                    </Widget>
                                </SubmitRule>
                                <Params>
                                    <Param>
                                        <Name>mouseEvent</Name>
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
            <MenuItem id="cancel" modifiers="2" operatorStatusArray="3,4" text="取消">
                <Listeners>
                    <MouseListener id="menu_item_cancel_listener" serverClazz="nc.uap.cpb.org.pwdsgy.PwdsecUifCancelMouseListener">
                        <Events>
                            <Event async="true" name="onclick" onserver="true">
                                <SubmitRule cardSubmit="false" panelSubmit="false" tabSubmit="false">
                                    <Widget cardSubmit="false" id="main" panelSubmit="false" tabSubmit="false">
                                        <Dataset id="ds_pwdsgy" type="ds_current_page">
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
