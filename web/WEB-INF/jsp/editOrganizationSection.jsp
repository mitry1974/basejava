<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="orgSection" type="ru.javawebinar.basejava.model.OrganizationSection" scope="session"/>
<jsp:useBean id="sectionType" type="ru.javawebinar.basejava.model.SectionType" scope="session"/>

<br><a href="javascript:addOrganizationDialog('${sectionType}')">Добавить Организацию</a>
<table style="width: 900px; border: 0 #2E6E9E" rules="none" id="${sectionType}_organizations">
    <tbody>
    <c:forEach var="organization" items="${orgSection.organizations}" varStatus="orgCounter">
        <jsp:useBean id="organization" type="ru.javawebinar.basejava.model.Organization"/>
        <tr id="${sectionType}_${orgCounter.index}">
            <td>
                <div id="${orgCounter.index}${orgCounter.index}">

                    <hr color="#5f9ea0" size="2" width="900">
                    <p style="border: 1px solid #C1FF0A;  padding: 10px;">
                        <a href="javascript:deleteOrganization('${sectionType}', '${orgCounter.index}')">Удалить организацию </a><br>
                        <b>Название:</b><br>
                        <input type="text" size="100%" name="${sectionType}_name"
                               value="${organization.homePage.name}"/><br>
                        <b>Url:</b><br>
                        <input type="text" size="100%" name="${sectionType}_url"
                               value="${organization.homePage.url}"/><br>
                        <a href="javascript:addPositionDialog('${sectionType}', '${orgCounter.index}')">Добавить
                            позицию</a>
                    </p>
                    <table style="width: 900px; border: 0 #2E6E9E" rules="none"
                           id="${sectionType}_${orgCounter.index}_positions">
                        <c:forEach var="position" items="${organization.positions}" varStatus="posCounter">
                            <jsp:useBean id="position" type="ru.javawebinar.basejava.model.Organization.Position"/>
                            <tr id="${sectionType}_${orgCounter.index}_${posCounter.index}">
                                <td>
                                    <p style="border: 1px solid #C1FF0A;  padding: 10px;">
                                        <b>Дата начала:</b><br>
                                        <input type="month" name="${sectionType}_${orgCounter.index}_sdate"
                                               value="${position.startDate}"><br>
                                        <b>Дата окончания:</b><br>
                                        <input type="month" name="${sectionType}_${orgCounter.index}_fdate"
                                               value="${position.endDate}"><br>
                                        <b>Позиция:</b><br>
                                        <input type="text" size="100%"
                                               name="${sectionType}_${orgCounter.index}_title"
                                               value="${position.title}"><br>
                                        <b>Описание:</b><br>
                                        <input type="text" size="100%"
                                               name="${sectionType}_${orgCounter.index}_decsr"
                                               value="${position.description}"><br>
                                        <a href="javascript:deletePosition('${sectionType}', '${orgCounter.index}', '${posCounter.index}')">Удалить
                                            позицию</a>
                                    </p>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

