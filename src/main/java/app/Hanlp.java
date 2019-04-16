package app;

import com.hankcs.hanlp.HanLP;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.bson.Document;
import com.hankcs.hanlp.seg.common.Term;
import com.mongodb.client.MongoCursor;

import bean.Comment;
import dao.MongoJdbc;
import controller.Segment;

@Path("/api")
public class Hanlp {
  @GET @Path("/sample")
  @Produces(MediaType.TEXT_PLAIN)
  public String doSample() {
    HanLP.Config.enableDebug();
    System.out.println(HanLP.segment("你好，欢迎使用HanLP汉语处理包！接下来请从其他Demo中体验HanLP丰富的功能~"));
    return "success";
  }

  @POST @Path("/basic")
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  @Consumes(MediaType.APPLICATION_JSON)
  public Comment doBasicApi(Comment comment) {
    String content = comment.getContent();
    System.out.println("content: " + content);
    System.out.println(HanLP.segment(content));
    return comment;
  }

  @GET @Path("/segment/{rid}")
  @Produces(MediaType.APPLICATION_JSON)
  public HashMap<String, HashMap<String, Integer>> doSegmentByRid(@PathParam("rid") String rid, @QueryParam("withCommentId") Boolean withCommentId) {
    Segment segmentObj = new Segment();
    
    return segmentObj.segmentByRid(rid);
    
    // final String DATABASE = "comments";
    // final String FILTER_STR = "用户未点评，系统默认好评。";
    // Object content = "";
    // // 词频计数器
    // // HashMap<String, Integer> wordCounter = new HashMap<String, Integer>();
    // HashMap<String, HashMap<String, Integer>> wordCounterContainer = new HashMap<String, HashMap<String, Integer>>();
    // // 词性数组
    // String[] propList = {"a", "n", "v", "ns"};
    // MongoJdbc mongo = new MongoJdbc();

    // // 连接数据库，并获取集合数据
    // mongo.connectDb(DATABASE);
    // MongoCursor<Document> mongoCursor = mongo.findAll(rid);
    // // 对每一条评论进行分词，词频统计处理
    // while (mongoCursor.hasNext()) {
    //   content = mongoCursor.next().get("content");
    //   System.out.println(content);
    //   // 过滤系统默认好评的评论
    //   if (content.equals(FILTER_STR)) {
    //     continue;
    //   }
    //   // 分词
    //   List<Term> termList = HanLP.segment(content.toString());

    //   // 统计
    //   for (Term term : termList) {
    //     String[] termSplit = term.toString().split("/");
    //     String termWord = termSplit[0];
    //     String termProp = termSplit[1];
    //     for (String prop : propList) {
    //       if (termProp.equals(prop)) {
    //         if (!wordCounterContainer.containsKey(prop)) {
    //           wordCounterContainer.put(prop, new HashMap<String, Integer>());
    //         }
    //         HashMap<String, Integer> wordCounter = wordCounterContainer.get(prop);
    //         if (!wordCounter.containsKey(termWord)) {
    //           wordCounter.put(termWord, 1);
    //         } else {
    //           int count = wordCounter.get(termWord);
    //           wordCounter.put(termWord, count + 1);
    //         }
    //         wordCounterContainer.put(prop, wordCounter);
    //       }
    //     }
    //   }

    //   System.out.println(termList);
    // }
    
    // // 对 wordCounter 进行排序
    // // for (String key : wordCounterContainer.keySet()) {
    // //   HashMap<String, Integer> wordCounter = wordCounterContainer.get(key);
    // //   ArrayList<Map.Entry<String, Integer>> entryList = new ArrayList<>(wordCounter.entrySet());
    // //   Collections.sort(entryList, new Comparator<Map.Entry<String, Integer>>() {
    // //     @Override
    // //     public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
    // //       return o2.getValue().compareTo(o1.getValue());
    // //     }
    // //   });

    // //   System.out.println("统计结果: " + key);
    // //   for (Map.Entry<String, Integer> m : entryList) {
    // //     System.out.println(m.getKey() + ": " + m.getValue());
    // //   }
    // // }

    // mongo.closeMongoClient();
    // return wordCounterContainer;
  }

  @GET @Path("/segment/detail/{rid}")
  @Produces(MediaType.APPLICATION_JSON)
  public HashMap<String, HashMap<String, List<String>>> doSegmentByRidWithCid(@PathParam("rid") String rid) {
    Segment segmentObj = new Segment();
    
    return segmentObj.segmentByRidWithCid(rid);
  }
}