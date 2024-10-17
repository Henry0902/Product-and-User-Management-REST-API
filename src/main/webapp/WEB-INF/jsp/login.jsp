
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Admin | Log in</title>

	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<!-- Tell the browser to be responsive to screen width -->
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Font Awesome -->
<link rel="stylesheet" href="${contextPath}/plugins/fontawesome-free/css/all.min.css">
<!-- Ionicons -->
<!-- <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css"> -->
<!-- icheck bootstrap -->
<link rel="stylesheet" href="${contextPath}/plugins/icheck-bootstrap/icheck-bootstrap.min.css">
<!-- Theme style -->
<link rel="stylesheet" href="${contextPath}/css/adminlte.min.css">
<!-- Google Font: Source Sans Pro -->
<link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700" rel="stylesheet">
	<!-- Thêm vào layout/js.jsp -->
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/js/toastr.min.js"></script>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/css/toastr.min.css">


	<script type="text/javascript">
		function login() {
			var username = document.getElementById("username").value;
			var password = document.getElementById("password").value;

			fetch('/login', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json',
				},
				body: JSON.stringify({
					username: username,
					password: password
				})
			})
					.then(response => response.json())
					.then(data => {
						if (data.token) {
							// Lưu JWT token vào localStorage
							localStorage.setItem('jwtToken', data.token);
							alert("Đăng nhập thành công!");
						} else {
							alert("Đăng nhập thất bại!");
						}
					})
					.catch((error) => {
						console.error('Error:', error);
					});
		}
	</script>
	<script type="text/javascript">
		function fetchData() {
			var token = localStorage.getItem('jwtToken');

			fetch('/', {
				method: 'GET',
				headers: {
					'Authorization': 'Bearer ' + token
				}
			})
					.then(response => response.json())
					.then(data => {
						// Xử lý dữ liệu nhận được từ backend
						console.log(data);
					})
					.catch((error) => {
						console.error('Error:', error);
					});
		}
	</script>

<%--	onsubmit="event.preventDefault(); handleLogin();"--%>
</head>
<body class="hold-transition login-page">
	<div class="login-box">
		<div class="login-logo">
			<a href="${contextPath}/">
				<b>Admin</b>
			</a>
		</div>
		<!-- /.login-logo -->
		<div class="card">
			<div class="card-body login-card-body">
				<c:if test="${not empty message }">
					<p class="login-box-msg" style="color: red;">${message }</p>
				</c:if>
				<form id="sign_in" method="POST" action="${contextPath}/login" >
					<div class="input-group mb-3">
						<input type="text" class="form-control" name="username" id="username" placeholder="Tên đăng nhập" required autofocus>
						<div class="input-group-append">
							<div class="input-group-text">
								<span class="fas fa-user"></span>
							</div>
						</div>
					</div>
					<div class="input-group mb-3">
						<input type="password" class="form-control" name="password" id="password" placeholder="Mật khẩu" required>
						<div class="input-group-append">
							<div class="input-group-text">
								<span class="fas fa-lock"></span>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-7">
						</div>
						<!-- /.col -->
						<div class="col-5">
							<button type="submit" class="btn btn-primary btn-block">Đăng nhập</button>
						</div>
						<!-- /.col -->
					</div>
				</form>
			</div>
			<!-- /.login-card-body -->
		</div>
	</div>

	<!-- /.login-box -->

	<!-- jQuery -->
	<script src="${contextPath}/plugins/jquery/jquery.min.js"></script>
	<!-- Bootstrap 4 -->
	<script src="${contextPath}/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
	<!-- AdminLTE App -->
	<script src="${contextPath}/js/adminlte.min.js"></script>

</body>
</html>
