<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="history/history.css" />
<!--
<%
  String playerpath = request.getContextPath()+"/core/pt/swf/down";
%>
-->
<title></title>
<script>
	var webContextPath = '<%=request.getContextPath()%>';
	var isIE  = (navigator.appVersion.indexOf("MSIE") != -1) ? true : false;
	//var isWin = (navigator.appVersion.toLowerCase().indexOf("win") != -1) ? true : false;
	var isOpera = (navigator.userAgent.indexOf("Opera") != -1) ? true : false;
	var browser = (isIE==true?'IE':'Opera')
	var playerpath = '<%=request.getContextPath()%>'+"/core/pt/swf/down?browser="+browser;
</script>
<script src="AC_OETags.js" language="javascript"></script>
<script src="client.js" language="javascript"></script>

<!--  BEGIN Browser History required section -->
<script src="history/history.js" language="javascript"></script>
<!--  END Browser History required section -->

<style>
body { margin: 0px; overflow:hidden }
</style>
<script language="JavaScript" type="text/javascript">
<!--
// -----------------------------------------------------------------------------
// Globals
// Major version of Flash required
var requiredMajorVersion = 9;
// Minor version of Flash required
var requiredMinorVersion = 0;
// Minor version of Flash required
var requiredRevision = 124;
// -----------------------------------------------------------------------------
// -->
</script>
</head>

<body scroll="no">
<script language="JavaScript" type="text/javascript">
<!--
// Version check for the Flash Player that has the ability to start Player Product Install (6.0r65)
var hasProductInstall = DetectFlashVer(6, 0, 65);

// Version check based upon the values defined in globals
var hasRequestedVersion = DetectFlashVer(requiredMajorVersion, requiredMinorVersion, requiredRevision);

if ( hasProductInstall && !hasRequestedVersion ) {
	var alternateContent = 'Your Flash Player is too old '
  	+ 'This content requires the higher version Adobe Flash Player. '
   	+ '<a href='+playerpath+'>Get new version Flash</a>';
    document.write(alternateContent);  
} else if(!hasProductInstall){ 
    var alternateContent = 'Alternate HTML content should be placed here. '
  	+ 'This content requires the Adobe Flash Player. '
   	+ '<a href='+playerpath+'>Get Flash</a>';
    document.write(alternateContent);  
  }else {
	// if we've detected an acceptable version
	// embed the Flash Content SWF when all tests are passed
	AC_FL_RunContent(
			"src", "wfbrowser",
			"width", "100%",
			"height", "100%",
			"align", "middle",
			"id", "wfbrowser",
			"quality", "high",
			"bgcolor", "#ffffff",
			"name", "wfbrowser",
			"allowScriptAccess","sameDomain",
			"type", "application/x-shockwave-flash",
			"pluginspage", "http://www.adobe.com/go/getflashplayer"
	);
  }
// -->
</script>
<noscript>
  	<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
			id="wfbrowser" width="100%" height="100%"
			codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">
			<param name="movie" value="wfbrowser.swf" />
			<param name="quality" value="high" />
			<param name="bgcolor" value="#ffffff" />
			<param name="allowScriptAccess" value="sameDomain" />
			<embed src="wfbrowser.swf" quality="high" bgcolor="#ffffff"
				width="100%" height="100%" name="wfbrowser" align="middle"
				play="true"
				loop="false"
				quality="high"
				allowScriptAccess="sameDomain"
				type="application/x-shockwave-flash"
				pluginspage="http://www.adobe.com/go/getflashplayer">
			</embed>
	</object>
</noscript>
</body>
</html>
