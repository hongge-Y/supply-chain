<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../header.jsp">
  <jsp:param name="title" value="销售订单管理"/>
</jsp:include>

<%-- 权限控制：管理员/经理有全部权限，销售部可编辑本模块 --%>
<c:set var="isAdminOrManager" value="${sessionScope.roleCode eq 'DSSCM_ADMIN' or sessionScope.roleCode eq 'DSSCM_MANAGER'}"/>
<c:set var="canEdit" value="${isAdminOrManager or sessionScope.roleCode eq 'DSSCM_SALES'}"/>

<div class="card">
  <div class="card-header">
    <h2>销售订单管理</h2>
    <c:if test="${canEdit}">
      <a href="${pageContext.request.contextPath}/order/add" class="btn btn-primary">新增订单</a>
    </c:if>
  </div>

  <div class="search-form">
    <form action="list" method="get">
      <div class="field">
        <label>顾客姓名</label>
        <input type="text" name="userName" value="${userName}" placeholder="输入姓名">
      </div>
      <div class="field">
        <label>订单状态</label>
        <select name="status">
          <option value="">全部</option>
          <option value="1" ${status eq 1 ? 'selected' : ''}>待审核</option>
          <option value="2" ${status eq 2 ? 'selected' : ''}>审核通过</option>
          <option value="3" ${status eq 3 ? 'selected' : ''}>配货</option>
          <option value="4" ${status eq 4 ? 'selected' : ''}>卖家已发货</option>
          <option value="5" ${status eq 5 ? 'selected' : ''}>已收货</option>
        </select>
      </div>
      <div class="field">
        <button type="submit" class="btn btn-primary">搜索</button>
      </div>
    </form>
  </div>

  <div class="table-responsive">
    <table>
      <tr><th>订单号</th><th>顾客</th><th>数量</th><th>总价</th><th>状态</th><th>审批状态</th><th>操作</th></tr>
      <c:forEach items="${orders}" var="o">
        <c:set var="isOwnDraft" value="${o.approvalStatus eq 0 and o.createdBy eq sessionScope.loginUser.id}"/>
        <tr>
          <td>${o.serialNumber}</td>
          <td>${o.userName}</td>
          <td>${o.proCount}</td>
          <td>${o.cost}</td>
          <td>
            <c:choose>
              <c:when test="${o.status eq 1}"><span class="status-badge warning">待审核</span></c:when>
              <c:when test="${o.status eq 2}"><span class="status-badge info">审核通过</span></c:when>
              <c:when test="${o.status eq 3}"><span class="status-badge purple">配货</span></c:when>
              <c:when test="${o.status eq 4}"><span class="status-badge success">卖家已发货</span></c:when>
              <c:when test="${o.status eq 5}"><span class="status-badge success">已收货</span></c:when>
              <c:otherwise><span class="status-badge">未知</span></c:otherwise>
            </c:choose>
          </td>
          <td>
            <c:choose>
              <c:when test="${o.approvalStatus eq 0}"><span class="status-badge secondary">草稿</span></c:when>
              <c:when test="${o.approvalStatus eq 1}"><span class="status-badge warning">待审批</span></c:when>
              <c:when test="${o.approvalStatus eq 2}"><span class="status-badge success">已通过</span></c:when>
              <c:when test="${o.approvalStatus eq 3}"><span class="status-badge danger">已驳回</span></c:when>
              <c:otherwise><span class="status-badge">未知</span></c:otherwise>
            </c:choose>
          </td>
          <td>
            <div class="action-group">
              <a href="view/${o.id}" class="btn btn-sm btn-primary">详情</a>
              <%-- 修改按钮 --%>
              <c:choose>
                <c:when test="${isAdminOrManager}">
                  <a href="edit/${o.id}" class="btn btn-sm btn-primary">修改</a>
                </c:when>
                <c:when test="${canEdit and isOwnDraft}">
                  <a href="edit/${o.id}" class="btn btn-sm btn-primary">修改</a>
                </c:when>
                <c:when test="${canEdit}">
                  <a href="edit/${o.id}" class="btn btn-sm btn-warning">修改</a>
                </c:when>
              </c:choose>
              <%-- 删除按钮 --%>
              <c:choose>
                <c:when test="${isAdminOrManager}">
                  <a href="javascript:void(0);" onclick="confirmDelete('delete/${o.id}')" class="btn btn-sm btn-danger">删除</a>
                </c:when>
                <c:when test="${canEdit and isOwnDraft}">
                  <a href="javascript:void(0);" onclick="confirmDelete('delete/${o.id}')" class="btn btn-sm btn-danger">删除</a>
                </c:when>
                <c:when test="${canEdit}">
                  <a href="javascript:void(0);" onclick="openApplyModal('order','delete',${o.id})" class="btn btn-sm btn-warning">申请删除</a>
                </c:when>
              </c:choose>
              <%-- 提交审批（仅草稿） --%>
              <c:if test="${o.approvalStatus eq 0 and (isAdminOrManager or (canEdit and o.createdBy eq sessionScope.loginUser.id))}">
                <a href="javascript:void(0);" onclick="submitApproval('order',${o.id})" class="btn btn-sm btn-warning">提交审批</a>
              </c:if>
            </div>
          </td>
        </tr>
      </c:forEach>
    </table>
  </div>

  <c:set var="searchParams" value="userName=${userName}&amp;status=${status}"/>
  <jsp:include page="../include/pagination.jsp"/>
</div>

<jsp:include page="../include/apply_modal.jsp"/>
<script>
  function confirmDelete(url) {
    if (confirm('确认删除该订单吗？')) location.href = url;
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
