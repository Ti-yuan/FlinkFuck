import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.FlatMapOperator;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

/**
 * @AUTHOR: Maynard
 * @DATE: 2023/04/03 13:01
 **/

public class DSet {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        DataSource<String> DS = env.readTextFile("D:\\TheCode\\FlinkFuck\\flink_fuck\\src\\main\\file\\wc.txt");
        FlatMapOperator<String, Tuple2<String, Long>> FMStream = DS.flatMap((String line, Collector<Tuple2<String, Long>> out) -> {
            String[] words = line.split(" ");
            for (String word : words) {
                out.collect(Tuple2.of(word, 1L));
            }
        })
                .returns(Types.TUPLE(Types.STRING, Types.LONG));

        AggregateOperator<Tuple2<String, Long>> sum = FMStream.groupBy(0).sum(1);
        sum.print();
    }
}
