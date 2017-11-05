package tpwebservicesrest

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class BibliothequeController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        switch (request.getMethod()) {
            case "GET":
                if(!Bibliotheque.list(params)) {
                    render (status: 404, text:"Aucune bibliothèques trouvées")
                    return
                }
            default:
                response.status
                break;
        }
        params.max = Math.min(max ?: 10, 100)
        respond Bibliotheque.list(params), model:[bibliothequeCount: Bibliotheque.count()]
    }

    def books(Bibliotheque bibliotheque) {
        switch (request.getMethod()) {
            case "GET":
                if(bibliotheque) {
                    if (bibliotheque.livres) {
                        respond bibliotheque
                    } else {
                        render(status: 404, text: "La bibliothèque ne contient aucun livre")
                        return
                    }
                } else {
                    render (status: 404, text:"La bibliothèque spécifiée est introuvable")
                    return
                }
            default:
                response.status
                break;
        }

    }

    def book() {
        def livre = Livre.get(params.livre)
        def bibliotheque = Bibliotheque.get(params.biblio);

        switch (request.getMethod()) {
            case "GET":
                if(!bibliotheque || !livre) {
                    render (status: 404, text:"La bibliothèque ou le livre spécifié sont introuvable")
                    return
                }
            default:
                response.status
                break;
        }
        respond livre
    }

    def show(Bibliotheque bibliotheque) {

        switch (request.getMethod()) {
            case "GET":
                if(!bibliotheque) {
                    render (status: 404, text:"La bibliothèque spécifiée est introuvable")
                    return
                }
            default:
                response.status
                break;
        }
        respond bibliotheque
    }

    def create() {
        Bibliotheque biblio = new Bibliotheque(params)
        save(biblio)
    }

    @Transactional
    def save(Bibliotheque bibliotheque) {
        if (bibliotheque == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (bibliotheque.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond bibliotheque.errors, view:'create'
            return
        }

        switch (request.getMethod()) {
            case "POST":
                if(bibliotheque) {
                    if (bibliotheque.save(flush: true))
                        response.status = 201
                    else
                        render(status: 400, text: "Impossible de sauvegarder la bibliothèque")
                } else {
                    render(status: 404, text: "la ressource n'est valide")
                }
                break;
            default:
                response.status
                break;
        }

        //bibliotheque.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'bibliotheque.label', default: 'Bibliotheque'), bibliotheque.id])
                redirect bibliotheque
            }
            '*' { respond bibliotheque, [status: CREATED] }
        }
    }

    def edit(Bibliotheque bibliotheque) {
        switch (request.getMethod()) {
            case "GET":
                if(!bibliotheque) {
                    render (status: 404, text:"La bibliothèque spécifié est introuvable")
                    return
                }
            default:
                response.status
                break;
        }
        respond bibliotheque
    }

    @Transactional
    def update(Bibliotheque bibliotheque) {
        if (bibliotheque == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (bibliotheque.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond bibliotheque.errors, view:'edit'
            return
        }

        switch (request.getMethod()) {
            case "PUT":
                if (bibliotheque.save(flush: true))
                    response.status = 201
                else
                    render (status: 400, text:"Impossible de sauvegarder la bibliothèque")
                break;
            case "GET":
            default:
                response.status
                break;
        }

       // bibliotheque.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'bibliotheque.label', default: 'Bibliotheque'), bibliotheque.id])
                redirect bibliotheque
            }
            '*'{ respond bibliotheque, [status: OK] }
        }
    }

    @Transactional
    def delete(Bibliotheque bibliotheque) {

        if (bibliotheque == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        bibliotheque.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'bibliotheque.label', default: 'Bibliotheque'), bibliotheque.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'bibliotheque.label', default: 'Bibliotheque'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
