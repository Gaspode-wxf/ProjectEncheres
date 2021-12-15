<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<!-- <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" >
<meta name="description" content="">
<meta name="author" content="Will E. Cefes">



CSS
<link href="style/reset.css" rel="stylesheet">
<link href="style/style.css" rel="stylesheet"> -->
<%@include file="/WEB-INF/Views/include/global/head.jspf"%>
<title>Connectez-Vous !</title>
</head>
<header>
<%@ include file = "/WEB-INF/Views/include/global/header.jspf" %>


<h1 class="titrePage">Connectez-vous</h1>



</header>
<main>


<div class="wrapper">

<form method="post" action="ServletSignIn">

<label for="pseudo">Identifiant (Pseudo) : </label>
<c:choose>
<c:when test="${requestScope.pseudo!=null }">
<input type="text" name="pseudo" id="pseudo" value="<%=request.getAttribute("pseudo")%>">
</c:when>
<c:otherwise>
<input type="text" name="pseudo" id="pseudo">

</c:otherwise>
</c:choose>

<label for="mdp">Mot de passe : </label>
<input type="password" name="mdp" id="mdp">


<label for="remember">Se souvenir de moi</label>
<input type="checkbox" name="remember" id ="remember"> 

<a href=#>Mot de passe oublié ?</a>



<input type="submit" value="Se connecter">

</form>
<form action="/ProjectEncheres/ServletCreerCompte" method="get">
<button type="submit">Créer un compte</button>
</form>

</div>


</main>
<footer><%@ include file = "/WEB-INF/Views/include/global/footer.jspf" %> </footer>
</body>
</html>