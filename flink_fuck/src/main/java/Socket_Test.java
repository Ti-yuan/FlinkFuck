import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * @AUTHOR: Maynard
 * @DATE: 2023/04/04 14:50
 **/

public class Socket_Test {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> DSS = env.socketTextStream("hadoop163", 9999);
        SingleOutputStreamOperator<Tuple2<String, Long>> FMS = DSS.flatMap((String lineWords, Collector<Tuple2<String, Long>> out) -> {
            String[] words = lineWords.split(" ");
            for (String word : words) {
                out.collect(Tuple2.of(word, 1L));
            }
        }).returns(Types.TUPLE(Types.STRING, Types.LONG));

        KeyedStream<Tuple2<String, Long>, String> KSS = FMS.keyBy(word -> word.f0);
        SingleOutputStreamOperator<Tuple2<String, Long>> sum = KSS.sum(1);
        sum.print();
        env.execute();
    }
}
