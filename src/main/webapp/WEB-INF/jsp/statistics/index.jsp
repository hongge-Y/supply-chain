<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../header.jsp">
  <jsp:param name="title" value="经营统计"/>
</jsp:include>
<style>
.stat-grid { display:grid; grid-template-columns:repeat(4,1fr); gap:20px; padding:20px 0; }
.stat-card { background:#fff; border-radius:12px; padding:28px 20px; text-align:center; box-shadow:0 1px 3px rgba(0,0,0,0.06); }
.stat-card .stat-label { font-size:13px; color:#64748b; margin-bottom:8px; }
.stat-card .stat-value { font-size:32px; font-weight:700; color:#1e293b; }
.stat-card.blue .stat-value { color:#3b82f6; }
.stat-card.green .stat-value { color:#10b981; }
.stat-card.orange .stat-value { color:#f59e0b; }
.stat-card.purple .stat-value { color:#8b5cf6; }
</style>
<div class="card">
  <div class="card-header"><h2>经营统计</h2></div>
  <div class="stat-grid">
    <div class="stat-card blue"><div class="stat-label">销售订单</div><div class="stat-value">${totalOrders}</div></div>
    <div class="stat-card green"><div class="stat-label">采购订单</div><div class="stat-value">${totalBills}</div></div>
    <div class="stat-card orange"><div class="stat-label">商品种类</div><div class="stat-value">${totalProducts}</div></div>
    <div class="stat-card purple"><div class="stat-label">供应商</div><div class="stat-value">${totalProviders}</div></div>
  </div>
</div>
<jsp:include page="../footer.jsp"/>
