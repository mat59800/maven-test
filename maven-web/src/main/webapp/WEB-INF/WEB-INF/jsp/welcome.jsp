<%@ page pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!doctype html>
<html lang="en">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
  content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet"
  href="<c:url value="resources/css/bootstrap.min.css"/>">
<link rel="stylesheet"
  href="<c:url value="resources/css/whyd-styles.css"/>">
<link rel="stylesheet"
  href="<c:url value="resources/css/easy-autocomplete.min.css"/>">

<title>Whyd ?</title>
</head>
<body>
  <header>
    <!-- Fixed navbar -->
    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
      <a class="navbar-brand" href="#">Whyd : What Have You Done ?</a>
    </nav>
  </header>


  <!-- Begin page content -->
  <main role="main" class="container"> <c:if
    test="${authenticated}">
    <h2 class="mt-5">
      Welcome
      <c:out value="${user.name}"></c:out>
      !
      </h1>
      <form:form method="POST" action="message" modelAttribute="message">
        <div class="form-group">
          <label class="sr-only" for="inlineFormInput">What Have
            you done ?</label>
          <form:textarea class="form-control mb-2 mr-sm-2 mb-sm-0"
            id="inlineFormInput" placeholder="What Have you done?"
            path="content" cols="100" />
        </div>

        <div class="form-group" id="advanced" style="display: none">

          <div class="form-inline">
            <div class="form-check mb-2 mr-sm-2 mb-sm-0">
              <label class="form-check-label"> <form:checkbox
                  class="form-check-input" path="private" /> Private ?
              </label>
            </div>
            <label class="sr-only" for="inlineFormInputGroup">Username</label>
            <div class="input-group mb-2 mr-sm-2 mb-sm-0">
              <div class="input-group-prepend">
                <span class="input-group-text" id="basic-addon1">@</span>
              </div>
              <form:input type="text" class="form-control"
                id="inlineFormInputGroup" placeholder="Recipient"
                path="recipient" />
            </div>



          </div>
        </div>
        <div class="form-group">
          <form:button class="btn btn-primary">Send !</form:button>
          <a id="options" href="#">Advanced Options</a>
        </div>
      </form:form>
  </c:if> </main>



  <div id="messages" class="mx-auto col-md-8">
    <h2 class="mt-5">What happened ?</h2>
    <c:forEach items="${messages}" var="m">
      <div class="row mt-2">
        <fmt:parseDate value="${ m.creationDate }"
          pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
        <span class="text-white bg-secondary border rounded mr-1"><fmt:formatDate
            pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }" /> </span>

        <span
          class="border border-secondary rounded bg-primary text-white">${m.author.name}
          <c:if test="${not empty m.recipient}">
          -> ${m.recipient.name}
          </c:if> <c:if test="${m.isPrivate()}"> &#x1F512;    </c:if>

        </span> <span class="border border-primary rounded w-100 p-3  ">${m.content}</span>
      </div>


    </c:forEach>
  </div>
  <!-- Optional JavaScript -->
  <!-- jQuery first, then Popper.js, then Bootstrap JS -->
  <script src="<c:url value="resources/js/jquery-3.3.1.min.js"/>"></script>
  <script src="<c:url value="resources/js/popper.min.js"/>"></script>
  <script src="<c:url value="resources/js/bootstrap.min.js"/>"></script>
  <script
    src="<c:url value="resources/js/jquery.easy-autocomplete.min.js"/>"></script>
  <script src="<c:url value="resources/js/typeahead.bundle.nmin.js"/>"></script>

  <script>
			$(document).ready(function() {
				$("#options").click(function() {
					$("#advanced").toggle();
				});
				var options = {
					url : "<c:url value="api/users"/>",

					getValue : "email",

					list : {
						match : {
							enabled : true
						}
					},
					theme : bootstrap
				};
				$("#inlineFormInputGroup").easyAutocomplete(options);
			})
		</script>
</body>
</html>