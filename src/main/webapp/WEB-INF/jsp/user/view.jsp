<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="用户详情"/>
</jsp:include>

<div class="card form-card detail-view">
    <div class="card-header"><h2>👤 用户详情</h2></div>
    <table class="detail-table">
        <tr><td class="detail-label">编码</td><td class="detail-value">${user.userCode}</td></tr>
        <tr><td class="detail-label">姓名</td><td class="detail-value">${user.userName}</td></tr>
        <tr><td class="detail-label">性别</td><td class="detail-value">${user.gender eq 1 ? '女' : '男'}</td></tr>
        <tr><td class="detail-label">出生日期</td><td class="detail-value">${user.birthday}</td></tr>
        <tr><td class="detail-label">电话</td><td class="detail-value">${user.phone}</td></tr>
        <tr><td class="detail-label">邮箱</td><td class="detail-value">${user.email}</td></tr>
        <tr><td class="detail-label">地址</td><td class="detail-value">${user.address}</td></tr>
        <tr>
            <td class="detail-label">审批状态</td>
            <td class="detail-value">
                <c:choose>
                    <c:when test="${user.status eq 0 or user.status eq 3}"><span class="status-badge warning">待审批</span></c:when>
                    <c:when test="${user.status eq 1}"><span class="status-badge success">已通过</span></c:when>
                    <c:when test="${user.status eq 2}"><span class="status-badge danger">已驳回</span></c:when>
                    <c:otherwise>未知</c:otherwise>
                </c:choose>
            </td>
        </tr>
        <tr>
            <td class="detail-label">角色</td>
            <td class="detail-value">
                <span class="status-badge info">
                    <c:forEach items="${roles}" var="r">
                        <c:if test="${r.id eq user.userRole}">${r.roleName}</c:if>
                    </c:forEach>
                </span>
            </td>
        </tr>
    </table>
    <div class="form-actions">
        <a href="${pageContext.request.contextPath}/user/list" class="btn btn-primary">← 返回列表</a>
    </div>
</div>
<jsp:include page="../footer.jsp"/>
