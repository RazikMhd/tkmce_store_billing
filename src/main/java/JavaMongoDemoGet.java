import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Iterator;

public class JavaMongoDemoGet {
    public static void main(String[] args){


        // Creating a Mongo client
        MongoClient mongo = new MongoClient( "localhost" , 27017 );
        // Accessing the database
        MongoDatabase database = mongo.getDatabase("tkm_store");
        // Retrieving a collection
        MongoCollection<Document> collection = database.getCollection("inventory");
        Document document = collection.find().first();
        System.out.println(document.toJson());

        ArrayList<Document> documentList = collection.find().into(new ArrayList<>());
        for (Document student : documentList) {
            System.out.println(student.toJson());
        }


    }
}