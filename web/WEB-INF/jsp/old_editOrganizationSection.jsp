<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="orgSection" type="ru.javawebinar.basejava.model.OrganizationSection" scope="session"/>
<jsp:useBean id="sectionType" type="ru.javawebinar.basejava.model.SectionType" scope="session"/>
<div id="${sectionType}">
    <table id="${sectionType}_header" style="width: 900px;">
        <tr>
            <td colspan="2"><h2>${sectionType.title}</h2></td>
            <td align="right">
                <button type="button" name="1" value="resume/1"
                        onclick="experience_overlay(this)"><img
                        src="resources/img/add.png"
                        title="Добавить место работы"
                        style="vertical-align: bottom">
                </button>
            </td>
        </tr>
    </table>

    <c:forEach var="organization" items="${orgSection.organizations}">
        <jsp:useBean id="organization" type="ru.javawebinar.basejava.model.Organization"/>
        <div id="${organization.homePage.name}">
            <div id="${organization.homePage.name}_header">
                <table style="width: 900px; border: 0px #2E6E9E" rules="none">
                    <tbody>
                    <tr>
                        <td colspan="3" bgcolor="#5f9ea0"></td>
                    </tr>
                    <tr>
                        <td width="25%"><h3>Организация:</h3></td>
                        <td align="right" colspan="2">
                            <button type="button" name="2" value="resume/deleteAchiev"
                                    onclick="deleteObject('${organization.homePage.name}')"><img
                                    src="resources/img/delete.png"
                                    title="Удалить организацию">
                            </button>
                        </td>
                    </tr>
                    <tr>
                        <td width="25%"><b>Название:</b></td>
                        <td colspan="2"><input type="text" name="${sectionType}_organizationName" size="100%"
                                               value="${organization.homePage.name}"/></td>
                    </tr>
                    <tr>
                        <td width="25%"><b>Url:</b></td>
                        <td colspan="2"><input type="text" name="organizationUrl" size="100%"
                                               value="${organization.homePage.url}"/></td>
                    </tr>
                    <tr>
                        <td width="25%"><h3>Позиции:</h3></td>
                        <td align="right" colspan="2">
                            <button type="button" onclick="position_overlay('${organization.homePage.name}_positions')">
                                <img
                                        name="${organization.homePage.name}_positions" src="resources/img/add.png"
                                        title="Добавить позицию"></button>
                        </td>
                    </tr>
                </table>
            </div>
            <div id="${organization.homePage.name}_positions" name="positions">
                <c:forEach var="position" items="${organization.positions}">
                    <jsp:useBean id="position" type="ru.javawebinar.basejava.model.Organization.Position"/>
                    <div name="position" id="${position.title}">
                        <table style="width: 900px;">
                            <tr>
                                <td width="25%"><b>Дата начала:</b></td>
                                <td><input type="month" name="begindate" value="${position.startDate}"></td>
                                <td width="10%" align="right">
                                    <button type="button" onclick="deleteObject('${position.title}')"><img
                                            name="${position.title}"
                                            src="resources/img/delete.png"
                                            title="Удалить позицию">
                                    </button>
                                </td>
                            </tr>
                            <tr>
                                <td width="25%"><b>Дата окончания:</b></td>
                                <td colspan="2"><input type="month" name="enddate" value="${position.endDate}"></td>
                            </tr>
                            <tr>
                                <td width="25%"><b>Позиция:</b></td>
                                <td colspan="2"><input type="text" size="100%" name="positiontitle"
                                                       value="${position.title}">
                                </td>
                            </tr>
                            <tr>
                                <td width="25%"><b>Описание:</b></td>
                                <td colspan="2"><input type="text" size="100%" name="positiondescription"
                                                       value="${position.description}">
                                </td>
                            </tr>
                        </table>
                    </div>
                </c:forEach>
            </div>
            </tbody>
        </div>
    </c:forEach>
</div>

