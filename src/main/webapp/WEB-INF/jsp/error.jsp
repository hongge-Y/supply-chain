<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<jsp:include page="header.jsp">
    <jsp:param name="title" value="错误"/>
</jsp:include>

<div class="error-page">
    <div class="error-card">
        <h2>操作失败</h2>
        <p>${message}</p>
        <div class="error-actions">
            <c:if test="${not empty url}">
                <a href="${url}" class="btn btn-primary">返回上一页</a>
            </c:if>
            <a href="${pageContext.request.contextPath}/main" class="btn btn-outline">返回首页</a>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"/>
