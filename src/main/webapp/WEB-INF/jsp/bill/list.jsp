<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../header.jsp">
  <jsp:param name="title" value="采购订单管理"/>
</jsp:include>

<%-- 权限控制：管理员/经理有全部权限，采购部可编辑本模块 --%>
<c:set var="isAdminOrManager" value="${sessionScope.roleCode eq 'DSSCM_ADMIN' or sessionScope.roleCode eq 'DSSCM_MANAGER'}"/>
<c:set var="canEdit" value="${isAdminOrManager or sessionScope.roleCode eq 'DSSCM_PURCHASE'}"/>

<div class="card">
  <div class="card-header">
    <h2>采购订单管理</h2>
    <c:if test="${canEdit}">
      <a href="${pageContext.request.contextPath}/bill/add" class="btn btn-primary">新增订单</a>
    </c:if>
  </div>

  <div class="search-form">
    <form action="list" method="get">
      <div class="field"><label>商品名称</label><input type="text" name="productName" value="${productName}" placeholder="输入商品名称"></div>
      <div class="field"><label>供应商</label>
        <select name="providerId">
          <option value="">全部</option>
          <c:forEach items="${providers}" var="p">
            <option value="${p.id}" ${p.id eq providerId ? 'selected' : ''}>${p.proName}</option>
          </c:forEach>
        </select>
      </div>
      <div class="field"><label>支付状态</label>
        <select name="isPayment">
          <option value="">全部</option>
          <option value="1" ${isPayment eq 1 ? 'selected' : ''}>未支付</option>
          <option value="2" ${isPayment eq 2 ? 'selected' : ''}>已支付</option>
        </select>
      </div>
      <div class="field"><button type="submit" class="btn btn-primary">搜索</button></div>
    </form>
  </div>

  <div class="table-responsive">
    <table>
      <tr><th>账单编码</th><th>商品名称</th><th>数量</th><th>总价</th><th>支付</th><th>审批状态</th><th>操作</th></tr>
      <c:forEach items="${bills}" var="b">
        <c:set var="isOwnDraft" value="${b.approvalStatus eq 0 and b.createdBy eq sessionScope.loginUser.id}"/>
        <tr>
          <td>${b.billCode}</td>
          <td>${b.productName}</td>
          <td>${b.productCount}</td>
          <td>${b.totalPrice}</td>
          <td>${b.isPayment eq 1 ? '未支付' : '已支付'}</td>
          <td>
            <c:choose>
              <c:when test="${b.approvalStatus eq 0}"><span class="status-badge secondary">草稿</span></c:when>
              <c:when test="${b.approvalStatus eq 1}"><span class="status-badge warning">待审批</span></c:when>
              <c:when test="${b.approvalStatus eq 2}"><span class="status-badge success">已通过</span></c:when>
              <c:when test="${b.approvalStatus eq 3}"><span class="status-badge danger">已驳回</span></c:when>
              <c:otherwise><span class="status-badge">未知</span></c:otherwise>
            </c:choose>
          </td>
          <td>
            <div class="action-group">
              <a href="view/${b.id}" class="btn btn-sm btn-primary">详情</a>
              <%-- 修改按钮：管理员/经理直接改，部门角色可改(草稿直接改/已审批进编辑页) --%>
              <c:choose>
                <c:when test="${isAdminOrManager}">
                  <a href="edit/${b.id}" class="btn btn-sm btn-primary">修改</a>
                </c:when>
                <c:when test="${canEdit and isOwnDraft}">
                  <a href="edit/${b.id}" class="btn btn-sm btn-primary">修改</a>
                </c:when>
                <c:when test="${canEdit}">
                  <a href="edit/${b.id}" class="btn btn-sm btn-warning">修改</a>
                </c:when>
              </c:choose>
              <%-- 删除按钮：管理员/经理直接删，部门角色仅自己的草稿可删，其它走申请 --%>
              <c:choose>
                <c:when test="${isAdminOrManager}">
                  <a href="javascript:void(0);" onclick="confirmDelete('delete/${b.id}')" class="btn btn-sm btn-danger">删除</a>
                </c:when>
                <c:when test="${canEdit and isOwnDraft}">
                  <a href="javascript:void(0);" onclick="confirmDelete('delete/${b.id}')" class="btn btn-sm btn-danger">删除</a>
                </c:when>
                <c:when test="${canEdit}">
                  <a href="javascript:void(0);" onclick="openApplyModal('bill','delete',${b.id})" class="btn btn-sm btn-warning">申请删除</a>
                </c:when>
              </c:choose>
              <%-- 提交审批（仅草稿） --%>
              <c:if test="${b.approvalStatus eq 0 and (isAdminOrManager or (canEdit and b.createdBy eq sessionScope.loginUser.id))}">
                <a href="javascript:void(0);" onclick="submitApproval('bill',${b.id})" class="btn btn-sm btn-warning">提交审批</a>
              </c:if>
            </div>
          </td>
        </tr>
      </c:forEach>
    </table>
  </div>

  <c:set var="searchParams" value="productName=${productName}&amp;providerId=${providerId}&amp;isPayment=${isPayment}"/>
  <jsp:include page="../include/pagination.jsp"/>
</div>

<jsp:include page="../include/apply_modal.jsp"/>
<script>
  function confirmDelete(url) { if (confirm('确认删除该订单吗？')) location.href = url; }
  function submitApproval(module, id) {
    if (confirm('确认提交审批吗？')) {
      fetch('${pageContext.request.contextPath}/approval/workbench/submit', {
        method:'POST', headers:{'Content-Type':'application/x-www-form-urlencoded'},
        body:'module='+module+'&id='+id
      }).then(function(r){return r.text();}).then(function(d){
        if(d==='ok'){alert('已提交审批');location.reload();}else{alert('提交失败：'+d);}
      });
    }
  }
</script>
<jsp:include page="../footer.jsp"/>
