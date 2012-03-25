<?xml version="1.0" encoding='UTF-8'?>
<Application TagName="Application" caption="查询管理" controllerClazz="nc.uap.ctrl.tpl.qry.CpQryPlanCtrl" defaultWindowId="cp_qryinit" id="paMgrApp" sourcePackage="ctrl/src/public/">
    <PageMetas>
        <PageMeta caption="查询模板管理" id="cp_qryinit">
        </PageMeta>
        <PageMeta caption="模板分配" id="cp_templateassign">
        </PageMeta>
    </PageMetas>
    <Connectors>
        <Connector pluginId="assign_plugin" plugoutId="plugout_gridrow" source="main" sourceWindow="cp_templateassign" target="template_relation" targetWindow="cp_qryinit">
            <Maps>
                <Map>
                    <outValue>plugoutRow</outValue>
                    <inValue>assignRow</inValue>
                </Map>
            </Maps>
        </Connector>
    </Connectors>
</Application>
