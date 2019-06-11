import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import org.bson.Document;

import java.util.Date;

public class SQSListener implements RequestHandler<SQSEvent, Void> {
    private static final MongoConnection mongo;

    static { //Allows Lambda to reuse the mongo connection rather than "cold-boot" it
        mongo = new MongoConnection();
    }

    public Void handleRequest(SQSEvent event, Context context){
        for(SQSEvent.SQSMessage message: event.getRecords()) {
            Document doc = Document.parse(message.getBody());
            doc.append("timestamp", new Date());
            mongo.insertRecord(doc);
        }
        return null;
    }

}
