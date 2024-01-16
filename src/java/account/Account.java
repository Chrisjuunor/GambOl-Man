package account;

import java.sql.Statement;
import objects.Games;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class Account {

    public Account(){}
    
    public String createAccount(String name, String phone, String password, int age){
        
        try(Connection conn = getDBConnection()){
            String query = "INSERT INTO user (name, phone, password, age) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, phone);
            stmt.setString(3, password);
            stmt.setInt(4, age);
            if(age >= 18){
                stmt.execute();
            }else{
                return "User must be at least 18 years old";
            }
            
            query = "SELECT last_insert_id() AS id";
            Statement stmtt = conn.createStatement();
            ResultSet rs = stmtt.executeQuery(query);
            if(rs.next()){
                return String.valueOf(rs.getInt("id"));
            }
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "account created successfully";
    }
        
    public String login(String phone, String password) {
        try(Connection conn = getDBConnection()){
            String query = "SELECT * FROM user WHERE phone = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, phone);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if(!rs.next()){
                return "login failed";
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "login successful";
    }
    
    public List<Games> gameSelection() {
        List<Games> games = new ArrayList<>();
        try(Connection conn = getDBConnection()){
            String sql = "SELECT * FROM games";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                Games game = new Games(rs.getInt("id"), 
                        rs.getString("game"),
                        rs.getString("description"),
                        rs.getString("betting_options"));
                games.add(game);
            return games;
            }
          
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
        }
        return games;
    }
    
    public String placeBet(int user_id, int game_id, float bet_amount, String bet_options){
        try(Connection conn =  getDBConnection()){
            String query = "SELECT * FROM user WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, user_id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                float balance = rs.getFloat("balance");
                float newBalance = balance - bet_amount;
                
                if(balance >= bet_amount){
                    String query1 = "INSERT INTO bets (user_id, game_id, bet_amount, bet_options) VALUES (?, ?, ?, ?)";
                    PreparedStatement stmt1 = conn.prepareStatement(query1);
                    stmt1.setInt(1, user_id);
                    stmt1.setInt(2, game_id);
                    stmt1.setFloat(3, bet_amount);
                    stmt1.setString(4, bet_options);
                    stmt1.execute();
                    
                    
                    
                    String query3 = "UPDATE user SET balance = ? WHERE user_id = ?";
                    PreparedStatement stmt3 = conn.prepareStatement(query3);
                    stmt3.setFloat(1, newBalance);
                    stmt3.setInt(2, user_id);
                    stmt3.execute();
                    
                    return "Bet placed successfully!";
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Action failed!";
    }
    
    public boolean topUp(int user_id, float amount) {
        try(Connection conn = getDBConnection()){
            String query = "UPDATE user SET balance = balance + ? WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setFloat(1, amount);
            stmt.setInt(2, user_id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                query = "INSERT INTO transaction_history (user_id, transaction_type, amount) VALUES (?, deposit, ?)";
                PreparedStatement stmt1 = conn.prepareStatement(query);
                stmt1.setInt(1, user_id);
                stmt1.setFloat(2, amount);
                stmt1.execute();
            }
            
//            String type = "deposit";
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    
    public float checkBal(int user_id) {
        try(Connection conn = getDBConnection()){
            String query = "SELECT balance FROM user WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, user_id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                float bal = rs.getFloat("balance");
                return bal;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public String history(int user_id) {
        return "";
    }
    
    Connection getDBConnection() throws ClassNotFoundException, SQLException{
        String user = "root";
        String password = "Dhnonny23";
        String url = "jdbc:mysql://localhost:3306/gambleman?useSSL=false";
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, password);
    }
   
}
