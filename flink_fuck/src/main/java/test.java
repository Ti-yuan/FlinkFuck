import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

import java.util.Arrays;

/**
 * @AUTHOR: Maynard
 * @DATE: 2022/11/10 10:26
 **/

public class test {
    public static void main(String[] args) throws Exception {
//        创建执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//        读取文件
        DataStreamSource<String> lineDS = env.readTextFile("input/word.txt");
//        转换类型
        SingleOutputStreamOperator<Tuple2<String, Long>> wordAndOne = lineDS.flatMap((String line, Collector<String> word) -> {
                    Arrays.stream(line.split(" ")).forEach(word::collect);
                })
                .returns(Types.STRING)
                .map(word -> Tuple2.of(word, 1L))
                .returns(Types.TUPLE(Types.STRING, Types.LONG));
//        分组
        KeyedStream<Tuple2<String, Long>, String> wordAndOneKs = wordAndOne.keyBy(t -> t.f0);
//        求和
        SingleOutputStreamOperator<Tuple2<String, Long>> sum = wordAndOneKs.sum(1);
        sum.print();
        env.execute();
    }
}
