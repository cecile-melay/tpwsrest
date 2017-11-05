package tpwebservicesrest

class BootStrap {

    def init = { servletContext ->

        Bibliotheque bibliotheque
        Livre livre
        def mydate

        for (def i = 1; i <10; i++) {

            bibliotheque = new Bibliotheque(nom :'bibliotheque Lyon'+i+'ème', adresse: 'Lyon'+i+'ème', anneeConstru: 1994)

            for (def j = 0; j <10; j++) {
                mydate = new Date().copyWith(
                    year: 2014,
                    month: Calendar.APRIL,
                    dayOfMonth: 3+j+i)
                livre = new Livre(nom: 'Livre'+i+'-' + j, dateParu: mydate, isbn: 'Livre'+i+'-' + j, auteur: 'auteur'+i+'-' + j)
                bibliotheque.addToLivres(livre).save()
            }
            livre = null
            bibliotheque = null
        }

    }

    def destroy = {
    }
}
