<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>upload to kindle</title>
</head>
<body>
<p>upload your docs</p>

<form method="post" action="">
		<div>
			<label>sender address:</label><div>${f:h("uploadUser.user.email")}</div>
			<label>kindle address:</label>
			<input type="text" ${f:text("uploadUser.uploadDestAddress")}/>
			<select name="uploadUser.addrType">
				<option ${f:select("uploadUser.addrType", "KINDLECOM")}>@kindle.com</option>
				<option ${f:select("uploadUser.addrType", "FREEKINDLECOM")}>@free.kindle.com</option>
			</select>
		</div>
		<div><label>convert:</label><input type="checkbox" name="convert" /></div>
		<div><input type="submit" value="Upload" /></div>
</form>
</body>
</html>
