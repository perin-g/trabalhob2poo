package br.cesul.trabalhob2.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.pojo.PojoCodecProvider;

public class MongoConfig {
    private static MongoDatabase db;

    private MongoConfig() {}

    public static MongoDatabase database() {
     if (db == null) {
         var pojo = PojoCodecProvider.builder().automatic(true).build();
         var settings = MongoClientSettings.builder().codecRegistry(
                 CodecRegistries.fromRegistries(
                         MongoClientSettings.getDefaultCodecRegistry(),
                         CodecRegistries.fromProviders(pojo)
                 )
         ).build();
         MongoClient client  = MongoClients.create(settings);
         db = client.getDatabase("biblioteca");
     }

     return db;
    }
}
