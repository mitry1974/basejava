<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/resume/addOrganization">
    <h3>Добавить организацию</h3>
    <table>
        <tr>
            <td>Start date:</td>
            <td><input name="startdate" type="month"></td>
        </tr>
        <tr>
            <td>Finish date:</td>
            <td><input name="finishdate" type="month"></td>
        </tr>
        <tr>
            <td>Finish date:</td>
            <td><input name="title" type="text"></td>
        </tr>
        <tr>
            <td>Url:</td>
            <td><input name="url" type="url"></td>
        </tr>
    </table>
    <input name="submit" value="Send" type="submit"/>
</form>
</body>
</html>