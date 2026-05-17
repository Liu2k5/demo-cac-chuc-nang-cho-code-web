package liu.democacchucnangchocodeweb.api;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(Api.API)
public class Api {
    public static final String API = "/api";
    public static final String AUTH = API + "/auth";
    public static final String CUSTOMER = API + "/customer";
    public static final String ADMINISTRATOR = API + "/administrator";


}
