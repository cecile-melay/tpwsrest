package tpwebservicesrest

class ApiController {

    def index() { }

    def book() {
        switch (request.getMethod()) {
            case "POST":
                if(!Bibliotheque.get(params.bibliotheque.id)) {
                    render (status: 400, text:"Impossible d'attacher un livre à une bibliotheque inexistante")
                    return
                }
                def bookInstance = new Livre(params)
                if (bookInstance.save(flush:true))
                    response.status = 201
                else
                    response.status = 400
            break;
            case "GET":
            default:
                response.status
            break;
        }
    }
}
