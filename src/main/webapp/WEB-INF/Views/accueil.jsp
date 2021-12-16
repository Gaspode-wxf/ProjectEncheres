<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="fr.eni.projet.encheres.bo.Categorie"%>
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
<%@include file="include/global/head.jspf"%>
<title>Accueil</title>

</head>
<body>
<header>
<%@ include file = "include/global/header.jspf" %>


<h1 class="titrePage">Accueil</h1>



</header>
<main>

<c:if test="${!sessionScope.connecte}">
<div class="wrapper"> 
<%@ include file="include/user/fragmentBandeauUserNonConnecte.jspf" %>
</div>
</c:if>
<c:if test="${sessionScope.connecte}">
<div class="wrapper"> 
<%@ include file="include/user/fragmentBandeauUserConnecte.jspf" %>
</div>
</c:if>
<nav class="recherche">
<form class="champRecherche" action="" method="post">
<input type="search" placeholder="Le nom de l'article contient" name="motClef" id="motClef" >
<label for="categories">Choisissez une catégorie</label>
<input list="categories" id="choixCategorie" name="choixCategorie">
<datalist id="categories">

	<option value="Toutes" selected>
	<c:forEach var="cat" items="${listeCat}">
	
	<option value="${cat.nom}">
	

</c:forEach>
</datalist>

<input type="submit" value="Valider" >
</form>
</nav>


<div class="listeEncheres">
<%@include file="include/articles/listeArticle.jspf" %>

</div>





</main> 
<footer> <%@ include file = "include/global/footer.jspf" %></footer>

</body>
</html>