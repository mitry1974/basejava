<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="orgSection" type="ru.javawebinar.basejava.model.OrganizationSection" scope="session"/>
<c:forEach var="organization" items="${orgSection.organizations}">
    <jsp:useBean id="organization" type="ru.javawebinar.basejava.model.Organization"/>
    <tr>
        <td colspan="2">
            <h3><a href="${organization.homePage.url}">${organization.homePage.name}</a></h3>
        </td>
    </tr>
    <c:forEach var="position" items="${organization.positions}">
        <tr>
            <td width="15%" style="vertical-align: top">${position.startDate} - ${position.endDate}</td>
            <td><b>${position.title}</b>.<br>${position.description}</td>
        </tr>

    </c:forEach>
</c:forEach>
