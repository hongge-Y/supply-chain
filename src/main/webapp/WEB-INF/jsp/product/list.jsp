<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../header.jsp">
  <jsp:param name="title" value="商品管理"/>
</jsp:include>

<%-- 权限控制：管理员/经理有全部权限，物资部可编辑本模块 --%>
<c:set var="isAdminOrManager" value="${sessionScope.roleCode eq 'DSSCM_ADMIN' or sessionScope.roleCode eq 'DSSCM_MANAGER'}"/>
<c:set var="canEdit" value="${isAdminOrManager or sessionScope.roleCode eq 'DSSCM_MATERIAL'}"/>

<div class="card">
  <div class="card-header">
    <h2>商品管理</h2>
    <div>
      <c:if test="${canEdit}">
        <a href="${pageContext.request.contextPath}/product/add" class="btn btn-primary">新增商品</a>
      </c:if>
      <a href="${pageContext.request.contextPath}/product/category/list" class="btn btn-primary">分类管理</a>
    </div>
  </div>

  <c:if test="${not empty error}">
    <div class="alert alert-error">${error}</div>
  </c:if>

  <div class="search-form">
    <form action="list" method="get">
      <div class="field">
        <label>名称</label>
        <input type="text" name="name" value="${name}" placeholder="输入商品名称">
      </div>
      <div class="field">
        <label>一级分类</label>
        <select name="categoryLevel1Id">
          <option value="">全部</option>
          <c:forEach items="${level1List}" var="c1">
            <option value="${c1.id}" ${c1.id eq categoryLevel1Id ? 'selected' : ''}>${c1.name}</option>
          </c:forEach>
        </select>
      </div>
      <div class="field">
        <button type="submit" class="btn btn-primary">搜索</button>
      </div>
    </form>
  </div>

  <div class="table-responsive">
    <table>
      <tr><th>名称</th><th>描述</th><th>价格</th><th>库存</th><th>位置</th><th>审批状态</th><th>操作</th></tr>
      <c:forEach items="${products}" var="p">
        <c:set var="isOwnDraft" value="${p.approvalStatus eq 0 and p.createdBy eq sessionScope.loginUser.id}"/>
        <tr>
          <td>${p.name}</td>
          <td>${p.description}</td>
          <td>${p.price}</td>
          <td>${p.stock}</td>
          <td>${p.placement}</td>
          <td>
            <c:choose>
              <c:when test="${p.approvalStatus eq 0}"><span class="status-badge secondary">草稿</span></c:when>
              <c:when test="${p.approvalStatus eq 1}"><span class="status-badge warning">待审批</span></c:when>
              <c:when test="${p.approvalStatus eq 2}"><span class="status-badge success">已通过</span></c:when>
              <c:when test="${p.approvalStatus eq 3}"><span class="status-badge danger">已驳回</span></c:when>
              <c:otherwise><span class="status-badge">未知</span></c:otherwise>
            </c:choose>
          </td>
          <td>
            <div class="action-group">
              <a href="view/${p.id}" class="btn btn-sm btn-primary">详情</a>
              <%-- 修改按钮 --%>
              <c:choose>
                <c:when test="${isAdminOrManager}">
                  <a href="edit/${p.id}" class="btn btn-sm btn-primary">修改</a>
                </c:when>
                <c:when test="${canEdit and isOwnDraft}">
                  <a href="edit/${p.id}" class="btn btn-sm btn-primary">修改</a>
                </c:when>
                <c:when test="${canEdit}">
                  <a href="edit/${p.id}" class="btn btn-sm btn-warning">修改</a>
                </c:when>
              </c:choose>
              <%-- 删除按钮 --%>
              <c:choose>
                <c:when test="${isAdminOrManager}">
                  <a href="javascript:void(0);" onclick="confirmDelete('delete/${p.id}')" class="btn btn-sm btn-danger">删除</a>
                </c:when>
                <c:when test="${canEdit and isOwnDraft}">
                  <a href="javascript:void(0);" onclick="confirmDelete('delete/${p.id}')" class="btn btn-sm btn-danger">删除</a>
                </c:when>
                <c:when test="${canEdit}">
                  <a href="javascript:void(0);" onclick="openApplyModal('product','delete',${p.id})" class="btn btn-sm btn-warning">申请删除</a>
                </c:when>
              </c:choose>
              <%-- 提交审批（仅草稿） --%>
              <c:if test="${p.approvalStatus eq 0 and (isAdminOrManager or (canEdit and p.createdBy eq sessionScope.loginUser.id))}">
                <a href="javascript:void(0);" onclick="submitApproval('product',${p.id})" class="btn btn-sm btn-warning">提交审批</a>
              </c:if>
            </div>
          </td>
        </tr>
      </c:forEach>
    </table>
  </div>

  <c:set var="searchParams" value="name=${name}&amp;categoryLevel1Id=${categoryLevel1Id}"/>
  <jsp:include page="../include/pagination.jsp"/>
</div>

<jsp:include page="../include/apply_modal.jsp"/>
<script>
  function confirmDelete(url) {
    if (confirm('确认删除该商品吗？')) location.href = url;
  }
  function submitApproval(module, id) {
    if (confirm('确认提交审批吗？')) {
      fetch('${pageContext.request.contextPath}/approval/workbench/submit', {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: 'module=' + module + '&id=' + id
      }).then(function(res) { return res.text(); }).then(function(data) {
        if (data === 'ok') { alert('已提交审批'); location.reload(); }
        else { alert('提交失败：' + data); }
      });
    }
  }
</script>
<jsp:include page="../footer.jsp"/>
