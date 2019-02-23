package dao;

import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.FindIterable;

public class MongoJdbc {
  private static final String MONGO_URI = "106.13.70.140";
  private static final int MONGO_PORT = 27017;
  private MongoClient mongoClient = null;
  private MongoDatabase mongoDatabase = null;

  public MongoJdbc() {
    try {
      // 连接 mongodb
      mongoClient = new MongoClient(MONGO_URI, MONGO_PORT);
    } catch(Exception e) {
      System.err.println(e);
    }
  }

  public void connectDb(String database) {
    try {
      // 连接数据库
      mongoDatabase = mongoClient.getDatabase(database);
    } catch(Exception e) {
      System.err.println(e);
    }
  }

  public MongoCursor<Document> findAll(String collectionName) {
    MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
    FindIterable<Document> findIterable = collection.find();
    MongoCursor<Document> mongoCursor = findIterable.iterator();
    return mongoCursor;
  }

  public void showCursor(MongoCursor<Document> mongoCursor) {
    while (mongoCursor.hasNext()) {
      System.out.println(mongoCursor.next());
    }
  }

  public void closeMongoClient() {
    mongoClient.close();
  }
}