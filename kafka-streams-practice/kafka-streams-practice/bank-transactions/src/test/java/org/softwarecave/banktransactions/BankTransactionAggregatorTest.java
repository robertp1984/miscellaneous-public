package org.softwarecave.banktransactions;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.TestOutputTopic;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.TopologyTestDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class BankTransactionAggregatorTest {

    private TopologyTestDriver testDriver;

    @BeforeEach
    public void setup() {
        BankTransactionAggregator bankTransactionAggregator = new BankTransactionAggregator();
        Topology topology = bankTransactionAggregator.createTopology();
        testDriver = new TopologyTestDriver(topology);
    }

    @AfterEach
    public void tearDown() {
        testDriver.close();
    }

    @Test
    public void testBankTransactionsNoClients() {
        var inputTopic = getInputTopic();
        var outputTopic = getOutputTopic();


        Map<String, String> outputMap = outputTopic.readKeyValuesToMap();
        assertThat(outputMap.size()).isEqualTo(0);
    }

    @Test
    public void testBankTransactionsOneClient() {
        var inputTopic = getInputTopic();
        var outputTopic = getOutputTopic();

        inputTopic.pipeInput("John", "{\"clientName\":\"John\",\"amount\":50.0,\"transactionDateTime\":\"2024-06-01T10:00:00\"}");

        Map<String, String> outputMap = outputTopic.readKeyValuesToMap();
        assertThat(outputMap.size()).isEqualTo(1);
        assertThat(outputMap.get("John"))
                .isNotNull()
                .contains("\"clientName\":\"John\"")
                .contains("\"totalAmount\":50.0")
                .contains("\"transactionCount\":1")
                .contains("\"latestTransactionDateTime\":\"2024-06-01T10:00:00\"");
    }


    @Test
    public void testBankTransactionsTwoClients() {
        var inputTopic = getInputTopic();
        var outputTopic = getOutputTopic();

        inputTopic.pipeInput("John", "{\"clientName\":\"John\",\"amount\":50.0,\"transactionDateTime\":\"2024-06-01T10:00:00\"}");
        inputTopic.pipeInput("John", "{\"clientName\":\"John\",\"amount\":25.0,\"transactionDateTime\":\"2024-06-01T11:00:00\"}");
        inputTopic.pipeInput("Jane", "{\"clientName\":\"Jane\",\"amount\":30.0,\"transactionDateTime\":\"2024-06-01T12:00:00\"}");

        Map<String, String> outputMap = outputTopic.readKeyValuesToMap();
        assertThat(outputMap).hasSize(2);
        assertThat(outputMap.get("John"))
                .isNotNull()
                .contains("\"clientName\":\"John\"")
                .contains("\"totalAmount\":75.0")
                .contains("\"transactionCount\":2")
                .contains("\"latestTransactionDateTime\":\"2024-06-01T11:00:00\"");
        assertThat(outputMap.get("Jane"))
                .isNotNull()
                .contains("\"clientName\":\"Jane\"")
                .contains("\"totalAmount\":30.0")
                .contains("\"transactionCount\":1")
                .contains("\"latestTransactionDateTime\":\"2024-06-01T12:00:00\"");

    }

    private TestInputTopic<String, String> getInputTopic() {
        return testDriver.createInputTopic("bank.transactions",
                new StringSerializer(), new StringSerializer());
    }

    private TestOutputTopic<String, String> getOutputTopic() {
        return testDriver.createOutputTopic("bank.transactions.aggregated",
                new StringDeserializer(), new StringDeserializer());
    }

}
