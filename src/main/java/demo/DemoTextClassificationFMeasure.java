package demo;

import com.hankcs.hanlp.classification.classifiers.IClassifier;
import com.hankcs.hanlp.classification.classifiers.NaiveBayesClassifier;
import com.hankcs.hanlp.classification.corpus.FileDataSet;
import com.hankcs.hanlp.classification.corpus.IDataSet;
import com.hankcs.hanlp.classification.corpus.MemoryDataSet;
import com.hankcs.hanlp.classification.statistics.evaluations.Evaluator;
import com.hankcs.hanlp.classification.statistics.evaluations.FMeasure;
// import com.hankcs.hanlp.classification.tokenizers.BigramTokenizer;
import com.hankcs.hanlp.classification.tokenizers.HanLPTokenizer;

import java.io.IOException;

import static demo.DemoTextClassification.CORPUS_FOLDER;

public class DemoTextClassificationFMeasure {
  public static void main(String[] args) throws IOException {
    // 前 90% 作为训练集
    IDataSet trainingCorpus = new FileDataSet().setTokenizer(new HanLPTokenizer()).load(CORPUS_FOLDER, "UTF-8", 0.9);
    IClassifier classifier = new NaiveBayesClassifier();
    classifier.train(trainingCorpus);
    // 后 10% 作为测试集
    IDataSet testingCorpus = new MemoryDataSet(classifier.getModel()).load(CORPUS_FOLDER, "UTF-8", -0.1);

    // 计算准确率
    FMeasure result = Evaluator.evaluate(classifier, testingCorpus);
    System.out.println(result);
  }
}