package tpwebservicesrest

class Livre {

    String nom
    Date dateParu
    String isbn
    String auteur
    static belongsTo = [bibliotheque : Bibliotheque]

    static constraints = {
        nom blank: false
        dateParu nullable: true
        isbn nullable: true
        auteur nullable: true
    }
}
