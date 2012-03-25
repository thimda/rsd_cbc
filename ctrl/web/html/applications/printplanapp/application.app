<?xml version="1.0" encoding='UTF-8'?>
<Application TagName="Application" caption="打印模板管理" controllerClazz="nc.uap.ctrl.tpl.qry.CpQryPlanCtrl" defaultWindowId="cp_printinit" id="printplanapp" sourcePackage="ctrl/src/public/">
    <PageMetas>
        <PageMeta caption="打印模板管理" id="cp_printinit">
        </PageMeta>
        <PageMeta caption="模板分配" id="cp_templateassign">
        </PageMeta>
    </PageMetas>
    <Connectors>
        <Connector pluginId="assign_plugin" plugoutId="plugout_gridrow" source="main" sourceWindow="cp_templateassign" target="template_relation" targetWindow="cp_printinit">
            <Maps>
                <Map>
                    <outValue>plugoutRow</outValue>
                    <inValue>assignRow</inValue>
                </Map>
            </Maps>
        </Connector>
    </Connectors>
</Application>
