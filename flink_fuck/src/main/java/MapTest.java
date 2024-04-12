import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @AUTHOR: Maynard
 * @DATE: 2023/04/08 10:08
 **/

public class MapTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        DataStreamSource<Event> stream = env.fromElements(new Event("Marry","./home",1000L),
                new Event("Bob","./cart",2000L),
                new Event("Alice","./prod?id=100",3000L));

//        SingleOutputStreamOperator<Event> filter = stream.filter(new MyFilter());

        DataStreamSink<Event> filter = stream.filter(data -> data.user.equals("Marry")).print("Lambda click");
//        filter.print();
        env.execute();
    }

    public  static  class MyFilter implements FilterFunction<Event>{
        @Override
        public boolean filter(Event event) throws Exception {
            return event.user.equals("Marry");
        }
    }
}
