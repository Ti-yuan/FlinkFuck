import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * @AUTHOR: Maynard
 * @DATE: 2023/04/03 22:20
 **/

public class DataStream {
    public static void main(String[] args) throws Exception {
        //1.创建流处理环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //2.读取文件
        DataStreamSource<String> DS = env.readTextFile("D:\\TheCode\\FlinkFuck\\flink_fuck\\src\\main\\file\\wc.txt");
        SingleOutputStreamOperator<Tuple2<String, Long>> FM = DS.flatMap((String lineWords, Collector<Tuple2<String, Long>> out) -> {
            String[] words = lineWords.split(" ");
            for (String word : words) {
                out.collect(Tuple2.of(word, 1L));
            }
        }).returns(Types.TUPLE(Types.STRING, Types.LONG));
        KeyedStream<Tuple2<String, Long>, String> keyStream = FM.keyBy(word -> word.f0);
        SingleOutputStreamOperator<Tuple2<String, Long>> sum = keyStream.sum(1);
        sum.print();
        env.execute();
    }
}
