<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../header.jsp">
  <jsp:param name="title" value="新闻管理"/>
</jsp:include>

<%-- 权限控制：新闻模块 --%>
<%-- 销售部(7)/管理员(1)/经理(2)：显示新增/修改/删除 --%>
<%-- 人事部(4)/采购部(5)/物资部(6)：只显示查看 --%>
<%-- 新闻无审批流，直接发布 --%>
<c:set var="isAdminOrManager" value="${sessionScope.roleCode eq 'DSSCM_ADMIN' or sessionScope.roleCode eq 'DSSCM_MANAGER'}"/>
<c:set var="canEdit" value="${isAdminOrManager or sessionScope.roleCode eq 'DSSCM_SALES'}"/>
<c:set var="canDelete" value="${isAdminOrManager or sessionScope.roleCode eq 'DSSCM_SALES'}"/>

<div class="card">
  <div class="card-header">
    <h2>新闻管理</h2>
    <c:if test="${canEdit}">
      <a href="${pageContext.request.contextPath}/news/add" class="btn btn-primary">发布新闻</a>
    </c:if>
  </div>

  <div class="search-form">
    <form action="list" method="get">
      <div class="field">
        <label>标题</label>
        <input type="text" name="title" value="${title}" placeholder="输入标题关键字">
      </div>
      <div class="field">
        <button type="submit" class="btn btn-primary">搜索</button>
      </div>
    </form>
  </div>

  <div class="table-responsive">
    <table>
      <tr><th>标题</th><th>创建时间</th><th>审批状态</th><th>操作</th></tr>
      <c:forEach items="${newsList}" var="n">
        <tr>
          <td>${n.title}</td>
          <td>${n.creationDate}</td>
          <td>
            <c:choose>
              <c:when test="${n.approvalStatus eq 0}"><span class="status-badge secondary">草稿</span></c:when>
              <c:when test="${n.approvalStatus eq 1}"><span class="status-badge warning">待审批</span></c:when>
              <c:when test="${n.approvalStatus eq 2}"><span class="status-badge success">已通过</span></c:when>
              <c:when test="${n.approvalStatus eq 3}"><span class="status-badge danger">已驳回</span></c:when>
              <c:otherwise>未知</c:otherwise>
            </c:choose>
          </td>
          <td>
            <div class="action-group">
              <a href="view/${n.id}" class="btn btn-sm btn-primary">详情</a>
              <c:if test="${canEdit}">
                <a href="edit/${n.id}" class="btn btn-sm btn-primary">修改</a>
              </c:if>
              <c:if test="${canDelete}">
                <a href="javascript:void(0);" onclick="confirmDelete('delete/${n.id}')" class="btn btn-sm btn-danger">删除</a>
              </c:if>
            </div>
          </td>
        </tr>
      </c:forEach>
    </table>
  </div>

  <c:set var="searchParams" value="title=${title}"/>
  <jsp:include page="../include/pagination.jsp"/>
</div>

<jsp:include page="../include/apply_modal.jsp"/>
<script>
  function confirmDelete(url) {
    if (confirm('确认删除该新闻吗？')) location.href = url;
  }
</script>
<jsp:include page="../footer.jsp"/>
