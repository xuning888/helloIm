package com.github.xuning888.helloim.contract.kafka;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author xuning
 * @date 2025/8/2 16:00
 */
public abstract class MsgKafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(MsgKafkaConsumer.class);

    private static final long timeoutSeconds = 10;

    private final Consumer<String, byte[]> consumer;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private final Set<String> topics = new HashSet<>();

    private final AtomicBoolean start = new AtomicBoolean();

    private final boolean autoCommit;

    public MsgKafkaConsumer(Properties properties, List<String> topics) {
        consumer = new KafkaConsumer<>(properties);
        String autoCommitConfig = properties.getProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG);
        if (autoCommitConfig == null) {
            autoCommit = true;
        } else {
            autoCommit = "true".equals(autoCommitConfig);
        }
        this.topics.addAll(topics);
    }

    public void start() {
        if (consumer != null) {
            if (start.compareAndSet(false, true)) {
                // 订阅topics
                consumer.subscribe(this.topics);
                // 开始消费
                consume();
            } else {
                logger.warn("重复启动消费者！！！！！！");
            }
        }
    }

    public boolean stop() {
        // 通知doConsume方法退出循环
        start.compareAndSet(true, false);
        executorService.shutdown();
        return true;
    }

    private void consume() {
        this.executorService.submit(this::doConsume);
    }

    private void doConsume() {
        try {
            while (start.get()) {
                try {
                    process();
                } catch (Exception ex) {
                    logger.error("doConsume error", ex);
                }
            }
        } catch (Exception ex) {
            logger.error("consumer error", ex);
        } finally {
            if (consumer != null) {
                consumer.close();
            }
        }
    }

    private void process() {
        String batchLogId = UUID.randomUUID().toString();
        // 拉取消息
        ConsumerRecords<String, byte[]> records = consumer.poll(Duration.ofSeconds(timeoutSeconds));
        if (records.isEmpty()) {
            return;
        }
        // 查看这组消息都来自哪些 TopicPartition, 按照 TopicPartition 分组消息
        Set<TopicPartition> partitions = records.partitions();

        long begin = System.currentTimeMillis();

        // 按照 TopicPartition 分组处理
        for (TopicPartition partition : partitions) {
            // 拿到一个partition的消息
            List<ConsumerRecord<String, byte[]>> partitionRecords = records.records(partition);
            for (ConsumerRecord<String, byte[]> record : partitionRecords) {
                String logId = UUID.randomUUID().toString();
                try {
                    processRecord(record, logId);
                } catch (Exception ex) {
                    logger.error("process processRecord failed, batchLogId: {}, logId: {}", batchLogId, logId, ex);
                }
            }

            // 打印日志记录一下
            long lastOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
            logger.info("process topic: {}, partition: {}, offset: {}, recordSize: {}, cost: {}ms, batchLogId: {}",
                    partition.topic(), partition.partition(), lastOffset, partitionRecords.size(),
                    System.currentTimeMillis() - begin, batchLogId);

            if (!autoCommit) {
                // 确保消息都消费完成后手动提交
                consumer.commitSync(Collections.singletonMap(partition,
                        new OffsetAndMetadata(lastOffset + 1)));
            }
        }

        long consumerTotalTime = System.currentTimeMillis() - begin;
        if (consumerTotalTime > 100) {
            logger.info("process consumerTotalTime: {}ms, batchLogId: {} ", consumerTotalTime, batchLogId);
        }
    }

    public abstract void processRecord(ConsumerRecord<String, byte[]> record, String logId);

}