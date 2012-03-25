<?xml version="1.0" encoding='UTF-8'?>
<PageMeta caption="excel控件" id="file\exceldemo">
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
        <Widget id="user" refId="user">
        </Widget>
    </Widgets>
    <Listeners>
        <PageListener id="excel_defaultListener" serverClazz="nc.uap.lfw.core.event.deft.DefaultPageServerListener">
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
        <MenuBarComp id="tesmenu1">
            <MenuItem id="addcolumn" modifiers="2" text="增加列1">
                <Listeners>
                    <MouseListener id="addcolumnListener1">
                        <Events>
                            <Event async="true" name="onclick" onserver="false">
                                <Params>
                                    <Param>
                                        <Name>mouseEvent</Name>
                                        <Desc>                                            <![CDATA[]]>
                                        </Desc>
                                    </Param>
                                </Params>
                                <Action>                                    <![CDATA[	var masterds = pageUI.getWidget('user').getDataset('pt_user');
	var field = {key:"testcolumn",value:"测试列",dftValue:"testdata",nullAble:true,dataType:"String",field:"testcolumn",formater:null,maxValue:null,minValue:null,isLock:false};
	masterds.addField(field);
	var rows = masterds.getRows();
	for(var i=0;i<rows.length;i++){
		var dataarr = rows[i].dataArr;
		dataarr.push(field.dftValue);
	}
	var excelcmp = pageUI.getWidget('user').getComponent("test");
	var model = excelcmp.model	
	var testheader =  new ExcelCompHeader('testcolumn','测试列','20','String',true,false,true,'','','left','',false,DefaultRender,'StringText',null,null,null,false,false);
	model.addHeader(testheader );
	model.setDataSet(masterds);

	excelcmp.setModel(model);
	]]>
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
