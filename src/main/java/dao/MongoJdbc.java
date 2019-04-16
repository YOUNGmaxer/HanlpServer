package dao;

import org.bson.Document;

// import com.mongodb.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
// import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.FindIterable;

import com.mongodb.MongoCredential;
// import com.mongodb.ServerAddress;

public class MongoJdbc {
  // private final String IP = "106.13.70.140";  // 迁移数据库到新服务器
  private final String IP = "114.115.130.213";
  private final int PORT = 27017;
  // private static final String USER = "root";
  // private static final char[] PWD = "688232max".toCharArray();
  // private static final String SOURCE = "admin";
  private String MONGO_URL = "mongodb://realroot:688232max@"+ IP + ":" + PORT + "/?authSource=admin";
  private MongoClient mongoClient = null;
  private MongoDatabase mongoDatabase = null;
  // private MongoCredential credential = MongoCredential.createCredential(USER, SOURCE, PWD);

  public MongoJdbc() {
  try {
      // 连接 mongodb
      // mongoClient = new MongoClient(IP, PORT);
      // mongoClient = new MongoClient(new ServerAddress(this.IP, this.PORT), this.credential, new MongoClientOptions.Builder().build());
      mongoClient = MongoClients.create(this.MONGO_URL);
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