import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * @AUTHOR: Maynard
 * @DATE: 2023/04/08 14:59
 **/

public class FlatMapTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        DataStreamSource<Event> DSS = env.fromElements(new Event("Marry", "./home", 1000L),
                new Event("Bob", "./cart", 2000L),
                new Event("Alice", "./prod?id=100", 3000L));

        SingleOutputStreamOperator<String> outstream = DSS.flatMap((Event value, Collector<String> out) -> {
            out.collect(value.user);
            out.collect(value.url);
            out.collect(value.timestamp.toString());
        }).returns(new TypeHint<String>() {
        });
        outstream.print();
        env.execute();
    }
}
