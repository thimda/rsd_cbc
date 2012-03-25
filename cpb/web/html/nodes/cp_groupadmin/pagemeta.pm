<?xml version="1.0" encoding='UTF-8'?>
<PageMeta caption="集团管理员管理">
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
        <Widget id="group" refId="group">
        </Widget>
        <Widget id="relateRole" refId="relateRole">
        </Widget>
    </Widgets>
    <Menus>
        <MenuBarComp id="menu_card">
            <MenuItem id="add" modifiers="2" operatorStatusArray="0,1,2" text="新建">
                <Listeners>
                    <MouseListener id="menu_item_add_listener" serverClazz="nc.uap.cpb.org.groupadmin.GroupAdminUifAddMouseListener">
                        <Events>
                            <Event async="true" name="onclick" onserver="true">
                                <SubmitRule cardSubmit="false" panelSubmit="false" tabSubmit="false">
                                    <Widget cardSubmit="true" id="main" panelSubmit="false" tabSubmit="false">
                                        <Dataset id="userds" type="ds_current_page">
                                        </Dataset>
                                    </Widget>
                                </SubmitRule>
                                <Params>
                                    <Param>
                                        <Name>mouseEvent</Name>
                                        <Value></Value>
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
            <MenuItem id="delete" modifiers="2" operatorStatusArray="1,2" text="删除">
                <Listeners>
                    <MouseListener id="menu_item_delete_listener" serverClazz="nc.uap.cpb.org.groupadmin.GroupAdminUifDelMouseListener">
                        <Events>
                            <Event async="true" name="onclick" onserver="true">
                                <SubmitRule cardSubmit="false" panelSubmit="false" tabSubmit="false">
                                    <Widget cardSubmit="false" id="main" panelSubmit="false" tabSubmit="false">
                                        <Dataset id="groupds" type="ds_all_line">
                                        </Dataset>
                                        <Dataset id="userds" type="ds_all_line">
                                        </Dataset>
                                    </Widget>
                                </SubmitRule>
                                <Params>
                                    <Param>
                                        <Name>mouseEvent</Name>
                                        <Value></Value>
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
            <MenuItem id="edit" modifiers="2" operatorStatusArray="1" text="修改">
                <Listeners>
                    <MouseListener id="menu_item_edit_listener" serverClazz="nc.uap.cpb.org.groupadmin.GroupAdminUifEditMouseListener">
                        <Events>
                            <Event async="true" name="onclick" onserver="true">
                                <SubmitRule cardSubmit="false" panelSubmit="false" tabSubmit="false">
                                    <Widget cardSubmit="true" id="main" panelSubmit="false" tabSubmit="false">
                                        <Dataset id="userds" type="ds_current_page">
                                        </Dataset>
                                    </Widget>
                                </SubmitRule>
                                <Params>
                                    <Param>
                                        <Name>mouseEvent</Name>
                                        <Value></Value>
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
            <MenuItem id="save" modifiers="2" operatorStatusArray="4,3" text="保存">
                <Listeners>
                    <MouseListener id="menu_item_save_listener" serverClazz="nc.uap.cpb.org.groupadmin.GroupAdminUifSaveMouseListener">
                        <Events>
                            <Event async="true" name="onclick" onserver="true">
                                <SubmitRule cardSubmit="false" panelSubmit="false" tabSubmit="false">
                                    <Widget cardSubmit="false" id="main" panelSubmit="false" tabSubmit="false">
                                        <Dataset id="groupds" type="ds_all_line">
                                        </Dataset>
                                        <Dataset id="userds" type="ds_all_line">
                                        </Dataset>
                                    </Widget>
                                </SubmitRule>
                                <Params>
                                    <Param>
                                        <Name>mouseEvent</Name>
                                        <Value></Value>
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
                    <MouseListener id="menu_item_cancel_listener" serverClazz="nc.uap.cpb.org.groupadmin.GroupAdminUifCancelMouseListener">
                        <Events>
                            <Event async="true" name="onclick" onserver="true">
                                <SubmitRule cardSubmit="false" panelSubmit="false" tabSubmit="false">
                                    <Widget cardSubmit="false" id="main" panelSubmit="false" tabSubmit="false">
                                        <Dataset id="userds" type="ds_current_page">
                                        </Dataset>
                                    </Widget>
                                </SubmitRule>
                                <Params>
                                    <Param>
                                        <Name>mouseEvent</Name>
                                        <Value></Value>
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
            <MenuItem id="authorize" modifiers="2" operatorStatusArray="1" text="授权集团">
                <Listeners>
                    <MouseListener id="menu_item_authorize_listener" serverClazz="nc.uap.cpb.org.groupadmin.GroupAdminUifAuthorizeGroupMouseListener">
                        <Events>
                            <Event async="true" name="onclick" onserver="true">
                                <SubmitRule cardSubmit="false" panelSubmit="false" tabSubmit="false">
                                </SubmitRule>
                                <Params>
                                    <Param>
                                        <Name>mouseEvent</Name>
                                        <Value></Value>
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
            <MenuItem id="relaterole" modifiers="2" operatorStatusArray="1" text="关联角色">
                <Listeners>
                    <MouseListener id="menu_item_relaterole_listener" serverClazz="nc.uap.cpb.org.groupadmin.GroupAdminRelateRoleMouseListener">
                        <Events>
                            <Event async="true" name="onclick" onserver="true">
                                <SubmitRule cardSubmit="false" panelSubmit="false" tabSubmit="false">
                                </SubmitRule>
                                <Params>
                                    <Param>
                                        <Name>mouseEvent</Name>
                                        <Value></Value>
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
            <MenuItem displayHotKey="CTRL+SHIFT+" id="card" modifiers="3" operatorStatusArray="1" text="卡片显示">
                <Listeners>
                    <MouseListener id="menu_item_card_listener" serverClazz="nc.uap.cpb.org.groupadmin.GroupAdminUifCardMouseListener">
                        <Events>
                            <Event async="true" name="onclick" onserver="true">
                                <SubmitRule cardSubmit="false" panelSubmit="false" tabSubmit="false">
                                    <Widget cardSubmit="true" id="main" panelSubmit="false" tabSubmit="false">
                                    </Widget>
                                </SubmitRule>
                                <Params>
                                    <Param>
                                        <Name>mouseEvent</Name>
                                        <Value></Value>
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
        <MenuBarComp id="menu_list">
            <MenuItem id="add" modifiers="2" operatorStatusArray="0,1,2" text="新建">
                <Listeners>
                    <MouseListener id="menu_item_add_listener" serverClazz="nc.uap.cpb.org.groupadmin.GroupAdminUifAddMouseListener">
                        <Events>
                            <Event async="true" name="onclick" onserver="true">
                                <SubmitRule cardSubmit="false" panelSubmit="false" tabSubmit="false">
                                    <Widget cardSubmit="true" id="main" panelSubmit="false" tabSubmit="false">
                                        <Dataset id="userds" type="ds_current_page">
                                        </Dataset>
                                    </Widget>
                                </SubmitRule>
                                <Params>
                                    <Param>
                                        <Name>mouseEvent</Name>
                                        <Value></Value>
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
            <MenuItem id="delete" modifiers="2" operatorStatusArray="1,2" text="删除">
                <Listeners>
                    <MouseListener id="menu_item_delete_listener" serverClazz="nc.uap.cpb.org.groupadmin.GroupAdminUifDelMouseListener">
                        <Events>
                            <Event async="true" name="onclick" onserver="true">
                                <SubmitRule cardSubmit="false" panelSubmit="false" tabSubmit="false">
                                    <Widget cardSubmit="false" id="main" panelSubmit="false" tabSubmit="false">
                                        <Dataset id="groupds" type="ds_current_line">
                                        </Dataset>
                                        <Dataset id="userds" type="ds_current_line">
                                        </Dataset>
                                        <Dataset id="admingroupds" type="ds_all_line">
                                        </Dataset>
                                    </Widget>
                                </SubmitRule>
                                <Params>
                                    <Param>
                                        <Name>mouseEvent</Name>
                                        <Value></Value>
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
            <MenuItem id="edit" modifiers="2" operatorStatusArray="1" text="修改">
                <Listeners>
                    <MouseListener id="menu_item_edit_listener" serverClazz="nc.uap.cpb.org.groupadmin.GroupAdminUifEditMouseListener">
                        <Events>
                            <Event async="true" name="onclick" onserver="true">
                                <SubmitRule cardSubmit="false" panelSubmit="false" tabSubmit="false">
                                    <Widget cardSubmit="true" id="main" panelSubmit="false" tabSubmit="false">
                                        <Dataset id="userds" type="ds_current_page">
                                        </Dataset>
                                    </Widget>
                                </SubmitRule>
                                <Params>
                                    <Param>
                                        <Name>mouseEvent</Name>
                                        <Value></Value>
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
            <MenuItem id="save" modifiers="2" operatorStatusArray="4,3" text="保存">
                <Listeners>
                    <MouseListener id="menu_item_save_listener" serverClazz="nc.uap.cpb.org.groupadmin.GroupAdminUifSaveMouseListener">
                        <Events>
                            <Event async="true" name="onclick" onserver="true">
                                <SubmitRule cardSubmit="false" panelSubmit="false" tabSubmit="false">
                                    <Widget cardSubmit="false" id="main" panelSubmit="false" tabSubmit="false">
                                        <Dataset id="groupds" type="ds_all_line">
                                        </Dataset>
                                        <Dataset id="userds" type="ds_all_line">
                                        </Dataset>
                                    </Widget>
                                </SubmitRule>
                                <Params>
                                    <Param>
                                        <Name>mouseEvent</Name>
                                        <Value></Value>
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
                    <MouseListener id="menu_item_cancel_listener" serverClazz="nc.uap.cpb.org.groupadmin.GroupAdminUifCancelMouseListener">
                        <Events>
                            <Event async="true" name="onclick" onserver="true">
                                <SubmitRule cardSubmit="false" panelSubmit="false" tabSubmit="false">
                                    <Widget cardSubmit="false" id="main" panelSubmit="false" tabSubmit="false">
                                        <Dataset id="userds" type="ds_current_page">
                                        </Dataset>
                                    </Widget>
                                </SubmitRule>
                                <Params>
                                    <Param>
                                        <Name>mouseEvent</Name>
                                        <Value></Value>
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
            <MenuItem displayHotKey="CTRL+SHIFT+" id="authorize" modifiers="3" operatorStatusArray="1" text="授权集团">
                <Listeners>
                    <MouseListener id="menu_item_cancel_listener" serverClazz="nc.uap.cpb.org.groupadmin.GroupAdminUifAuthorizeGroupMouseListener">
                        <Events>
                            <Event async="true" name="onclick" onserver="true">
                                <SubmitRule cardSubmit="false" panelSubmit="false" tabSubmit="false">
                                </SubmitRule>
                                <Params>
                                    <Param>
                                        <Name>mouseEvent</Name>
                                        <Value></Value>
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
            <MenuItem id="relaterole" modifiers="2" operatorStatusArray="1" text="关联角色">
                <Listeners>
                    <MouseListener id="menu_item_cancel_listener" serverClazz="nc.uap.cpb.org.groupadmin.GroupAdminRelateRoleMouseListener">
                        <Events>
                            <Event async="true" name="onclick" onserver="true">
                                <SubmitRule cardSubmit="false" panelSubmit="false" tabSubmit="false">
                                </SubmitRule>
                                <Params>
                                    <Param>
                                        <Name>mouseEvent</Name>
                                        <Value></Value>
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
            <MenuItem displayHotKey="CTRL+SHIFT+" id="list" modifiers="3" operatorStatusArray="1,0" text="列表显示">
                <Listeners>
                    <MouseListener id="menu_item_list_listener" serverClazz="nc.uap.cpb.org.groupadmin.GroupAdminUifListMouseListener">
                        <Events>
                            <Event async="true" name="onclick" onserver="true">
                                <SubmitRule cardSubmit="false" panelSubmit="false" tabSubmit="false">
                                    <Widget cardSubmit="true" id="main" panelSubmit="false" tabSubmit="false">
                                    </Widget>
                                </SubmitRule>
                                <Params>
                                    <Param>
                                        <Name>mouseEvent</Name>
                                        <Value></Value>
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
