<%--
 (#)users.jsp    0.2.0   07/10/2026

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
    <title><fmt:message key="jsp.users.title" /></title>
    <link rel="stylesheet" href="<c:url value='/static/css/styles.css' />" />
  </head>
  <body>
    <h2><fmt:message key="jsp.users.title" /> <c:out value="${requestScope.projectId}" /></h2>

    <table>
      <thead>
        <tr>
          <th><fmt:message key="jsp.users.userId" /></th>
          <th><fmt:message key="jsp.users.firstName" /></th>
          <th><fmt:message key="jsp.users.lastName" /></th>
          <th><fmt:message key="jsp.users.email" /></th>
          <th><fmt:message key="jsp.users.age" /></th>
          <th><fmt:message key="jsp.users.role" /></th>
          <th><fmt:message key="jsp.users.createdAt" /></th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="u" items="${requestScope.users}">
          <tr>
            <td style="text-align: center;"><c:out value="${u.userId}" /></td>
            <td style="text-align: center;"><c:out value="${u.firstName}" /></td>
            <td style="text-align: center;"><c:out value="${u.lastName}" /></td>
            <td style="text-align: center;"><c:out value="${u.email}" /></td>
            <td style="text-align: center;"><c:out value="${u.age}" /></td>
            <td style="text-align: center;"><c:out value="${u.role}" /></td>
            <td style="text-align: center;"><fmt:formatDate value="${u.createdAt}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </body>
</html>
