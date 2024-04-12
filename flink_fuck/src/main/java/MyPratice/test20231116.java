package MyPratice;

import org.apache.flink.api.common.eventtime.Watermark;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.datagen.DataGeneratorSource;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

/**
 * @AUTHOR: Maynard
 * @DATE: 2023/11/16 09:49
 **/

public class test20231116 {
    public static void main(String[] args) {

//        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(new Configuration());
//
//        KafkaSource.builder()
//                        .se
//
//        env.addSource(new FlinkKafkaConsumer<Object>("sensor", new SimpleStringSchema(),))

    }
}
