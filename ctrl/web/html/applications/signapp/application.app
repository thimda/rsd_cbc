<?xml version="1.0" encoding='UTF-8'?>
<Application TagName="Application" caption="印章" controllerClazz="nc.uap.portal.ctrl.office.controller.SignAppControl" defaultWindowId="sign_remotesigns" id="signapp" sourcePackage="ctrl/src/public/">
    <PageMetas>
        <PageMeta caption="印章编辑" id="sign_editor">
        </PageMeta>
        <PageMeta caption="服务器印章" id="sign_remotesigns">
        </PageMeta>
    </PageMetas>
    <Connectors>
        <Connector pluginId="signedit_plugin" plugoutId="sign_plugout" source="main" sourceWindow="sign_editor" target="main" targetWindow="sign_remotesigns">
            <Maps>
                <Map>
                </Map>
            </Maps>
        </Connector>
    </Connectors>
</Application>
