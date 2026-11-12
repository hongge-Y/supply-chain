<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>登录 - 百货中心供应链管理系统</title>
    <style>
        *{margin:0;padding:0;box-sizing:border-box}
        body{
            font-family:'Microsoft YaHei','Segoe UI',sans-serif;
            background:#1a365d;min-height:100vh;
            display:flex;align-items:center;justify-content:center;
            position:relative;overflow:hidden
        }
        body::before{
            content:'';position:absolute;width:500px;height:500px;
            background:radial-gradient(circle,rgba(37,99,235,0.15),transparent 70%);
            top:-100px;right:-100px
        }
        body::after{
            content:'';position:absolute;width:400px;height:400px;
            background:radial-gradient(circle,rgba(37,99,235,0.1),transparent 70%);
            bottom:-80px;left:-80px
        }
        .login-card{
            background:#fff;border-radius:16px;padding:44px 40px 36px;width:400px;
            box-shadow:0 20px 60px rgba(0,0,0,0.2);position:relative;z-index:1
        }
        .login-card h1{color:#1a365d;font-size:20px;font-weight:700;text-align:center;letter-spacing:0.5px;margin-bottom:2px}
        .login-card .subtitle{color:#94a3b8;font-size:12px;text-align:center;margin-bottom:32px;letter-spacing:1px}
        .login-card label{display:block;color:#1a365d;font-size:12px;margin-bottom:4px;font-weight:600}
        .login-card input{
            width:100%;padding:10px 14px;border:1px solid #e2e8f0;border-radius:8px;
            color:#334155;font-size:14px;outline:none;transition:all .2s;background:#f8fafc
        }
        .login-card input:focus{border-color:#2563eb;box-shadow:0 0 0 3px rgba(37,99,235,0.12);background:#fff}
        .login-card .field{margin-bottom:18px}
        .login-card .error-msg{color:#dc2626;font-size:13px;padding:8px 14px;background:#fef2f2;border-radius:8px;margin-bottom:18px;text-align:center;border:1px solid #fecaca}
        .login-card .success-msg{color:#16a34a;font-size:13px;padding:8px 14px;background:#dcfce7;border-radius:8px;margin-bottom:18px;text-align:center;border:1px solid #bbf7d0}
        .login-card button{
            width:100%;padding:12px;background:#1a365d;border:none;border-radius:8px;
            color:#fff;font-size:14px;font-weight:600;cursor:pointer;
            transition:all .2s;letter-spacing:1px
        }
        .login-card button:hover{background:#0f2440;transform:translateY(-1px);box-shadow:0 4px 16px rgba(26,54,93,0.3)}
        .login-card .register-link{text-align:center;margin-top:20px;color:#94a3b8;font-size:13px}
        .login-card .register-link a{color:#2563eb;text-decoration:none;font-weight:500}
        .login-card .register-link a:hover{color:#1d4ed8}
    </style>
</head>
<body>
<div class="login-card">
    <h1>百货中心供应链管理系统</h1>
    <div class="subtitle">DEPARTMENT STORE SCM</div>
    <form action="${pageContext.request.contextPath}/dologin" method="post">
        <c:if test="${param.registered eq '1'}"><div class="success-msg">注册成功，请登录</div></c:if>
        <c:if test="${param.error eq '1'}"><div class="error-msg">用户名或密码错误</div></c:if>
        <div class="field"><label>账号</label><input type="text" name="userCode" placeholder="用户编码" autocomplete="off"></div>
        <div class="field"><label>密码</label><input type="password" name="userPassword" placeholder="密码"></div>
        <button type="submit">登 录</button>
    </form>
    <div class="register-link">还没有账号？<a href="${pageContext.request.contextPath}/register">立即注册</a></div>
</div>
</body>
</html>
