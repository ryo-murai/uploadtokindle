<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>Upload to Kindle</title>
</head>
<body>
<p>
<div>${url}</div>
<div>uploadToKindle couldn't fetch the document URL.</div>
<div>possible causes are:</div>
<ul>
<li>an authentication needed</li>
<li>accessing the site from uploadToKindle is forbiddened</li>
<li>the URL is not correct</li>
<li>the site is currently unavailable for some reason (i.e. maintenance etc)</li>
<li>and so on ...</li>
</ul>
</p>
<%@ include file="msg1000inc.jsp"%>
</body>

