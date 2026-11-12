package br.cesul.trabalhob2.repository;

import br.cesul.trabalhob2.model.Livro;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Updates.set;
import static java.lang.reflect.Array.set;

public class  LivroRepository {
    private final MongoCollection<Livro> col;

    public LivroRepository(MongoDatabase db, String collectionName) {
        this.col = db.getCollection(collectionName, Livro.class);
    }

    public LivroRepository(MongoDatabase db) {
    this(db, "Livros");
    }

    public void  deleteAll() {
        col.deleteMany(new Document());
    }

    public Livro save (Livro livro) {
        col.insertOne(livro);
        return livro;
    }

    public List<Livro> findAll(){
    var list = new ArrayList<Livro>();
    col.find().into(list);
    return list;
    }

    private Bson byId(String id){
    return Filters.or(
            Filters.eq("_id", id),
            Filters.eq("id", id)
            );
    }

    public void toggleFlag (String id){
    var found = col.find(byId(id)).first();
    if (found != null){
        col.updateOne(byId(id),set("emprestado", !found.isEmprestado()));
     }
    }

}





