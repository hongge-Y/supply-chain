<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../header.jsp">
  <jsp:param name="title" value="供应商详情"/>
</jsp:include>

<div class="card form-card detail-view">
  <div class="card-header"><h2>🚚 供应商详情</h2></div>
  <table class="detail-table">
    <tr><td class="detail-label">编码</td><td class="detail-value">${provider.proCode}</td></tr>
    <tr><td class="detail-label">名称</td><td class="detail-value">${provider.proName}</td></tr>
    <tr><td class="detail-label">描述</td><td class="detail-value">${provider.proDesc}</td></tr>
    <tr><td class="detail-label">联系人</td><td class="detail-value">${provider.proContact}</td></tr>
    <tr><td class="detail-label">电话</td><td class="detail-value">${provider.proPhone}</td></tr>
    <tr><td class="detail-label">地址</td><td class="detail-value">${provider.proAddress}</td></tr>
    <tr><td class="detail-label">传真</td><td class="detail-value">${provider.proFax}</td></tr>
    <tr><td class="detail-label">创建时间</td><td class="detail-value">${provider.creationDate}</td></tr>
  </table>
  <div class="form-actions">
    <a href="${pageContext.request.contextPath}/provider/list" class="btn btn-primary">← 返回列表</a>
  </div>
</div>
<jsp:include page="../footer.jsp"/>
