package dao;

import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;

public class MongoJdbcTest {
  private static MongoJdbc mongoJdbc;
  private static final String DATABASE = "comments";

  @BeforeClass
  public static void beforeClass() throws Exception {
    mongoJdbc = new MongoJdbc();
    mongoJdbc.connectDb(DATABASE);
  }

  @AfterClass
  public static void afterClass() throws Exception {
    mongoJdbc.closeMongoClient();
  }

  @Test
  public void testFindAll() {
    mongoJdbc.showCursor(mongoJdbc.findAll("10011"));
    assertEquals("1", "1");
  }
}