<%@page import="fr.eni.projet.encheres.bo.user.Vendeur"%>
<%@page import="fr.eni.projet.encheres.bo.user.Utilisateur"%>
<%@page import="fr.eni.projet.encheres.bo.Adresse"%>
<%@page import="fr.eni.projet.encheres.bo.Categorie"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/WEB-INF/Views/include/global/head.jspf"%>
<title>vendreUnArticle</title>
</head>
<body>
<header>
<%@ include file = "/WEB-INF/Views/include/global/header.jspf" %>
<h1> Nouvelle Vente</h1>
</header>


<form action="ServletVendreUnArticle" method="post">


		<!-- nomArticle -->

			<label for="nomArticle">Article : </label>
			<input type="text" name="nomArticle" id="nomArticle" required>

		<!-- description -->
	
			<label for="description">Description : </label>
			<input type="text" name="description" id="description" required>
	

		<!--categorie  -->
	
			<c:forEach var="categorie" items="${listeCategorie}">
				<div>
					<input type="radio" name="categorie" id="${categorie}" value="${categorie}">
					<label for="${categorie}">${categorie.nom}</label>
				</div>
			</c:forEach>
	
		<!--photo  -->
		
		<!-- miseAPrix -->
	
			<label for="prixInitial">Mise à prix : </label>
			<input type="number" step="5" value="0" min="0" max="500" name="prixInitial" id="prixInitial" required>

		
		<!-- debutDeEnchere -->

			<label for="dateDebut">Début de l'enchère : </label>
			<input type="date" name="dateDebut" id="dateDebut" required>

		
		<!-- finDeEnchere -->
			<label for="dateFin">Fin de l'enchère : </label>
			<input type="date" name="dateFin" id="dateFin" required>
		<!-- retrait -->	
			<label>Rue :</label>
			${user.adresse.rue }
			Code postal : ${user.adresse.codePostal }
			Ville : ${user.adresse.ville }
		<!-- autogénéré en fonction de qui est connecté -->
		

		<!-- Bouton Enregistrer - Création article dans la base de donnée -->
<div>
	<input type="submit" value="Créer">
	
			<!-- Bouton Annuler - Reset des champs-->
	<input type="reset" value="Annuler">
</div>



</form>
</body>
<footer>
<%@ include file = "/WEB-INF/Views/include/global/footer.jspf" %>
</footer>

</html>