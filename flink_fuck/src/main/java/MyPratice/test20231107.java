package MyPratice;

import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.FlatMapOperator;
import org.apache.flink.api.java.operators.UnsortedGrouping;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.runtime.execution.Environment;
import org.apache.flink.util.Collector;

import java.util.Arrays;

/**
 * @AUTHOR: Maynard
 * @DATE: 2023/11/07 10:43
 **/

public class test20231107 {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        DataSource<String> DS = env.readTextFile("flink_fuck/src/main/file/wc.txt");

        FlatMapOperator<String, Tuple2<String, Long>> FMDS = DS.flatMap((String lineWords, Collector<Tuple2<String, Long>> out) -> {
            String[] words = lineWords.split(" ");
            for (String word : words) {
                out.collect(Tuple2.of(word, 1L));
            }
        }).returns(Types.TUPLE(Types.STRING,Types.LONG));

        UnsortedGrouping<Tuple2<String, Long>> WordsDS = FMDS.groupBy(0);

        AggregateOperator<Tuple2<String, Long>> WordsSum = WordsDS.sum(1);
        WordsSum.print();
    }
}
