package org.softwarecave.springbootnotecategorizer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.softwarecave.springbootnotecategorizer.categorizer.Categorizer;
import org.softwarecave.springbootnotecategorizer.categorizer.CategorizerFactory;

import java.util.Properties;

public class NoteCategorizerApp {

    private final String bootstrapServers;
    private final String inputTopic;
    private final String outputTopic;

    public NoteCategorizerApp(String bootstrapServers, String inputTopic, String outputTopic) {
        this.bootstrapServers = bootstrapServers;
        this.inputTopic = inputTopic;
        this.outputTopic = outputTopic;
    }

    public void run() {
        Properties config = createConfig();
        Topology topology = createTopology();

        // intentionally not using try-with-resources here to keep the application running until shutdown
        KafkaStreams streams = new KafkaStreams(topology, config);
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }

    private Properties createConfig() {
        Properties props = new Properties();
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "note-categorizer");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Long().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        return props;
    }

    private Topology createTopology() {
        Categorizer keywordBasedCategorizer = new CategorizerFactory().getKeywordBasedCategorizer();

        StreamsBuilder builder = new StreamsBuilder();
        KStream<Long, String> stickyNoteStream = builder.stream(inputTopic);

        KStream<Long, String> categorizedStickyNoteStream = stickyNoteStream
                .mapValues((value) -> new StickyNoteModifier(value, keywordBasedCategorizer)
                        .addCategories(2)
                        .getModifiedObjectJson());

        categorizedStickyNoteStream.to(outputTopic, Produced.with(Serdes.Long(), Serdes.String()));

        return builder.build();
    }
}
