package MyPratice;

import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

import java.util.Arrays;

/**
 * @AUTHOR: Maynard
 * @DATE: 2023/11/09 21:23
 **/

public class test20231109 {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> DSS = env.readTextFile("flink_fuck/src/main/file/wc.txt");
        SingleOutputStreamOperator<String> FMS = DSS.flatMap((String lineWord, Collector<String> words) -> {
            Arrays.stream(lineWord.split(" ")).forEach(words::collect);
        }).returns(Types.STRING);

        SingleOutputStreamOperator<Tuple2<String, Long>> MS = FMS.map(word -> (Tuple2.of(word, 1L)))
                .returns(Types.TUPLE(Types.STRING,Types.LONG));
        KeyedStream<Tuple2<String, Long>, String> KS = MS.keyBy(t -> t.f0);
        SingleOutputStreamOperator<Tuple2<String, Long>> sum = KS.sum(1);
        sum.print();

        env.execute();
    }
}
