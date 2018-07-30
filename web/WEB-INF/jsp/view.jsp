<%@ page import="ru.javawebinar.basejava.model.ListSection" %>
<%@ page import="ru.javawebinar.basejava.model.OrganizationSection" %>
<%@ page import="ru.javawebinar.basejava.model.TextSection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="resources/css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img
            src="resources/img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    </p>
    <table cellpadding="2">
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType, ru.javawebinar.basejava.model.Section>"/>
            <tr>
                <td colspan="2"><h2><a name="type.name">${sectionEntry.key.title}</a></h2></td>
            </tr>
            <c:choose>
                <c:when test="${sectionEntry.key == 'OBJECTIVE'}">
                    <tr>
                        <td colspan="2">
                            <h3><%=((TextSection) sectionEntry.getValue()).getContent()%>
                            </h3>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${sectionEntry.key == 'PERSONAL'}">
                    <tr>
                        <td colspan="2">
                            <h3><%=((TextSection) sectionEntry.getValue()).getContent()%>
                            </h3>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${sectionEntry.key == 'ACHIEVEMENT'}">
                    <%session.setAttribute("listItems", ((ListSection) sectionEntry.getValue()).getItems());%>
                    <jsp:include page="viewListSection.jsp"/>
                </c:when>
                <c:when test="${sectionEntry.key == 'QUALIFICATIONS'}">
                    <%session.setAttribute("listItems", ((ListSection) sectionEntry.getValue()).getItems());%>
                    <jsp:include page="viewListSection.jsp"/>
                </c:when>
                <c:when test="${sectionEntry.key == 'EXPERIENCE'}">
                    <%session.setAttribute("orgSection", (OrganizationSection) sectionEntry.getValue());%>
                    <jsp:include page="viewOrganizationSection.jsp"/>
                </c:when>
                <c:when test="${sectionEntry.key == 'EDUCATION'}">
                    <%session.setAttribute("orgSection", (OrganizationSection) sectionEntry.getValue());%>
                    <jsp:include page="viewOrganizationSection.jsp"/>
                </c:when>
            </c:choose>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
