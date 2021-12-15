<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="fr.eni.projet.encheres.bo.Adresse"%>
    <%@page import="fr.eni.projet.encheres.bo.user.*"%>
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
<title>Mon Profil</title>
</head>
<header>
<%@ include file = "/WEB-INF/Views/include/global/header.jspf" %>


<h1 class="titrePage">Mon Profil</h1>



</header>
<main>
<h1>Mon Profil</h1>

<form action="ServletVoirProfil" method="post">
<table>


<tr>
<td>
<label for="pseudo">Pseudo : </label>
<input type="text" name="pseudo" id="pseudo" value="${user.pseudo}" required>
</td>

<td>
<label for="nom">Nom : </label>
<input type="text" name="nom" id="nom" value="${user.nom}" required>
</td>

</tr>

<tr>
<td>
<label for="prenom">Prénom : </label>
<input type="text" name="prenom" id="prenom"  value="${user.prenom}" required>
</td>

<td>
<label for="email">E-Mail : </label>
<input type="email" name="email" id="email"  value="${user.email}" required>
</td>

</tr>
 


<tr>
<td>
<label for="phone">Téléphone : </label>
<input type="tel" name="phone" id="phone"  value="${user.telephone}" >
</td>

<td>
<label for="rue">Rue : </label>
<input type="text" name="rue" id="rue" required  value="${user.adresse.rue}" >
</td>

</tr>

<tr>
<td>
<label for="codePostal">Code Postal : </label>
<input type="text" name="codePostal" id="codePostal"  value="${user.adresse.codePostal}"  required>
</td>

<td>
<label for="ville">Ville : </label>
<input type="text" name="ville" id="ville"  value="${user.adresse.ville}"  required>
</td>

</tr>


<tr>
<td>
<label for="mdp">Mot de passe : </label>
<input type="password" name="mdp" id="mdp"  value="${user.mdp}"   required>
</td>

<td>
<label for="mdpConf">Confirmation : </label>
<input type="password" name="mdpConf" id="mdpConf" required>
</td>

</tr>


</table>












<div>
<input type="submit" value="Modifier">
<input type="submit" value="Supprimer" formnovalidate="formnovalidate" formmethod="DELETE">
<input type="submit" value="Annuler" formaction="ServletAccueil" formnovalidate="formnovalidate">

</div>







</form>

</main>
<footer><%@ include file = "/WEB-INF/Views/include/global/footer.jspf" %> </footer>
</body>
</html>