package br.cesul.studentregistry.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.pojo.PojoCodecProvider;

public class MongoConfig {
    private static MongoDatabase db;

    private MongoConfig() {}

    public static MongoDatabase database(){
        // Para inicializar o MongoDB só uma vez
        if (db == null) {
            var pojo = PojoCodecProvider.builder().automatic(true).build();
            var settings = MongoClientSettings.builder().codecRegistry(
                    CodecRegistries.fromRegistries(
                        MongoClientSettings.getDefaultCodecRegistry(),
                        CodecRegistries.fromProviders(pojo)
                    )
            ).build(); // Montar as settings com o codec
            MongoClient cliente = MongoClients.create(settings);
            db = cliente.getDatabase("studentregistry");
        }

        return db;
    }
}
