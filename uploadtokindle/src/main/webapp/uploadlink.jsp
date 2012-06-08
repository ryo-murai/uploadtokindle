<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Upload to Kindle</title>
</head>
<body>
	<form method="post" action="uploadlink">
			<div><label>URL:</label><input type="text" name="url" value="${url}" /></div>
			<div>
				<label>File:</label><input type="text" name="fileName" value="${fileName}" />
				<select name="fileExt">
					<option ${f:select("fileExt", "AUTO")}>(*auto detect the type)</option>
					<option ${f:select("fileExt", "PDF")}>.pdf</option>
					<option ${f:select("fileExt", "MOBI")}>.mobi</option>
					<option ${f:select("fileExt", "EPUB")}>.epub</option>
				</select>
			</div>
			<div><label>convert:</label><input type="checkbox" ${f:checkbox("convert")} /></div>
			<div><input type="submit" value="Upload" /></div>
	</form>
</body>
</html>
