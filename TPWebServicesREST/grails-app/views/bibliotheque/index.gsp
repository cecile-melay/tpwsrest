<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'bibliotheque.label', default: 'Bibliotheque')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-bibliotheque" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="list-bibliotheque" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
        <br>
            <div id="content" role="main">
                <section class="row colset-2-its">
                    <h4>Accès gestion des livres par bibliothèque</h4>
                    <br>
            <g:each in="${bibliothequeList}" var="custcust">
                <ul>
                    <li><g:link class="show" action="books" resource="${custcust}">${custcust.nom}</g:link></li>
                </ul>
            </g:each>
                </section>
            </div>
            <br>
            <h4>Affichage classique</h4>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <f:table collection="${bibliothequeList}" />

            <div class="pagination">
                <g:paginate total="${bibliothequeCount ?: 0}" />
            </div>
        </div>
    </body>
</html>