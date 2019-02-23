package demo;

import com.hankcs.hanlp.corpus.occurrence.Occurrence;
import com.hankcs.hanlp.corpus.occurrence.PairFrequency;
import com.hankcs.hanlp.corpus.occurrence.TermFrequency;
import com.hankcs.hanlp.corpus.occurrence.TriaFrequency;

import java.util.Map;
import java.util.Set;

/**
 * 演示词共现统计
 *
 * @author hankcs
 */
public class DemoOccurrence
{
    public static void main(String[] args)
    {
        Occurrence occurrence = new Occurrence();
        occurrence.addAll("在计算机音视频和图形图像技术等二维信息算法处理方面目前比较先进的视频处理算法");
        occurrence.compute();

        Set<Map.Entry<String, TermFrequency>> uniGram = occurrence.getUniGram();
        for (Map.Entry<String, TermFrequency> entry : uniGram)
        {
            TermFrequency termFrequency = entry.getValue();
            System.out.println(termFrequency);
        }

        Set<Map.Entry<String, PairFrequency>> biGram = occurrence.getBiGram();
        for (Map.Entry<String, PairFrequency> entry : biGram)
        {
            PairFrequency pairFrequency = entry.getValue();
            if (pairFrequency.isRight())
                System.out.println(pairFrequency);
        }

        Set<Map.Entry<String, TriaFrequency>> triGram = occurrence.getTriGram();
        for (Map.Entry<String, TriaFrequency> entry : triGram)
        {
            TriaFrequency triaFrequency = entry.getValue();
            if (triaFrequency.isRight())
                System.out.println(triaFrequency);
        }
    }
}