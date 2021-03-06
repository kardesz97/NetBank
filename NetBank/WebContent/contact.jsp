<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html style="height: 100%;">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
<title>Netbank - Contact</title>
<link rel="icon" href="images/title.png"></link>
</head>
<body style="height: 100%;">
	<div class="container h-100">
		<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
			<a class="navbar-brand" href="<c:out value="${pageContext.servletContext.contextPath}" />/auth/home.jsp"><img src="<c:out value="${pageContext.servletContext.contextPath}" />/images/logo.png" alt="logo" style="width: 60px; height: 60px;"></a>
			<!-- Links -->
			<div class="collapse navbar-collapse align-items-center" id="navbarResponsive">
				<ul class="navbar-nav mr-auto align-items-center">
					<li class="nav-item "><a class="nav-link" href="<c:out value="${pageContext.servletContext.contextPath}" />/auth/home">Home</a></li>
					<li class="nav-item "><a class="nav-link" href="<c:out value="${pageContext.servletContext.contextPath}" />/auth/myPage.jsp">My Page</a></li>
					<li class="nav-item"><a class="nav-link" href="<c:out value="${pageContext.servletContext.contextPath}" />/loadEditProfile">Edit profile</a></li>
					<li class="nav-item "><a class="nav-link" href="<c:out value="${pageContext.servletContext.contextPath}" />/loadMessages">Messages</a></li>
					<li class="nav-item active"><a class="nav-link" href="<c:out value="${pageContext.servletContext.contextPath}" />/contact.jsp">Contact</a></li>
				</ul>
				<ul class="navbar-nav ml-auto align-items-center">
					<li class="nav-link"><c:out value="${sessionScope.loggedInUser.username}" /></li>
					<li class="nav-link"><c:out value="${sessionScope.loggedInUser.totalBalance}" /></li>
					<li class="nav-item"><a class="nav-link" href="<c:out value="${pageContext.servletContext.contextPath}" />/logout">Logout</a></li>
				</ul>
			</div>
		</nav>
		<div class="row h-75">
			<div class="col"></div>
			<div class="col my-auto">
				<h2>Contact</h2>
				<div class="container">
					<p>
						<label class="fas fa-thumbs-up">
							</i>Phone number: +36 70/123 - 4567
						</label>
					</p>
				</div>
				<div class="form-group">
					<label for="e-mail">E-mail address: info@netbank.com</label>
				</div>
			</div>
			<div class="col"></div>
		</div>
	</div>
</body>
</html>