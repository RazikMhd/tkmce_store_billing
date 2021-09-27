import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

public class JavaDemoInsert {
    public static void main(String[] args){

        // Creating a Mongo client
        MongoClient mongo = new MongoClient( "localhost" , 27017 );
        // Accessing the database
        MongoDatabase database = mongo.getDatabase("tkm_store");
        // Retrieving a collection
        MongoCollection<Document> collection = database.getCollection("inventory");

        Document item = new Document("_id", new ObjectId());
        item.append("name","Kissan Jam 200gms").append("price",90).append("quantity",10);
        collection.insertOne(item);
    }



}
