<%@page import="fr.eni.projet.encheres.bo.user.Vendeur"%>
<%@page import="fr.eni.projet.encheres.bo.user.Utilisateur"%>
<%@page import="fr.eni.projet.encheres.bo.Adresse"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file = "/WEB-INF/Views/include/global/head.jspf" %>
<title>vendreUnArticle</title>
</head>
<body>
<header><%@ include file = "/WEB-INF/Views/include/global/header.jspf" %></header>
<h1> Nouvelle Vente</h1>

<form action="ServletVendreUnArticle" method="post">
<table>
	<tr>
		<!-- nomArticle -->
		<td>
			<label for="nomArticle">Article : </label>
			<input type="text" name="nomArticle" id="nomArticle" required>
		</td>

		<!-- description -->
		<td>
			<label for="description">Description : </label>
			<input type="text" name="description" id="description" required>
		</td>

		<!--categorie  -->
		<td>
			<label for="categorie">Catégorie : </label>
			<input list="categories" type="text" name="categorie" id="categorie" required>
			<datalist id="categories">
				<option value="Sports et loisirs">
				<option value="Informatique">
				<option value="Ameublement">
				<option value="Vêtements">
			</datalist>
		</td>
		<!--photo  -->
		
		<!-- miseAPrix -->
		<td>
			<label for="prixInitial">Mise à prix : </label>
			<input type="number" step="5" value="0" min="0" max="500" name="prixInitial" id="prixInitial" required>
		</td>
		
		<!-- debutDeEnchere -->
		<td>
			<label for="dateDebut">Début de l'enchère : </label>
			<input type="date" name="dateDebut" id="dateDebut" required>
		</td>
		
		<!-- finDeEnchere -->
		<td>
			<label for="dateFin">Fin de l'enchère : </label>
			<input type="date" name="dateFin" id="dateFin" required>
		</td>
	</tr>
</table>
<table>
		<!-- retrait -->	
	<tr>		
		<td>
			<label>Rue :</label>
			${user.adresse.rue }
		</td>
		<td>
			Code postal : ${user.adresse.codePostal }
		</td>
		<td>
			Ville : ${user.adresse.ville }
		</td>
		<!-- autogénéré en fonction de qui est connecté -->
		
</table>

		<!-- Bouton Enregistrer -->
<div>
	<input type="submit" value="Créer">
	<input type="submit" value="Annuler" formaction="ServletAccueil" formnovalidate="formnovalidate">

</div>
		<!-- Bouton Annuler -->
</form>
</body>
<%@ include file = "/WEB-INF/Views/include/global/footer.jspf" %>
</html>