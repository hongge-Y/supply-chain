<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp"><jsp:param name="title" value="首页"/></jsp:include>

<style>
.dash-header{
    background:linear-gradient(135deg,#1a365d,#2d4a7a);
    border-radius:14px;padding:36px 32px;margin-bottom:24px;
    display:flex;justify-content:space-between;align-items:center
}
.dash-header .greeting h1{font-size:22px;font-weight:600;color:#fff;margin-bottom:4px}
.dash-header .greeting p{color:rgba(255,255,255,0.55);font-size:13px}
.dash-header .role-badge{
    background:rgba(255,255,255,0.1);padding:6px 16px;border-radius:20px;
    color:rgba(255,255,255,0.8);font-size:12px;letter-spacing:0.5px
}
.news-card{background:#fff;border-radius:10px;padding:20px 24px;box-shadow:0 1px 3px rgba(0,0,0,0.06)}
.news-card .nc-header{font-size:15px;font-weight:600;color:#0f172a;padding-bottom:12px;border-bottom:1px solid #f1f5f9;margin-bottom:8px}
</style>

<div class="dash-header">
    <div class="greeting">
        <h1>欢迎回来，${sessionScope.loginUser.userName}</h1>
        <p>
            <c:choose>
                <c:when test="${sessionScope.roleCode eq 'DSSCM_ADMIN'}">系统管理员</c:when>
                <c:when test="${sessionScope.roleCode eq 'DSSCM_MANAGER'}">经理</c:when>
                <c:when test="${sessionScope.roleCode eq 'DSSCM_PERSONNEL'}">人事部员工</c:when>
                <c:when test="${sessionScope.roleCode eq 'DSSCM_PURCHASE'}">采购部员工</c:when>
                <c:when test="${sessionScope.roleCode eq 'DSSCM_MATERIAL'}">物资部员工</c:when>
                <c:when test="${sessionScope.roleCode eq 'DSSCM_SALES'}">销售部员工</c:when>
                <c:when test="${sessionScope.roleCode eq 'DSSCM_EMPLOYEE'}">普通员工</c:when>
                <c:otherwise>未知</c:otherwise>
            </c:choose>
        </p>
    </div>
    <div class="role-badge">${sessionScope.loginUser.userCode}</div>
</div>

<div class="quick-grid">
    <c:if test="${sessionScope.roleCode eq 'DSSCM_ADMIN' or sessionScope.roleCode eq 'DSSCM_MANAGER' or sessionScope.roleCode eq 'DSSCM_PERSONNEL'}">
        <a href="${pageContext.request.contextPath}/user/list" class="quick-card"><div class="quick-title">用户管理</div></a>
    </c:if>
    <c:if test="${sessionScope.roleCode eq 'DSSCM_ADMIN' or sessionScope.roleCode eq 'DSSCM_MANAGER' or sessionScope.roleCode eq 'DSSCM_PURCHASE'}">
        <a href="${pageContext.request.contextPath}/provider/list" class="quick-card"><div class="quick-title">供应商管理</div></a>
        <a href="${pageContext.request.contextPath}/bill/list" class="quick-card"><div class="quick-title">采购订单</div></a>
    </c:if>
    <c:if test="${sessionScope.roleCode eq 'DSSCM_ADMIN' or sessionScope.roleCode eq 'DSSCM_MANAGER' or sessionScope.roleCode eq 'DSSCM_MATERIAL'}">
        <a href="${pageContext.request.contextPath}/product/list" class="quick-card"><div class="quick-title">商品管理</div></a>
    </c:if>
    <c:if test="${sessionScope.roleCode eq 'DSSCM_ADMIN' or sessionScope.roleCode eq 'DSSCM_MANAGER' or sessionScope.roleCode eq 'DSSCM_SALES'}">
        <a href="${pageContext.request.contextPath}/order/list" class="quick-card"><div class="quick-title">销售订单</div></a>
    </c:if>
    <c:if test="${sessionScope.roleCode eq 'DSSCM_ADMIN' or sessionScope.roleCode eq 'DSSCM_MANAGER'}">
        <a href="${pageContext.request.contextPath}/approval/workbench/index" class="quick-card"><div class="quick-title">审批工作台</div></a>
    </c:if>
    <a href="${pageContext.request.contextPath}/statistics/index" class="quick-card"><div class="quick-title">经营统计</div></a>
</div>

<c:if test="${not empty latestNews}">
<div class="news-card">
    <div class="nc-header" style="display:flex;justify-content:space-between;align-items:center">
        <span>新闻</span>
        <a href="${pageContext.request.contextPath}/news/list" style="font-size:12px;color:#3b82f6;font-weight:400;text-decoration:none">更多 →</a>
    </div>
    <c:forEach items="${latestNews}" var="news">
        <div style="display:flex;justify-content:space-between;padding:8px 0;border-bottom:1px solid #f1f5f9;font-size:13px">
            <span style="color:#334155">${news.title}</span>
            <span style="color:#94a3b8;font-size:12px">${news.creationDate}</span>
        </div>
    </c:forEach>
</div>
</c:if>
<jsp:include page="footer.jsp"/>
