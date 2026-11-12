<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="我的申请"/>
</jsp:include>

<div class="card">
    <div class="card-header">
        <h2>📋 我的申请</h2>
    </div>

    <div class="table-responsive">
        <table>
            <tr>
                <th>申请ID</th>
                <th>模块</th>
                <th>操作</th>
                <th>理由</th>
                <th>状态</th>
                <th>申请时间</th>
                <th>审批意见</th>
            </tr>
            <c:forEach items="${list}" var="a">
                <tr>
                    <td>${a.id}</td>
                    <td><span class="status-badge info">${a.module}</span></td>
                    <td>${a.action}</td>
                    <td style="max-width:200px; overflow:hidden; text-overflow:ellipsis; white-space:nowrap;">${a.applyReason}</td>
                    <td>
                        <c:choose>
                            <c:when test="${a.status eq 0}">
                                <span class="status-badge warning">⏳ 待审批</span>
                            </c:when>
                            <c:when test="${a.status eq 1}">
                                <span class="status-badge success">✓ 已通过</span>
                            </c:when>
                            <c:when test="${a.status eq 2}">
                                <span class="status-badge danger">✗ 已拒绝</span>
                            </c:when>
                            <c:otherwise><span class="status-badge">未知</span></c:otherwise>
                        </c:choose>
                    </td>
                    <td>${a.applyDate}</td>
                    <td>${a.approvalComment}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

<jsp:include page="../footer.jsp"/>
