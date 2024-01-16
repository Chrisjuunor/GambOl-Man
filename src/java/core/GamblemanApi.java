package core;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.json.JSONObject;
import account.Account;
import jakarta.ws.rs.POST;


@Path("api")
public class GamblemanApi {
    JSONObject json;
    Account account;
    
    public GamblemanApi(){
        account = new Account();
    }
    
    @Path("test")
    @GET
    @Produces(jakarta.ws.rs.core.MediaType.APPLICATION_JSON)
    public String test() {
        json = new JSONObject();
        json.put("status", "OK");
        return json.toString();
    }
    
    @Path("account/create")
    @POST
    @Produces(jakarta.ws.rs.core.MediaType.APPLICATION_JSON)
    public String account_create(@FormParam("name") String name, @FormParam("phone") String phone, 
            @FormParam("password") String password, @FormParam("age") int age) {
        json = new JSONObject();
        json.put("status", "OK");
        json.put("user", account.createAccount(name, phone, password, age));
        return json.toString();
    }
    
    @Path("account/login")
    @POST
    @Produces(jakarta.ws.rs.core.MediaType.APPLICATION_JSON)
    public String account_login(@FormParam("phone") String phone, @FormParam("password") String password) {
        json = new JSONObject();
        json.put("status", "OK");
        json.put("user", account.login(phone, password));
        return json.toString();
    }
    
    @Path("games")
    @POST
    @Produces(jakarta.ws.rs.core.MediaType.APPLICATION_JSON)
    public String games() {
        json = new JSONObject();
        json.put("status", "OK");
        json.put("games", account.gameSelection());
        return json.toString();
    }
    
    @Path("bet")
    @POST
    @Produces(jakarta.ws.rs.core.MediaType.APPLICATION_JSON)
    public String bet(@FormParam("user_id") int user_id, @FormParam("game_id") int game_id, 
            @FormParam("bet_amount") float bet_amount, @FormParam("bet_options") String bet_options) {
        json = new JSONObject();
        json.put("status", "OK");
        json.put("bet", account.placeBet(user_id, game_id, bet_amount, bet_options));
        return json.toString();
    }
    
    @Path("account/balance")
    @POST
    @Produces(jakarta.ws.rs.core.MediaType.APPLICATION_JSON)
    public String account_bal(@FormParam("user_id") int user_id) {
        json = new JSONObject();
        json.put("status", "OK");
        json.put("balance", account.checkBal(user_id));
        return json.toString();
    }
    
    @Path("top_up")
    @POST
    @Produces(jakarta.ws.rs.core.MediaType.APPLICATION_JSON)
    public String topUp(@FormParam("user_id") int user_id, @FormParam("amount") float amount) {
        json = new JSONObject();
        json.put("status", "OK");
        json.put("account", account.topUp(user_id, amount));
        return json.toString();
    }
    
    @Path("transaction_history")
    @POST
    @Produces(jakarta.ws.rs.core.MediaType.APPLICATION_JSON)
    public String trans_history(@FormParam("user_id") int user_id) {
        json = new JSONObject();
        json.put("status", "OK");
        json.put("balance", account.history(user_id));
        return json.toString();
    }
}
