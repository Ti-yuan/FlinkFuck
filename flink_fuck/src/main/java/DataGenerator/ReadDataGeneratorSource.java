package DataGenerator;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.connector.sink.Sink;
import org.apache.flink.api.connector.source.util.ratelimit.RateLimiterStrategy;
import org.apache.flink.connector.base.DeliveryGuarantee;
import org.apache.flink.connector.datagen.source.DataGeneratorSource;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.ConnectedStreams;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;


import DataGenerator.DataStreamSource.MyGeneratorFunction;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Arrays;
import java.util.Map;


/**
 * @AUTHOR: Maynard
 * @DATE: 2023/11/16 10:50
 **/

public class ReadDataGeneratorSource {

    public static void main(String[] args) throws Exception {
        // 1.获取执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        // 2.自定义数据生成器Source
        /*
         *  TODO DataGeneratorSource(
         *       GeneratorFunction<Long, OUT> generatorFunction
         *      ,long count
         *      ,RateLimiterStrategy rateLimiterStrategy
         *      ,TypeInformation<OUT> typeInfo)
         *   参数说明：
         *      @generatorFunction   ： 指定 GeneratorFunction 实现类(生成数据的具体实现类)
         *      @count               ： 指定输出数据的总行数(如果想一直输出，可以设置为Long.MAX_VALUE)
         *      @rateLimiterStrategy ： 指定发射速率(每秒发射的记录数)
         *      @typeInfo            ： 指定返回值类型
         * */
        DataGeneratorSource<FlinkUser> dataGeneratorSource = new DataGeneratorSource<>(
                // 指定 GeneratorFunction 实现类
                new MyGeneratorFunction(),
                // 指定 输出数据的总行数
                Long.MAX_VALUE,
                // 指定 每秒发射的记录数
                RateLimiterStrategy.perSecond(2),
                // 指定返回值类型
                TypeInformation.of(FlinkUser.class) // 将java的FlinkUser封装成到TypeInformation
        );

        // 3.读取 dataGeneratorSource 中的数据
        org.apache.flink.streaming.api.datastream.DataStreamSource<FlinkUser> DSS = env.fromSource(dataGeneratorSource
                , WatermarkStrategy.noWatermarks()  // 指定水位线生成策略
                , "data-generator");

        /*
         * 注意：生成的dataGeneratorSource为可并行算子
         *      生成的数据会均匀的分配到并行子任务中
         * */

        DSS.print();
        env.execute();
    }

}
