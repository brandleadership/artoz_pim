<%@ page import="ch.screenconcept.artoz.update.ArtozUpdate"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="de" lang="de">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="pragma" content="no-cache">
	<title>Artoz Update</title>
</head>
<%
	ArtozUpdate.createLanguages();
	ArtozUpdate.createCurrencies();
	ArtozUpdate.createCatalog();
	ArtozUpdate.createTitles();
	ArtozUpdate.createUserPriceGroups();
%>
<body>
</body>
</html>