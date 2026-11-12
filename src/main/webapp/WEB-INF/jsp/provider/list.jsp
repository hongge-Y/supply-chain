<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../header.jsp">
  <jsp:param name="title" value="供应商管理"/>
</jsp:include>

<%-- 权限控制：管理员/经理有全部权限，采购部可编辑本模块 --%>
<c:set var="isAdminOrManager" value="${sessionScope.roleCode eq 'DSSCM_ADMIN' or sessionScope.roleCode eq 'DSSCM_MANAGER'}"/>
<c:set var="canEdit" value="${isAdminOrManager or sessionScope.roleCode eq 'DSSCM_PURCHASE'}"/>

<div class="card">
  <div class="card-header">
    <h2>供应商管理</h2>
    <c:if test="${canEdit}">
      <a href="${pageContext.request.contextPath}/provider/add" class="btn btn-primary">新增供应商</a>
    </c:if>
  </div>

  <div class="search-form">
    <form action="list" method="get">
      <div class="field">
        <label>编码</label>
        <input type="text" name="proCode" value="${proCode}" placeholder="输入编码">
      </div>
      <div class="field">
        <label>名称</label>
        <input type="text" name="proName" value="${proName}" placeholder="输入名称">
      </div>
      <div class="field">
        <button type="submit" class="btn btn-primary">搜索</button>
      </div>
    </form>
  </div>

  <div class="table-responsive">
    <table>
      <tr><th>编码</th><th>名称</th><th>联系人</th><th>电话</th><th>地址</th><th>审批状态</th><th>操作</th></tr>
      <c:forEach items="${providers}" var="p">
        <c:set var="isOwnDraft" value="${p.approvalStatus eq 0 and p.createdBy eq sessionScope.loginUser.id}"/>
        <tr>
          <td>${p.proCode}</td>
          <td>${p.proName}</td>
          <td>${p.proContact}</td>
          <td>${p.proPhone}</td>
          <td>${p.proAddress}</td>
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
                  <a href="javascript:void(0);" onclick="openApplyModal('provider','delete',${p.id})" class="btn btn-sm btn-warning">申请删除</a>
                </c:when>
              </c:choose>
              <%-- 提交审批（仅草稿） --%>
              <c:if test="${p.approvalStatus eq 0 and (isAdminOrManager or (canEdit and p.createdBy eq sessionScope.loginUser.id))}">
                <a href="javascript:void(0);" onclick="submitApproval('provider',${p.id})" class="btn btn-sm btn-warning">提交审批</a>
              </c:if>
            </div>
          </td>
        </tr>
      </c:forEach>
    </table>
  </div>

  <c:set var="searchParams" value="proCode=${proCode}&amp;proName=${proName}"/>
  <jsp:include page="../include/pagination.jsp"/>
</div>

<jsp:include page="../include/apply_modal.jsp"/>
<script>
  function confirmDelete(url) {
    if (confirm('确认删除该供应商吗？')) location.href = url;
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
