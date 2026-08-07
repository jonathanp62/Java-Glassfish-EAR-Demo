<%--
 (#)index.jsp   0.4.0   07/14/2026
 (#)index.jsp   0.3.0   07/12/2026
 (#)index.jsp   0.2.0   07/09/2026
 (#)index.jsp   0.1.0   07/04/2026

 @author   Jonathan Parker
 @version  0.4.0
 @since    0.1.0

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
        <title><fmt:message key="jsp.index.title" /></title>
        <%-- The stylesheet can also be accessed at /resources/css/styles.css --%>
        <link rel="stylesheet" href="<c:url value='/static/css/styles.css' />" />
    </head>
    <body>
        <h2><fmt:message key="jsp.index.title" /></h2>
        <p><a href="<c:url value='/servlet/cars' />"><fmt:message key="jsp.index.cars" /></a></p>
        <p><a href="<c:url value='/servlet/distance' />"><fmt:message key="jsp.index.distance" /></a></p>
        <p><a href="<c:url value='/servlet/form' />"><fmt:message key="jsp.index.form.demo" /></a></p>
        <p><a href="<c:url value='/servlet/hello' />"><fmt:message key="jsp.index.hello" /></a></p>
        <p><a href="<c:url value='/servlet/people' />"><fmt:message key="jsp.index.people" /></a></p>
        <p><a href="<c:url value='/servlet/projects' />"><fmt:message key="jsp.index.projects" /></a></p>
        <p><a href="<c:url value='/servlet/register' />"><fmt:message key="jsp.index.register" /></a></p>
        <p><a href="<c:url value='/jsf/signup.xhtml' />"><fmt:message key="jsp.index.signup" /></a></p>
        <p><a href="<c:url value='/servlet/welcome-jsf' />"><fmt:message key="jsp.index.welcome.jsf" /></a></p>
        <p>Version: <c:out value="${initParam.appVersion}" /></p>
    </body>
</html>
