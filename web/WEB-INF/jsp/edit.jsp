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
    <form method="post" onsubmit="saveResume()" action="${pageContext.request.contextPath}/resume/${resumeAction}"
          enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <h3>Имя:</h3>
        <input type="text" name="fullName" size="100%" value="${resume.fullName}"/>
        <p>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <input type="text" name="${type.name()}" value="${resume.getContact(type)}"/><br>
        </c:forEach>

        <c:forEach var="sectionType" items="<%=SectionType.values()%>">
            <jsp:useBean id="sectionType" type="ru.javawebinar.basejava.model.SectionType"/>
            <%Section section = resume.getSections().get(sectionType);%>
            <c:choose>
                <c:when test="${sectionType == 'OBJECTIVE'}">
                    <td><h2>${sectionType.title}</h2></td>
                    <table>
                        <tr>
                            <td colspan="2">
                                <input type="text" size="100%" value="<%=((TextSection) section).getContent()%>"/>
                            </td>
                        </tr>
                    </table>
                </c:when>
                <c:when test="${sectionType == 'PERSONAL'}">
                    <td><h2>${sectionType.title}</h2></td>
                    <table>
                        <div id="personall">
                            <tr>
                                <td colspan="2">
                                    <input type="text" size="100%" value="<%=((TextSection) section).getContent()%>"/>
                                </td>
                            </tr>
                        </div>
                    </table>
                </c:when>
                <c:when test="${sectionType == 'ACHIEVEMENT'}">
                    <table id="achievs">
                        <tr>
                                    <td><h2>${sectionType.title}</h2></td>
                                    <td>
                                        <button type="button" name="1" value="resume/1"
                                                onclick="addAchievement()"><img
                                                src="resources/img/add.png"
                                                alt="Добавить..."
                                                style="vertical-align: bottom">
                                        </button>
                                    </td>
                        </tr>
                        <c:forEach var="itm" items="<%=((ListSection)resume.getSection(sectionType)).getItems()%>">
                            <tr>
                                <td>
                                    <input size="100%" name="${sectionType.name()}" value="${itm}"/>
                                </td>
                                <td>
                                    <button type="button" name="deleteAchiev" value="resume/deleteAchiev"
                                            onclick="deleteRow(this)"><img
                                            src="resources/img/delete.png"></button>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:when>
                <c:when test="${sectionType == 'QUALIFICATIONS'}">
                    <table id="qualifications">
                        <tr>
                            <td><h2>${sectionType.title}</h2></td>
                            <td>
                                <button type="button" name="2" value="resume/2"
                                        onclick="addQalification()"><img
                                        src="resources/img/add.png"
                                        alt="Добавить..."
                                        style="vertical-align: bottom">
                                </button>
                            </td>
                        </tr>
                        <div id="overlay">
                            <div>
                                <p>
                                <form id="form" name="name">
                                    <h2>Start date</h2>
                                    <h2>Finish date</h2>
                                </form>
                                </p>
                            </div>
                        </div>
                        <c:forEach var="itm" items="<%=((ListSection)resume.getSection(sectionType)).getItems()%>">
                            <tr>
                                <td>
                                    <input size="100%" type="text" name="${sectionType.name()}" value="${itm}"/>
                                </td>
                                <td>
                                    <button type="button" name="4" value="resume/4"
                                            onclick="deleteRow()"><img
                                            src="resources/img/delete.png"></button>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:when>
                <c:when test="${sectionType == 'EXPERIENCE'}">
                    <table id="experience">
                        <tr>
                            <td><h2>${sectionType.title}</h2></td>
                            <td>
                                <button type="button" name="1" value="resume/1"
                                        onclick="addAchievement()"><img
                                        src="resources/img/add.png"
                                        alt="Добавить..."
                                        style="vertical-align: bottom">
                                </button>
                            </td>
                        </tr>

                        <%session.setAttribute("orgSection", section);%>
                        <jsp:include page="editOrganizationSection.jsp"/>
                    </table>
                </c:when>
                <c:when test="${sectionType == 'EDUCATION'}">
                    <table id="education">
                        <tr>
                            <td><h2>${sectionType.title}</h2></td>
                            <td>
                                <button type="button" name="add" value="resume/1"
                                        onclick="addAchievement()"><img
                                        src="resources/img/add.png"
                                        alt="Добавить..."
                                        style="vertical-align: bottom">
                                </button>
                            </td>
                        </tr>
                        <%session.setAttribute("orgSection", section);%>
                        <jsp:include page="editOrganizationSection.jsp"/>
                    </table>
                </c:when>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit" onclick="saveResume()">Сохранить</button>
        <button onclick="windows.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
