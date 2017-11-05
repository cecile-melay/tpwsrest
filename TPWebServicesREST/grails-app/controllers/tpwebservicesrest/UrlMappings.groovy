package tpwebservicesrest

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')


        "/$biblio/biblio/$livre?"(controller:"bibliotheque", action:"book")

        "/biblio"(controller:"bibliotheque", action:"index")

        //"/$biblio/biblio"(controller:"bibliotheque", action:"show")

        //"/$biblio/biblio"(controller:"bibliotheque", action:"books")
    }
}
