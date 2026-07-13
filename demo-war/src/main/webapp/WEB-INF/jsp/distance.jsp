<%--
 (#)distance.jsp    0.3.0   07/12/2026

 @author   Jonathan Parker
 @version  0.3.0
 @since    0.3.0

 MIT License

 Copyright (c) 2026 Jonathan M. Parker

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
--%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title><fmt:message key="jsp.distance.title" /></title>
        <link rel="stylesheet" href="<c:url value='/static/css/styles.css' />" />
    </head>
    <body>
        <h2><fmt:message key="jsp.distance.title" /></h2>

        <table>
            <thead>
                <tr>
                    <th><fmt:message key="jsp.distance.fromZipCode" /></th>
                    <th><fmt:message key="jsp.distance.toZipCode" /></th>
                    <th><fmt:message key="jsp.distance.toCity" /></th>
                    <th><fmt:message key="jsp.distance.toState" /></th>
                    <th><fmt:message key="jsp.distance.distanceInMiles" /></th>
                    <th><fmt:message key="jsp.distance.distanceInKilometers" /></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="d" items="${requestScope.distances}">
                    <tr>
                        <td style="text-align: center;"><c:out value="${d.fromZipCode}" /></td>
                        <td style="text-align: center;"><c:out value="${d.toZipCode}" /></td>
                        <td style="text-align: center;"><c:out value="${d.toCity}" /></td>
                        <td style="text-align: center;"><c:out value="${d.toState}" /></td>
                        <td style="text-align: right;"><fmt:formatNumber value="${d.distanceInMiles}" type="number" minFractionDigits="3" maxFractionDigits="3" /></td>
                        <td style="text-align: right;"><fmt:formatNumber value="${d.distanceInKilometers}" type="number" minFractionDigits="3" maxFractionDigits="3" /></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>
