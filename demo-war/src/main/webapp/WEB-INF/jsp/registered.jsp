<%--
 (#)registered.jsp  0.2.0   07/11/2026

 @author   Jonathan Parker
 @version  0.2.0
 @since    0.2.0

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
        <title><fmt:message key="jsp.registered.title" /></title>
        <link rel="stylesheet" href="<c:url value='/static/css/styles.css' />" />
    </head>
    <body>
        <h2><fmt:message key="jsp.registered.title" /></h2>

        <c:if test="${not empty requestScope.successMessage}">
            <p><h3><c:out value="${requestScope.successMessage}" /></h3></p>
        </c:if>

        <c:if test="${not empty requestScope.errors}">
            <h3><fmt:message key="jsp.register.validation.errors" /></h3>
            <ul>
                <c:forEach var="err" items="${requestScope.errors}">
                    <li><c:out value="${err}" /></li>
                </c:forEach>
            </ul>
        </c:if>
    </body>
</html>
