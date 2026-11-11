package br.cesul.studentregistry.repository;

import br.cesul.studentregistry.model.Student;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Updates.set;


public class StudentRepository {
    private final MongoCollection<Student> col;

    //aq ele cria dois construtores, um passando a colecao padrao, ditando q ela é a padrao, e pro caso do nosso teste,
    //ele passa um banco especifico pra teste

    public StudentRepository(MongoDatabase db, String collectionName){
        this.col = db.getCollection(collectionName, Student.class);
    }

    public StudentRepository(MongoDatabase db) {
        this(db, "students");
    }

    public void deleteAll(){
        //delete all
        // oque faz: apaga tds os documentos da coleção em questão
        // por que existe: para que cada teste comece com uma base vazia evitando vazamento de estados de testes anteriores
        //ent poderia fazer como
        //col.drop()
        //ou
                col.deleteMany(new Document());
                //qnd passa o deleteMany com filtro vazio ele deleta tds os documentos, se eu chamasse o drop
        //ele derruba a coleção tb, mas ele é mais intrusivo
    }

    public Student save (Student s){
        col.insertOne(s);
        return s;
    }

    public List<Student> findAll(){
        var list = new ArrayList<Student>();
        //ele itera sobre todos os elementos q estou trabalhando, e adiciona de um por um à nossa lista
        col.find().into(list);
        return list;
    }

    private Bson  byId(String id){
        return Filters.or(
                Filters.eq("_id", id),
                Filters.eq("id", id)
        );
    }

    public void toggleFlag (String id){
        var found = col.find(byId(id)).first();
        if (found != null) {
            col.updateOne(byId(id),set("active", !found.isActive()));
        }
    }

    public void updateField(String id, double newGrade){
        col.updateOne(byId(id), set("grade", newGrade));
    }
}
