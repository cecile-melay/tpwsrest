package tpwebservicesrest

class Bibliotheque {

    String nom
    String adresse
    int anneeConstru
    static hasMany = [livres: Livre]

    static constraints = {
        nom blank: false
        adresse nullable: true
        anneeConstru nullable: true
    }
}
