<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.ufida.com/lfw" prefix="lfw" %>
<%@ taglib uri="http://www.ufida.com/ra" prefix="ra" %>
<lfw:pageView>
<html xmlns="http://www.w3.org/1999/xhtml">	
	<head>
		<title>UFIDA NC</title>
		<lfw:base/>
		<lfw:head/>		
		<lfw:pageImport/>	
	</head>
	<body>
		<ra:pageBody>
			<ra:widget id="main">
				<div style='border:1px solid red;width:350px;height:200px;background:yellow'>
					<ra:menubar id="menu_usergroup" widgetId="main" />
				</div>
				<div style='border:1px solid red;width:350px;'>
					<div>
						<ra:grid id="grid_usergroup" widgetId="main"/>
					</div>
				</div>
			</ra:widget>
			<div>
			</div>
		</ra:pageBody>
	</body>
</html>
</lfw:pageView>