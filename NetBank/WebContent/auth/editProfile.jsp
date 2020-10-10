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
<title>Edit Profile</title>
<link rel="icon" href="images/title.png"></link>
</head>
<body style="height: 100%;">
	<div class="container h-100">
		<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
			<a class="navbar-brand" href="<c:out value="${pageContext.servletContext.contextPath}" />/home"><img src="<c:out value="${pageContext.servletContext.contextPath}" />/images/logo.png" alt="logo" style="width: 60px; height: 60px;"></a>
			<!-- Links -->
			<div class="collapse navbar-collapse align-items-center" id="navbarResponsive">
				<ul class="navbar-nav mr-auto align-items-center">
					<li class="nav-item "><a class="nav-link" href="<c:out value="${pageContext.servletContext.contextPath}" />/home">Home</a></li>
					<li class="nav-item "><a class="nav-link" href="<c:out value="${pageContext.servletContext.contextPath}" />/auth/myPage.jsp">My Page</a></li>
					<li class="nav-item active"><a class="nav-link" href="<c:out value="${pageContext.servletContext.contextPath}" />/loadEditProfile">Edit profile</a></li>
					<li class="nav-item "><a class="nav-link" href="<c:out value="${pageContext.servletContext.contextPath}" />/loadMessages">Messages</a></li>
					<li class="nav-item "><a class="nav-link" href="<c:out value="${pageContext.servletContext.contextPath}" />/contact.jsp">Contact</a></li>
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
				<h2>Edit Profile</h2>
				<c:if test="${saveSuccessful}">
					<div class="alert alert-success alert-dismissible">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						<strong>Save successful!</strong>
					</div>
				</c:if>
				<form action="saveProfile" method="post">
					<div class="form-group">
						<label for="username">Username</label>
						<input type="text" class="form-control<c:if test="${usernameInvalid}"> is-invalid</c:if>" id="username" name="username" placeholder="Enter username" value="${username}" readonly="readonly">
						<c:if test="${usernameInvalid}">
							<p class="text-danger">Invalid username.</p>
						</c:if>
					</div>
					<div class="form-group">
						<label for="email">E-mail</label>
						<input type="email" class="form-control<c:if test="${emailInvalid}"> is-invalid</c:if>" id="email" name="email" placeholder="Enter e-mail" value="${email}" readonly="readonly">
						<c:if test="${emailInvalid}">
							<p class="text-danger">Invalid e-mail address.</p>
						</c:if>
					</div>
					<div class="form-group">
						<label for="password">Old Password</label>
						<input type="password" class="form-control<c:if test="${oldPasswordInvalid}"> is-invalid</c:if>" id="oldPassword" name="oldPassword" placeholder="Enter old password">
						<c:if test="${oldPasswordInvalid}">
							<p class="text-danger">Invalid old password.</p>
						</c:if>
					</div>
					<div class="form-group">
						<label for="password">Password</label>
						<input type="password" class="form-control<c:if test="${passwordInvalid}"> is-invalid</c:if>" id="password" name="password" placeholder="Enter password">
						<c:if test="${passwordInvalid}">
							<p class="text-danger">Invalid password.</p>
						</c:if>
					</div>
					<div class="form-group">
						<label for="passwordConfirm">Password Confirmation</label>
						<input type="password" class="form-control<c:if test="${passwordConfirmInvalid}"> is-invalid</c:if>" id="passwordConfirm" name="passwordConfirm" placeholder="Enter password again">
						<c:if test="${passwordConfirmInvalid}">
							<p class="text-danger">Invalid password confirmation.</p>
						</c:if>
					</div>
					<div class="form-row">
						<div class="form-group col-md-6">
							<label for="firstName">First name</label>
							<input type="text" class="form-control<c:if test="${firstNameInvalid}"> is-invalid</c:if>" id="firstName" name="firstName" value="${firstName}">
							<c:if test="${firstNameInvalid}">
								<p class="text-danger">Invalid first name.</p>
							</c:if>
						</div>
						<div class="form-group col-md-6">
							<label for="lastName">Last name</label>
							<input type="text" class="form-control<c:if test="${lastNameInvalid}"> is-invalid</c:if>" id="lastName" name="lastName" value="${lastName}">
							<c:if test="${lastNameInvalid}">
								<p class="text-danger">Invalid last name.</p>
							</c:if>
						</div>
					</div>
					<div class="form-group">
						<label for="city">City:</label>
						<select class="form-control<c:if test="${cityInvalid}"> is-invalid</c:if>" id="city" name="city">
							<option <c:if test="${city == -1}">selected="selected"</c:if> value="-1">Please select your city</option>
							<c:forEach var="cityItem" items="${cities}">
								<option <c:if test="${cityItem.cityId == city}">selected="selected"</c:if> value="${cityItem.cityId}">${cityItem.name}</option>
							</c:forEach>
						</select>
						<c:if test="${cityInvalid}">
							<p class="text-danger">Invalid city.</p>
						</c:if>
					</div>
					<div class="form-group">
						<label for="dateOfBirth">Date of Birth:</label>
						<input type="date" class="form-control<c:if test="${dateOfBirthInvalid}"> is-invalid</c:if>" id="dateOfBirth" name="dateOfBirth" placeholder="Enter birth date" value="${dateOfBirth}">
						<c:if test="${dateOfBirthInvalid}">
							<p class="text-danger">Invalid date of birth.</p>
						</c:if>
					</div>
					<div class="form-group">
						<label for="gender">Gender:</label>
						<select class="form-control<c:if test="${genderInvalid}"> is-invalid</c:if>" id="gender" name="gender">
							<option <c:if test="${gender == -1}">selected="selected"</c:if> value="-1">Please select your gender</option>
							<c:forEach var="genderItem" items="${genders}">
								<option <c:if test="${genderItem.genderId == gender}">selected="selected"</c:if> value="${genderItem.genderId}">${genderItem.name}</option>
							</c:forEach>
						</select>
						<c:if test="${genderInvalid}">
							<p class="text-danger">Invalid gender.</p>
						</c:if>
					</div>
					<p>Please enter a funny text about yourself:</p>
					<div class="form-group">
						<input type="text" class="form-control<c:if test="${introTextInvalid}"> is-invalid</c:if>" id="introText" name="introText" value="${introText}">
						<c:if test="${introTextInvalid}">
							<p class="text-danger">Invalid intro text.</p>
						</c:if>
					</div>
					<button type="submit" class="btn btn-primary">Save</button>
				</form>
			</div>
			<div class="col"></div>
		</div>
	</div>
	<script>
		$(document).ready(function() {
			$('[data-toggle="tooltip"]').tooltip();
		});
	</script>
</body>
</html>