package objects;

import java.io.Serializable;

public class Games implements Serializable {
    private int id;
    private String game;
    private String description;
    private String betting_options;

    public Games(int id, String game, String description, String betting_options) {
        this.id = id;
        this.game = game;
        this.description = description;
        this.betting_options = betting_options;
    }
    
     public Games() {
        this.id = 0;
        this.game = "";
        this.description = "";
        this.betting_options = "";
    }

    public String getBetting_options() {
        return betting_options;
    }

    public void setBetting_options(String betting_options) {
        this.betting_options = betting_options;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
//    public JSONObject toJSON(){
//        JSONObject json = new JSONObject();
//        json.put("game", game);
//        json.put("description", description);
//        json.put("betting_options", betting_options);
//        json.put("id", id);
//        return json;
//    }
}
