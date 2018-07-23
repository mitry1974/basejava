<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="orgSection" type="ru.javawebinar.basejava.model.OrganizationSection" scope="session"/>

<c:forEach var="organization" items="${orgSection.organizations}">
        <jsp:useBean id="organization" type="ru.javawebinar.basejava.model.Organization"/>
        <tr>
            <td><b>Название:</b></td>
            <td>
                <button type="button" name="2" value="resume/deleteAchiev"
                        onclick="deleteRow(this)"><img
                        src="resources/img/delete.png"></button>
            </td>

            </tr>
        <tr>
            <td><input type="text" size="100%" value="${organization.homePage.name}"/></td>
        </tr>
        <tr>
            <td><b>Url:</b></td>
        </tr>
        <tr>
            <td><input type="text" size="100%" value="${organization.homePage.url}"/></td>
        </tr>
        <c:forEach var="position" items="${organization.positions}">
            <tr>
                <td><b>Дата начала:</b></td>
            </tr>
            <tr>
                <td><input type="month" name="begindate" value="${position.startDate}"></td>
            </tr>
            <tr>
                <td><b>Дата окончания:</b></td>
            </tr>
            <tr>
                <td><input type="month" name="enddate" value="${position.endDate}"></td>
            </tr>
            <tr>
                <td><b>Позиция:</b></td>
            </tr>
            <tr>
                <td><input type="text" size="100%" name="positiontitle" value="${position.title}"></td>
            </tr>
            <tr>
                <td><b>Описание:</b></td>
            </tr>
            <tr>
                <td><input type="text" size="100%" name="positiondescription" value="${position.description}"></td>
            </tr>
        </c:forEach>
    <td bgcolor="#5f9ea0"></td>
</c:forEach>
