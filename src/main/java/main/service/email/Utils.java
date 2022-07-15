package main.service.email;

public class Utils {
    public static String getConfirmPage(){
        return "\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\" />\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "    <title>Register</title>\n" +
                "    <link href='https://fonts.googleapis.com/css?family=Lato:300,400|Montserrat:700' rel='stylesheet' type='text/css'>\n" +
                "    <style>\n" +
                "        @import url(//cdnjs.cloudflare.com/ajax/libs/normalize/3.0.1/normalize.min.css);\n" +
                "        @import url(//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css);\n" +
                "    </style>\n" +
                "    <link rel=\"stylesheet\" href=\"https://2-22-4-dot-lead-pages.appspot.com/static/lp918/min/default_thank_you.css\">\n" +
                "    <script src=\"https://2-22-4-dot-lead-pages.appspot.com/static/lp918/min/jquery-1.9.1.min.js\"></script>\n" +
                "    <script src=\"https://2-22-4-dot-lead-pages.appspot.com/static/lp918/min/html5shiv.js\"></script>\n" +
                "</head>\n" +
                "<body>\n" +
                "<header class=\"site-header\" id=\"header\">\n" +
                "    <h1 class=\"site-header__title\" data-lead-id=\"site-header-title\">Felicitari!</h1>\n" +
                "    <h2 class=\"site-header_title\" data-lead-id=\"site-header-title\" ><br>Contul tau este activ!</h2>\n" +
                "</header>\n" +
                "\n" +
                "<div class=\"main-content\">\n" +
                "    <i class=\"fa fa-check main-content__checkmark\" id=\"checkmark\"></i>\n" +
                "    <p class=\"main-content__body\" data-lead-id=\"main-content-body\">Multumim pentru interesul acordat aplicatiei noastre. Urmatorul pas este sa intri din nou in aplicatie si sa te loghezi cu noul tau cont</p>\n" +
                "</div>\n" +
                "\n" +
                "<footer class=\"site-footer\" id=\"footer\">\n" +
                "    <p class=\"site-footer__fineprint\" id=\"fineprint\">Whatsapp doc ©2021 | All Rights Reserved</p>\n" +
                "</footer>\n" +
                "</body>\n" +
                "</html>";
    }
    public static String getErrorPage(){
        return "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\" />\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "    <title>Register</title>\n" +
                "    <link href='https://fonts.googleapis.com/css?family=Lato:300,400|Montserrat:700' rel='stylesheet' type='text/css'>\n" +
                "    <style>\n" +
                "        @import url(//cdnjs.cloudflare.com/ajax/libs/normalize/3.0.1/normalize.min.css);\n" +
                "        @import url(//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css);\n" +
                "    </style>\n" +
                "    <link rel=\"stylesheet\" href=\"https://2-22-4-dot-lead-pages.appspot.com/static/lp918/min/default_thank_you.css\">\n" +
                "    <script src=\"https://2-22-4-dot-lead-pages.appspot.com/static/lp918/min/jquery-1.9.1.min.js\"></script>\n" +
                "    <script src=\"https://2-22-4-dot-lead-pages.appspot.com/static/lp918/min/html5shiv.js\"></script>\n" +
                "</head>\n" +
                "<body>\n" +
                "<header class=\"site-header\" id=\"header\">\n" +
                "    <h1 class=\"site-header__title\" data-lead-id=\"site-header-title\">Ne pare rau!</h1>\n" +
                "    <h2>Link inactiv!</h2>\n" +
                "    </header>\n" +
                "\n" +
                "<div class=\"main-content\">\n" +
                "\n" +
                "    <p class=\"main-content__body\" data-lead-id=\"main-content-body\">Multumim pentru interesul acordat aplicatiei noastre insa contul este deja activ</p>\n" +
                "</div>\n" +
                "\n" +
                "<footer class=\"site-footer\" id=\"footer\">\n" +
                "    <p class=\"site-footer__fineprint\" id=\"fineprint\">Whatsapp doc ©2021 | All Rights Reserved</p>\n" +
                "</footer>\n" +
                "</body>\n" +
                "</html>";
    }
}
