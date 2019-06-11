import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoConnection {
    private MongoCollection<Document> archive;

    public MongoConnection(){
        MongoClientURI uri = new MongoClientURI(System.getenv("MONGODB_URI"));
        MongoClient client = new MongoClient(uri);
        MongoDatabase db = client.getDatabase(uri.getDatabase());
        archive = db.getCollection("archive");
    }

    public void insertRecord(Document record){
        try {
            archive.insertOne(record);
        } catch (MongoWriteException mwe) {
            //Darn
        }
    }
}
