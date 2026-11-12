<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../header.jsp">
  <jsp:param name="title" value="新闻详情"/>
</jsp:include>

<div class="card form-card detail-view">
  <div class="card-header"><h2>📰 ${news.title}</h2></div>
  <p class="text-muted" style="margin-bottom:16px;font-size:13px;">发布时间：${news.creationDate}</p>
  <p class="text-muted" style="margin-bottom:16px;font-size:13px;">审批状态：
    <c:choose>
      <c:when test="${news.approvalStatus eq 0}"><span class="status-badge secondary">草稿</span></c:when>
      <c:when test="${news.approvalStatus eq 1}"><span class="status-badge warning">待审批</span></c:when>
      <c:when test="${news.approvalStatus eq 2}"><span class="status-badge success">已通过</span></c:when>
      <c:when test="${news.approvalStatus eq 3}"><span class="status-badge danger">已驳回</span></c:when>
      <c:otherwise>未知</c:otherwise>
    </c:choose>
  </p>
  <hr style="margin:0 0 20px;border:none;border-top:1px solid var(--border);">
  <p style="line-height:1.8;">${news.content}</p>
  <div class="form-actions">
    <a href="${pageContext.request.contextPath}/news/list" class="btn btn-primary">← 返回列表</a>
  </div>
</div>
<jsp:include page="../footer.jsp"/>
