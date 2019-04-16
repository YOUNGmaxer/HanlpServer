package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;

import dao.MongoJdbc;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

/**
 * Segment
 */
public class Segment {
  // 评论过滤词条
  private final String FILTER_STR = "用户未点评，系统默认好评。";
  // 词频分类存储计数器
  private HashMap<String, HashMap<String, Integer>> wordCounterContainer = new HashMap<String, HashMap<String, Integer>>();
  private HashMap<String, HashMap<String, List<String>>> wordCounterContainerWithCid = new HashMap<String, HashMap<String, List<String>>>();
  // 词性数组
  private String[] propList = {"a", "n", "v", "ns"};
  

  public HashMap<String, HashMap<String, Integer>> segmentByRid(String rid) {
    final String DATABASE = "comments";
    Document comment = null;
    Object content = "";

    MongoJdbc mongo = new MongoJdbc();
    // 连接数据库，并获取集合数据
    mongo.connectDb(DATABASE);
    MongoCursor<Document> mongoCursor = mongo.findAll(rid);

    while (mongoCursor.hasNext()) {
      comment = mongoCursor.next();
      content = comment.get("content");
      System.out.println(content);
      // 过滤系统默认好评的评论
      if (content.equals(this.FILTER_STR)) {
        continue;
      }
      // 分词
      List<Term> termList = HanLP.segment(content.toString());

      // 统计
      for (Term term : termList) {
        String[] termSplit = term.toString().split("/");
        String termWord = termSplit[0];
        String termProp = termSplit[1];
        for (String prop : this.propList) {
          if (termProp.equals(prop)) {
            if (!this.wordCounterContainer.containsKey(prop)) {
              this.wordCounterContainer.put(prop, new HashMap<String, Integer>());
            }
            HashMap<String, Integer> wordCounter = this.wordCounterContainer.get(prop);
            if (!wordCounter.containsKey(termWord)) {
              wordCounter.put(termWord, 1);
            } else {
              int count = wordCounter.get(termWord);
              wordCounter.put(termWord, count + 1);
            }
            this.wordCounterContainer.put(prop, wordCounter);
          }
        }
      }

      System.out.println(termList);
    }

    // 对 wordCounter 进行排序
    // for (String key : wordCounterContainer.keySet()) {
    //   HashMap<String, Integer> wordCounter = wordCounterContainer.get(key);
    //   ArrayList<Map.Entry<String, Integer>> entryList = new ArrayList<>(wordCounter.entrySet());
    //   Collections.sort(entryList, new Comparator<Map.Entry<String, Integer>>() {
    //     @Override
    //     public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
    //       return o2.getValue().compareTo(o1.getValue());
    //     }
    //   });

    //   System.out.println("统计结果: " + key);
    //   for (Map.Entry<String, Integer> m : entryList) {
    //     System.out.println(m.getKey() + ": " + m.getValue());
    //   }
    // }

    mongo.closeMongoClient();
    return this.wordCounterContainer;
  }

  public HashMap<String, HashMap<String, List<String>>> segmentByRidWithCid(String rid) {
    final String DATABASE = "comments";
    Document comment = null;
    Object content = "";
    String commentId = "";
    MongoJdbc mongo = new MongoJdbc();
    // 连接数据库，并获取集合数据
    mongo.connectDb(DATABASE);
    MongoCursor<Document> mongoCursor = mongo.findAll(rid);

    while (mongoCursor.hasNext()) {
      comment = mongoCursor.next();
      content = comment.get("content");
      commentId = comment.get("commentId").toString();
      System.out.println(content);
      // 过滤系统默认好评的评论
      if (content.equals(this.FILTER_STR)) {
        continue;
      }
      // 分词
      List<Term> termList = HanLP.segment(content.toString());

      for (Term term : termList) {
        String[] termSplit = term.toString().split("/");
        String termWord = termSplit[0];
        String termProp = termSplit[1];
        for (String prop : this.propList) {
          if (termProp.equals(prop)) {
            if (!this.wordCounterContainerWithCid.containsKey(prop)) {
              this.wordCounterContainerWithCid.put(prop, new HashMap<String, List<String>>());
            }
            HashMap<String, List<String>> cidContainer = this.wordCounterContainerWithCid.get(prop);
            List<String> cidList = cidContainer.get(termWord);
            if (cidList == null) {
              cidList = new ArrayList<String>();
            }
            cidList.add(commentId);
            cidContainer.put(termWord, cidList);
            this.wordCounterContainerWithCid.put(prop, cidContainer);
          }
        }
      }
    }
    mongo.closeMongoClient();
    return this.wordCounterContainerWithCid;
  }
}

