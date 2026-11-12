<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="商品分类管理"/>
</jsp:include>

<c:set var="isAdminOrManager" value="${sessionScope.roleCode eq 'DSSCM_ADMIN' or sessionScope.roleCode eq 'DSSCM_MANAGER'}"/>

<div class="card">
    <div class="card-header">
        <h2>
            <c:choose>
                <c:when test="${parentId eq 0}">一级分类</c:when>
                <c:otherwise>子分类（父级：${parentName}）</c:otherwise>
            </c:choose>
        </h2>
        <div class="header-actions">
            <a href="${pageContext.request.contextPath}/product/category/add?parentId=${parentId}" class="btn btn-primary">添加分类</a>
            <a href="${pageContext.request.contextPath}/product/list" class="btn btn-outline">返回商品管理</a>
        </div>
    </div>

    <c:if test="${param.error eq 'needapproval'}">
        <div style="padding:8px 14px;margin:0 20px 12px;background:#fef2f2;border-radius:6px;color:#dc2626;font-size:13px;border:1px solid #fecaca;">
            该操作需要审批，请通过申请流程提交
        </div>
    </c:if>

    <div class="table-responsive">
        <table>
            <tr><th>ID</th><th>名称</th><th>级别</th><th>操作</th></tr>
            <c:forEach items="${categories}" var="cat">
                <tr>
                    <td>${cat.id}</td>
                    <td>
                        <c:choose>
                            <c:when test="${cat.type < 3}">
                                <a href="${pageContext.request.contextPath}/product/category/list?parentId=${cat.id}" style="font-weight:500;">${cat.name}</a>
                            </c:when>
                            <c:otherwise>${cat.name}</c:otherwise>
                        </c:choose>
                    </td>
                    <td><span class="status-badge info">${cat.type}级</span></td>
                    <td>
                        <div class="action-group">
                            <c:if test="${isAdminOrManager}">
                                <a href="${pageContext.request.contextPath}/product/category/edit/${cat.id}" class="btn btn-sm btn-primary">修改</a>
                                <a href="javascript:void(0);" onclick="confirmDelete('${pageContext.request.contextPath}/product/category/delete/${cat.id}?parentId=${parentId}')" class="btn btn-sm btn-danger">删除</a>
                            </c:if>
                            <c:if test="${!isAdminOrManager}">
                                <a href="${pageContext.request.contextPath}/product/category/edit/${cat.id}" class="btn btn-sm btn-primary">修改</a>
                                <a href="javascript:void(0);" onclick="openApplyModal('category','delete',${cat.id})" class="btn btn-sm btn-warning">申请删除</a>
                            </c:if>
                        </div>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <div class="form-actions" style="border:none;padding-top:0;">
        <c:if test="${parentId ne 0}">
            <a href="${pageContext.request.contextPath}/product/category/list?parentId=0" class="btn btn-primary">返回一级分类</a>
        </c:if>
        <a href="${pageContext.request.contextPath}/product/list" class="btn btn-outline">返回商品管理</a>
    </div>
</div>

<jsp:include page="../include/apply_modal.jsp"/>
<script>
    function confirmDelete(url) { if (confirm('确认删除该分类吗？')) location.href = url; }
</script>
<jsp:include page="../footer.jsp"/>
