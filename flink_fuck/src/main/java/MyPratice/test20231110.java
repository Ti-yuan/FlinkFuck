package MyPratice;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.LocalStreamEnvironment;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.util.Collector;

import java.util.Arrays;
import java.util.Properties;

/**
 * @AUTHOR: Maynard
 * @DATE: 2023/11/10 16:07
 **/

public class test20231110 {
    public static void main(String[] args) throws Exception {
//        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(new Configuration());

        env.setParallelism(3);
//        DataStreamSource<String> DSS = env.socketTextStream("hadoop162", 9999);

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers","hadoop162:9092,hadoop163:9092,hadoop164:9092");
        properties.setProperty("group.id","kafka_source");
        properties.setProperty("auto.offset.reset", "latest");

        DataStreamSource<String> KafkaSource = env.
                addSource(new FlinkKafkaConsumer<>("sensor", new SimpleStringSchema(), properties));

        SingleOutputStreamOperator<String> FMSS = KafkaSource.flatMap((String lineWords, Collector<String> words) -> {
                    Arrays.stream(lineWords.split(" ")).forEach(words::collect);
                })
                .returns(Types.STRING);

        SingleOutputStreamOperator<Tuple2<String, Long>> MSS = FMSS.map(word -> Tuple2.of(word, 1L))
                .returns(Types.TUPLE(Types.STRING, Types.LONG));

        SingleOutputStreamOperator<Tuple2<String, Long>> sum = MSS.keyBy(t -> t.f0).sum(1);
        sum.print();

        env.execute();
    }
}
