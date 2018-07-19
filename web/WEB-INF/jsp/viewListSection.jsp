<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="listItems" type="java.util.List<java.lang.String>" scope="session"/>
<tr>
    <td colspan="2">
        <ul>
            <c:forEach var="item" items="${listItems}">
                <li>${item}</li>
            </c:forEach>
        </ul>
    </td>
</tr>