import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

import java.util.Arrays;

/**
 * @AUTHOR: Maynard
 * @DATE: 2022/11/10 15:50
 **/

public class unboundedStream {
    public static void main(String[] args) throws Exception {
//        创建执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//        读取流
        DataStreamSource<String> DS = env.socketTextStream("hadoop162", 9999);
//        转换类型
        SingleOutputStreamOperator<Tuple2<String, Long>> wordAndOne = DS.flatMap((String line, Collector<String> words) -> {
                    Arrays.stream(line.split(" ")).forEach(words::collect);
                })
                .returns(Types.STRING)
                .map(words -> Tuple2.of(words, 1L))
                .returns(Types.TUPLE(Types.STRING, Types.LONG));
//        分组求和
        SingleOutputStreamOperator<Tuple2<String, Long>> sum = wordAndOne.keyBy(t -> t.f0).sum(1);
        sum.print();
        env.execute();
    }
}
