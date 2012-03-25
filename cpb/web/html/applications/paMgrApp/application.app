<?xml version="1.0" encoding='UTF-8'?>
<Application TagName="Application" caption="个性化管理" controllerClazz="nc.uap.cpb.pamgr.PaMgrWindowController" defaultWindowId="pamgr" id="paMgrApp" sourcePackage="cpb/src/public/">
    <PageMetas>
        <PageMeta caption="个性化管理" id="pamgr">
        </PageMeta>
        <PageMeta caption="模板分配" id="cp_templateassign">
        </PageMeta>
    </PageMetas>
    <Connectors>
        <Connector pluginId="assign_plugin" plugoutId="plugout_gridrow" source="main" sourceWindow="cp_templateassign" target="template_relation" targetWindow="pamgr">
            <Maps>
                <Map>
                    <outValue>plugoutRow</outValue>
                    <inValue>assignRow</inValue>
                </Map>
            </Maps>
        </Connector>
    </Connectors>
</Application>
