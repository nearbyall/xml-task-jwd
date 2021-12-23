<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>File Upload</title>
</head>
<body>

	<form method="post" action="Controller" enctype="multipart/form-data">
	
		Select file to upload: <input type="file" name="file" accept=".xml"/>
		<br/>
		<input type="radio" name="parsertype" value="DOM"/>DOM
    	<input type="radio" name="parsertype" value="STAX"/>STAX
		<input type="radio" name="parsertype" value="SAX"/>SAX
		<br/> 
		<input type="submit" value="Upload" />
	</form>
	
</body>
</html>



