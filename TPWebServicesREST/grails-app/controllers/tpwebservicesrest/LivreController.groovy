package tpwebservicesrest

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class LivreController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Livre.list(params), model:[livreCount: Livre.count()]
    }

    def show(Livre livre) {
        switch (request.getMethod()) {
            case "GET":
                if(!livre) {
                    render (status: 404, text:"Le livre spécifié est introuvable")
                    return
                }
            default:
                response.status
                break;
        }
        respond livre
    }

    def create() {
        respond new Livre(params)
    }

    @Transactional
    def save(Livre livre) {
        if (livre == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (livre.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond livre.errors, view:'create'
            return
        }

        switch (request.getMethod()) {
            case "POST":
                if (livre.save(flush: true))
                    response.status = 201
                else
                    render (status: 400, text:"Impossible de sauvegarder le livre")
                break;
            case "GET":
            default:
                response.status
                break;
        }

        //livre.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'livre.label', default: 'Livre'), livre.id])
                redirect livre
            }
            '*' { respond livre, [status: CREATED] }
        }
    }

    def edit(Livre livre) {
        switch (request.getMethod()) {
            case "GET":
                if(!livre) {
                    render (status: 404, text:"Le livre spécifié est introuvable")
                    return
                }
            default:
                response.status
                break;
        }
        respond livre
    }

    @Transactional
    def update(Livre livre) {
        if (livre == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (livre.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond livre.errors, view:'edit'
            return
        }

        switch (request.getMethod()) {
            case "PUT":
                if (livre.save(flush: true))
                    response.status = 201
                else
                    render (status: 400, text:"Impossible de sauvegarder le livre")
                break;
            case "GET":
            default:
                response.status
                break;
        }

        //livre.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'livre.label', default: 'Livre'), livre.id])
                redirect livre
            }
            '*'{ respond livre, [status: OK] }
        }
    }

    @Transactional
    def delete(Livre livre) {

        if (livre == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        switch (request.getMethod()) {
            case "DELETE":
                if (livre) {
                    request.withFormat {
                        form multipartForm {
                            flash.message = message(code: 'default.deleted.message', args: [message(code: 'livre.label', default: 'Livre'), livre.id])
                            redirect action: "index", method: "GET"
                        }
                        '*' { render status: 201 }
                    }
                } else {
                    render (status: 400, text:"Impossible de supprimer le livre")
                }

                break;
            case "GET":
            default:
                response.status
                break;
        }

        livre.delete flush:true


    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'livre.label', default: 'Livre'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
