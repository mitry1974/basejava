<%@ page import="ru.javawebinar.basejava.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <script type="text/javascript" src="resources/js/editform.js" defer></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="resources/css/style.css">
    <jsp:useBean id="resumeAction" type="java.lang.String" scope="request"/>
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="${pageContext.request.contextPath}/resume/${resumeAction}"
          enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <h3>Имя:</h3>
        <input id="fullname" type="text" name="fullName" size="100%" value="${resume.fullName}"/>
        <p>
        <h3>Контакты:</h3>
        <table>
            <c:forEach var="type" items="<%=ContactType.values()%>">
                <tr>
                    <td><c:out value="${type.title}"></c:out></td>
                    <td><input type="text" id="${type}" name="${type.name()}" value="${resume.getContact(type)}"/><br>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <c:forEach var="sectionType" items="<%=SectionType.values()%>">
            <jsp:useBean id="sectionType" type="ru.javawebinar.basejava.model.SectionType"/>
            <%Section section = resume.getSections().get(sectionType);%>
            <h2>${sectionType.title}</h2>
            <c:choose>
                <c:when test="${sectionType eq 'OBJECTIVE' || sectionType eq 'PERSONAL'}">
                    <input type="text" size="100%" id='${sectionType}' name='${sectionType}'
                           value=<%=((TextSection) section).getContent()%>/>
                </c:when>
                <c:when test="${sectionType eq 'ACHIEVEMENT' || sectionType eq 'QUALIFICATIONS'}">
                    <h3>${sectionType.name()}</h3>
                    <textarea name='${type}' cols=75
                              rows=5><%=String.join("\n", ((ListSection) section).getItems())%></textarea>
                </c:when>
                <c:when test="${sectionType eq 'EXPERIENCE' || sectionType eq 'EDUCATION'}">
                    <%session.setAttribute("orgSection", section);%>
                    <%session.setAttribute("sectionType", sectionType);%>
                    <jsp:include page="editOrganizationSection.jsp"/>
                </c:when>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="windows.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

<div id="position_overlay">
    <div>
        <p><b>Позиция:</b></p>
        <table>
            <tr>
                <td>Начало работы:</td>
                <td><input type="month" id="position_startdate"></td>
            </tr>
            <tr>
                <td>Конец работы:</td>
                <td><input type="month" id="position_finishdate"></td>
            </tr>
            <tr>
                <td>Позиция:</td>
                <td><input type="text" id="position_title"></td>
            </tr>
            <tr>
                <td>Описание:</td>
                <td><input type="text" id="position_description"></td>
            </tr>
        </table>
        <a href="javascript:addPosition()">Добавить</a>
        <a href="javascript:closePositionDialog()">Закрыть</a>
    </div>
</div>

<div id="organization_overlay">
    <div>
        <p><b>Организация:</b></p>
        <table>
            <tr>
                <td>Название:</td>
                <td><input type="text" id="organization_title"></td>
            </tr>
            <tr>
                <td>Ссылка:</td>
                <td><input type="text" id="organization_url"></td>
            </tr>
        </table>
        <a href="javascript:addOrganization()">Добавить</a>
        <a href="javascript:closeOrganizationDialog()">Закрыть</a>
    </div>
</div>

