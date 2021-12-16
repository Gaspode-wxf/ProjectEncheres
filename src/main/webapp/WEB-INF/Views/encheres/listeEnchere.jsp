<%@page import="fr.eni.projet.encheres.bo.Article"%>
<%@page import="fr.eni.projet.encheres.bo.Categorie"%>
<%@page import="fr.eni.projet.encheres.bo.Adresse"%>
<%@page import="fr.eni.projet.encheres.bo.user.Vendeur"%>
<%@page import="fr.eni.projet.encheres.bo.Enchere" %>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" >
<meta name="description" content="">
<meta name="author" content="Greg / Spague - Les Chiens Prodiges">

<title>Fiche Article</title>

<!-- CSS -->
<link href="style/reset.css" rel="stylesheet">
<link href="style/style.css" rel="stylesheet">



</head>
<body>
<header>
<div class="nomAppli"> ENI-Encheres</div>
<h1 class="titrePage">Liste Encheres</h1>
<div class="signIn"> <a href="ServletSignIn">S'inscrire - Se connecter</a> </div>

</header>
<main>

<h2>Affichage liste des enchères</h2>


	<c:if test="${!empty catEnchere}">
		<c:forEach var="Enchere" items="${catEnchere}">
			<div class="wrapper">
			<div class="encheres">
			<ul>
				<li>Article : ${Enchere.getArticle().nomArticle}</li>
				<li>Vendeur : ${Enchere.getVendeur().pseudo}</li>
				<li>Date enchère : ${Enchere.dateEnchere}</li>
				<li>Montant enchère : ${Enchere.montantEnchere }</li>
			</ul>
			</div> 
			</div>
		</c:forEach>
	</c:if>
	<c:if test="${empty catEnchere}">
		<p>Aucune enchere.</p>
	</c:if>

<%-- <%@ include file="fragmentListeArticle.jsp" %> --%>

</main>
<footer>copyright - Greg / Spague - Les Chiens Prodiges </footer>
</body>
</html>