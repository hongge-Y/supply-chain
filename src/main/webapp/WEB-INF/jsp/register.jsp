<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>注册 - 百货中心供应链管理系统</title>
    <style>
        *{margin:0;padding:0;box-sizing:border-box}
        body{
            font-family:'Microsoft YaHei','Segoe UI',sans-serif;
            background:#1a365d;min-height:100vh;
            display:flex;align-items:center;justify-content:center;
            position:relative;overflow:hidden
        }
        body::before{content:'';position:absolute;width:500px;height:500px;background:radial-gradient(circle,rgba(37,99,235,0.15),transparent 70%);top:-100px;right:-100px}
        body::after{content:'';position:absolute;width:400px;height:400px;background:radial-gradient(circle,rgba(37,99,235,0.1),transparent 70%);bottom:-80px;left:-80px}
        .rc{background:#fff;border-radius:16px;padding:36px 40px;width:460px;box-shadow:0 20px 60px rgba(0,0,0,0.2);position:relative;z-index:1}
        .rc h1{color:#1a365d;font-size:18px;font-weight:700;text-align:center;margin-bottom:2px}
        .rc .sub{color:#94a3b8;font-size:12px;text-align:center;margin-bottom:24px;letter-spacing:1px}
        .rc label{color:#1a365d;font-size:12px;display:block;margin-bottom:4px;font-weight:600}
        .rc input,.rc select{width:100%;padding:9px 12px;border:1px solid #e2e8f0;border-radius:8px;color:#334155;font-size:14px;outline:none;transition:all .2s;background:#f8fafc}
        .rc input:focus,.rc select:focus{border-color:#2563eb;box-shadow:0 0 0 3px rgba(37,99,235,0.12);background:#fff}
        .rc .f{margin-bottom:14px}
        .rc .row{display:flex;gap:12px}
        .rc .row .f{flex:1}
        .rc .err{color:#dc2626;font-size:13px;padding:8px 14px;background:#fef2f2;border-radius:8px;margin-bottom:14px;text-align:center;border:1px solid #fecaca}
        .rc button{width:100%;padding:12px;background:#1a365d;border:none;border-radius:8px;color:#fff;font-size:14px;font-weight:600;cursor:pointer;transition:all .2s;letter-spacing:1px}
        .rc button:hover{background:#0f2440;transform:translateY(-1px);box-shadow:0 4px 16px rgba(26,54,93,0.3)}
        .rc .link{text-align:center;margin-top:18px;color:#94a3b8;font-size:13px}
        .rc .link a{color:#2563eb;text-decoration:none;font-weight:500}
        .rc .link a:hover{color:#1d4ed8}
    </style>
</head>
<body>
<div class="rc">
    <h1>注册新用户</h1>
    <div class="sub">创建账号，默认角色为普通员工</div>
    <c:if test="${not empty error}"><div class="err">${error}</div></c:if>
    <form action="${pageContext.request.contextPath}/doregister" method="post" onsubmit="return v()">
        <div class="f"><label>用户编码</label><input type="text" name="userCode" required placeholder="用于登录"></div>
        <div class="f"><label>真实姓名</label><input type="text" name="userName" required placeholder="请输入姓名"></div>
        <div class="row">
            <div class="f"><label>密码</label><input type="password" name="userPassword" id="p" required placeholder="至少6位"></div>
            <div class="f"><label>确认密码</label><input type="password" name="confirmPassword" id="c" required placeholder="再次输入"></div>
        </div>
        <div class="row">
            <div class="f"><label>性别</label><select name="gender"><option value="2">男</option><option value="1">女</option></select></div>
            <div class="f"><label>邮箱</label><input type="email" name="email" placeholder="可选"></div>
        </div>
        <div class="f"><label>手机号</label><input type="text" name="phone" placeholder="可选"></div>
        <div id="fe" class="err" style="display:none"></div>
        <button type="submit">注 册</button>
    </form>
    <div class="link">已有账号？<a href="${pageContext.request.contextPath}/login">立即登录</a></div>
</div>
<script>function v(){var p=document.getElementById('p').value,c=document.getElementById('c').value,e=document.getElementById('fe');if(p.length<6){e.style.display='block';e.innerHTML='密码长度至少6位';return false}if(p!==c){e.style.display='block';e.innerHTML='两次密码不一致';return false}e.style.display='none';return true}</script>
</body>
</html>
