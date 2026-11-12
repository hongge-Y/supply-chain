<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="currentURI" value="${pageContext.request.requestURI}" />
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${param.title} - 百货中心供应链管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/statics/css/style.css">
</head>
<body>
<header class="topbar">
    <div style="display:flex;align-items:center;gap:10px;">
        <button class="hamburger" onclick="toggleSidebar()">☰</button>
        <div class="logo">百货中心供应链管理系统</div>
    </div>
    <div class="user-info">
        <span class="user-name">${sessionScope.loginUser.userName}</span>
        <a href="${pageContext.request.contextPath}/logout">退出</a>
    </div>
</header>

<nav class="sidebar" id="sidebar">
    <a href="${pageContext.request.contextPath}/main" class="${fn:contains(currentURI,'/main')?' active':''}" data-icon="🏠">首页</a>
    <c:if test="${sessionScope.roleCode eq 'DSSCM_ADMIN' or sessionScope.roleCode eq 'DSSCM_MANAGER' or sessionScope.roleCode eq 'DSSCM_PERSONNEL'}">
        <a href="${pageContext.request.contextPath}/user/list" class="${fn:contains(currentURI,'/user/')?' active':''}" data-icon="👥">用户管理</a>
    </c:if>
    <c:if test="${sessionScope.roleCode eq 'DSSCM_ADMIN' or sessionScope.roleCode eq 'DSSCM_MANAGER' or sessionScope.roleCode eq 'DSSCM_PURCHASE'}">
        <a href="${pageContext.request.contextPath}/provider/list" class="${fn:contains(currentURI,'/provider/')?' active':''}" data-icon="🏭">供应商管理</a>
        <a href="${pageContext.request.contextPath}/bill/list" class="${fn:contains(currentURI,'/bill/')?' active':''}" data-icon="📦">采购订单管理</a>
    </c:if>
    <c:if test="${sessionScope.roleCode eq 'DSSCM_ADMIN' or sessionScope.roleCode eq 'DSSCM_MANAGER' or sessionScope.roleCode eq 'DSSCM_MATERIAL'}">
        <a href="${pageContext.request.contextPath}/product/list" class="${fn:contains(currentURI,'/product/')?' active':''}" data-icon="🛍️">商品管理</a>
    </c:if>
    <c:if test="${sessionScope.roleCode eq 'DSSCM_ADMIN' or sessionScope.roleCode eq 'DSSCM_MANAGER' or sessionScope.roleCode eq 'DSSCM_SALES'}">
        <a href="${pageContext.request.contextPath}/order/list" class="${fn:contains(currentURI,'/order/')?' active':''}" data-icon="💰">销售订单管理</a>
    </c:if>
    <a href="${pageContext.request.contextPath}/news/list" class="${fn:contains(currentURI,'/news/')?' active':''}" data-icon="📰">新闻管理</a>
    <a href="${pageContext.request.contextPath}/statistics/index" class="${fn:contains(currentURI,'/statistics/')?' active':''}" data-icon="📊">经营统计</a>
    <c:if test="${sessionScope.roleCode eq 'DSSCM_ADMIN' or sessionScope.roleCode eq 'DSSCM_MANAGER'}">
        <a href="${pageContext.request.contextPath}/approval/workbench/index" class="${fn:contains(currentURI,'/approval/')?' active':''}" data-icon="✅">审批工作台<c:if test="${sessionScope.pendingApprovalCount > 0}"><span class="approval-badge">${sessionScope.pendingApprovalCount > 99 ? '99+' : sessionScope.pendingApprovalCount}</span></c:if></a>
    </c:if>
</nav>

<script>
    function toggleSidebar(){document.getElementById('sidebar').classList.toggle('open')}
    document.addEventListener('click',function(e){
        var s=document.getElementById('sidebar'),h=document.querySelector('.hamburger');
        if(window.innerWidth<=768&&s.classList.contains('open')&&!s.contains(e.target)&&!h.contains(e.target))s.classList.remove('open')
    });
</script>
<main class="main-content">
