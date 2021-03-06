/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataAccess;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

/**
 *
 * @author Xuan Truong PC
 */
public class DataAccess {
    private final String _hostname = "localhost";
    private final int _port = 27017;
    private final String dbname = "test";
    private MongoClient  mongoClient = null;

    public MongoCollection<Document> getConnection() {
        try {
            mongoClient = new MongoClient(new ServerAddress(_hostname, _port).toString());
            MongoCollection<Document> mongoColection = (MongoCollection<Document>) mongoClient.getDatabase(dbname).getCollection("project");
            return mongoColection;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public void closeConnect(){
        try{
            mongoClient.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
