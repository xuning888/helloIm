package com.github.xuning888.helloim.contract.kafka;

import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author xuning
 * @date 2025/8/3 0:25
 */
public class KafkaProperties {

    /**
     * kafka服务端地址
     */
    private String bootstrapServers;

    /**
     * kafka producer 配置
     */
    private Producer producer;

    /**
     * kafka consumer 配置
     */
    private Consumer consumer;

    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public void setBootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public Properties buildConsumerProperties() {
        Map<String, String> prop = this.consumer.buildConsumerProperties();
        if (!prop.containsKey(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG)) {
            prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServers);
        }
        Properties properties = new Properties();
        properties.putAll(prop);
        return properties;
    }

    public Properties buildProducerProperties() {
        Map<String, String> prop = this.producer.buildProducerProperties();
        if (!prop.containsKey(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG)) {
            prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServers);
        }
        Properties properties = new Properties();
        properties.putAll(prop);
        return properties;
    }

    public static class Producer {
        private String bootstrapServers;
        private String keySerializer;
        private String valueSerializer;
        private String acks;
        private String retries;
        private String batchSize;
        private String bufferMemory;
        private String maxInflightRequestsPerConnection;
        private String lingerMs;

        public String getBootstrapServers() {
            return bootstrapServers;
        }

        public void setBootstrapServers(String bootstrapServers) {
            this.bootstrapServers = bootstrapServers;
        }

        public String getKeySerializer() {
            return keySerializer;
        }

        public void setKeySerializer(String keySerializer) {
            this.keySerializer = keySerializer;
        }

        public String getValueSerializer() {
            return valueSerializer;
        }

        public void setValueSerializer(String valueSerializer) {
            this.valueSerializer = valueSerializer;
        }

        public String getAcks() {
            return acks;
        }

        public void setAcks(String acks) {
            this.acks = acks;
        }

        public String getRetries() {
            return retries;
        }

        public void setRetries(String retries) {
            this.retries = retries;
        }

        public String getBatchSize() {
            return batchSize;
        }

        public void setBatchSize(String batchSize) {
            this.batchSize = batchSize;
        }

        public String getBufferMemory() {
            return bufferMemory;
        }

        public void setBufferMemory(String bufferMemory) {
            this.bufferMemory = bufferMemory;
        }

        public String getMaxInflightRequestsPerConnection() {
            return maxInflightRequestsPerConnection;
        }

        public void setMaxInflightRequestsPerConnection(String maxInflightRequestsPerConnection) {
            this.maxInflightRequestsPerConnection = maxInflightRequestsPerConnection;
        }

        public String getLingerMs() {
            return lingerMs;
        }

        public void setLingerMs(String lingerMs) {
            this.lingerMs = lingerMs;
        }

        public Map<String, String> buildProducerProperties() {
            Map<String, String> prop = new HashMap<>();

            // kafka 服务端地址
            if (StringUtils.isNotBlank(bootstrapServers)) {
                prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
            }

            // key的序列化方式
            if (StringUtils.isNotBlank(keySerializer)) {
                prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
            }

            // value的序列化方式
            if (StringUtils.isNotBlank(valueSerializer)) {
                prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
            }

            // acks
            if (StringUtils.isNotBlank(acks)) {
                prop.put(ProducerConfig.ACKS_CONFIG, acks);
            }

            // 重试次数
            if (StringUtils.isNotBlank(retries)) {
                prop.put(ProducerConfig.RETRIES_CONFIG, retries);
            }

            // 每个partition发送批次的大小
            if (StringUtils.isNotBlank(batchSize)) {
                prop.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
            }

            // 积压缓冲区的大小
            if (StringUtils.isNotBlank(bufferMemory)) {
                prop.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
            }

            // 单个conn中飞行消息的数量
            if (StringUtils.isNotBlank(maxInflightRequestsPerConnection)) {
                prop.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, maxInflightRequestsPerConnection);
            }

            // selector 阻塞的时间
            if (StringUtils.isNotBlank(lingerMs)) {
                prop.put(ProducerConfig.LINGER_MS_CONFIG, lingerMs);
            }
            return prop;
        }
    }

    public static class Consumer {
        private String bootstrapServers;
        private String keyDeserializer;
        private String valueDeserializer;
        private String enableAutocommit;
        private String autoOffsetReset;
        private String groupId;
        private String maxPollRecords;

        public String getBootstrapServers() {
            return bootstrapServers;
        }

        public void setBootstrapServers(String bootstrapServers) {
            this.bootstrapServers = bootstrapServers;
        }

        public String getKeyDeserializer() {
            return keyDeserializer;
        }

        public void setKeyDeserializer(String keyDeserializer) {
            this.keyDeserializer = keyDeserializer;
        }

        public String getValueDeserializer() {
            return valueDeserializer;
        }

        public void setValueDeserializer(String valueDeserializer) {
            this.valueDeserializer = valueDeserializer;
        }

        public String getEnableAutocommit() {
            return enableAutocommit;
        }

        public void setEnableAutocommit(String enableAutocommit) {
            this.enableAutocommit = enableAutocommit;
        }

        public String getAutoOffsetReset() {
            return autoOffsetReset;
        }

        public void setAutoOffsetReset(String autoOffsetReset) {
            this.autoOffsetReset = autoOffsetReset;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getMaxPollRecords() {
            return maxPollRecords;
        }

        public void setMaxPollRecords(String maxPollRecords) {
            this.maxPollRecords = maxPollRecords;
        }

        public Map<String, String> buildConsumerProperties() {
            Map<String, String> prop = new HashMap<>();

            if (StringUtils.isNotBlank(bootstrapServers)) {
                prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
            }

            // key 的反序列化
            if (StringUtils.isNotBlank(keyDeserializer)) {
                prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer);
            }

            // value 的序列化
            if (StringUtils.isNotBlank(valueDeserializer)) {
                prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer);
            }

            // 是否自动提交offset
            if (StringUtils.isNotBlank(enableAutocommit)) {
                prop.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutocommit);
            }

            // 自动重置offset的策略
            if (StringUtils.isNotBlank(autoOffsetReset)) {
                prop.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
            }


            // 消费组
            if (StringUtils.isNotBlank(groupId)) {
                prop.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
            }

            // 每次拉取消息的最大数量
            if (StringUtils.isNotBlank(maxPollRecords)) {
                prop.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
            }

            return prop;
        }
    }
}