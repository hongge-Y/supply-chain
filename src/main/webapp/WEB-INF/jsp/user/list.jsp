<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../header.jsp">
  <jsp:param name="title" value="用户管理"/>
</jsp:include>

<c:set var="isAdminOrManager" value="${sessionScope.roleCode eq 'DSSCM_ADMIN' or sessionScope.roleCode eq 'DSSCM_MANAGER'}"/>
<c:set var="canEdit" value="${isAdminOrManager or sessionScope.roleCode eq 'DSSCM_PERSONNEL'}"/>

<div class="card">
  <div class="card-header">
    <h2>用户管理</h2>
    <c:if test="${canEdit}">
      <a href="${pageContext.request.contextPath}/user/add" class="btn btn-primary">新增用户</a>
    </c:if>
  </div>

  <c:if test="${param.error eq 'needapproval'}">
    <div class="alert alert-warning" style="padding:10px 20px;margin:0 20px;background:#fef3c7;border-radius:8px;color:#92400e;font-size:14px;">
      该用户已激活，需要提交审批申请才能修改或删除
    </div>
  </c:if>

  <div class="search-form">
    <form action="list" method="get">
      <div class="field">
        <label>姓名</label>
        <input type="text" name="userName" value="${userName}" placeholder="输入姓名">
      </div>
      <div class="field">
        <label>角色</label>
        <select name="roleId">
          <option value="">全部</option>
          <c:forEach items="${roles}" var="role">
            <option value="${role.id}" ${role.id eq roleId ? 'selected' : ''}>${role.roleName}</option>
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
      <tr><th>编码</th><th>姓名</th><th>性别</th><th>电话</th><th>角色</th><th>状态</th><th>操作</th></tr>
      <c:forEach items="${users}" var="u">
        <tr>
          <td>${u.userCode}</td>
          <td>${u.userName}</td>
          <td>${u.gender eq 1 ? '女' : '男'}</td>
          <td>${u.phone}</td>
          <td>
            <c:forEach items="${roles}" var="r">
              <c:if test="${r.id eq u.userRole}">${r.roleName}</c:if>
            </c:forEach>
          </td>
          <td>
            <c:choose>
              <c:when test="${u.status eq 0 or u.status eq 3}"><span class="status-badge warning">待审批</span></c:when>
              <c:when test="${u.status eq 1}"><span class="status-badge success">已通过</span></c:when>
              <c:when test="${u.status eq 2}"><span class="status-badge danger">已驳回</span></c:when>
              <c:otherwise>未知</c:otherwise>
            </c:choose>
          </td>
          <td>
            <div class="action-group">
              <a href="view/${u.id}" class="btn btn-sm btn-primary">详情</a>
              <c:if test="${canEdit}">
                  <a href="edit/${u.id}" class="btn btn-sm btn-primary">修改</a>
                <c:choose>
                  <c:when test="${isAdminOrManager}">
                    <a href="javascript:void(0);" onclick="confirmDelete('delete/${u.id}')" class="btn btn-sm btn-danger">删除</a>
                  </c:when>
                  <c:otherwise>
                    <a href="javascript:void(0);" onclick="openApplyModal('user','delete',${u.id})" class="btn btn-sm btn-warning">申请删除</a>
                  </c:otherwise>
                </c:choose>
              </c:if>
            </div>
          </td>
        </tr>
      </c:forEach>
    </table>
  </div>

  <c:set var="searchParams" value="userName=${userName}&amp;roleId=${roleId}"/>
  <jsp:include page="../include/pagination.jsp"/>
</div>

<jsp:include page="../include/apply_modal.jsp"/>
<script>
  function confirmDelete(url) {
    if (confirm('确认删除该用户吗？')) location.href = url;
  }
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
