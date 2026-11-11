package br.cesul.studentregistry.viewmodel;

import br.cesul.studentregistry.model.Student;
import br.cesul.studentregistry.repository.StudentRepository;
import br.cesul.studentregistry.util.Validators;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class StudentListViewModel {
    //propriedades observáveis -> estado da tela
    private final ObservableList<Student> items = FXCollections.observableArrayList();

    //indicador de visibilidade do loading
    //a view faz o bind progress.visibleProperty().bind (vm.loadingProperty)
    private final BooleanProperty loading = new SimpleBooleanProperty(false);
    private final StringProperty error = new SimpleStringProperty("");

    //dependencia de dados
    private final StudentRepository repository;
    public StudentListViewModel(StudentRepository repository) {
        this.repository = repository;
    }


    public BooleanProperty loadingProperty() {
        return loading;
    }
    public StringProperty errorProperty() {
        return error;
    }

    public ObservableList<Student> getItems() {
        return items;
    }

    //carregar todos os estudantes da repo
    //ligar o loading durante a operacao
    //limpar o lblError
    //setar os itens na lista
    //em caso de falha, setar o lblError
    public void loadAll() {
        loading.set(true);
        error.set("");
        try {
            List<Student> result = repository.findAll();
            items.setAll(result);
        } catch (Throwable ex) {
            error.set("Falha ao carregar: " + ex.getMessage());
        } finally {
            loading.set(false);
        }
    }

    public void addNew(String name, String email, double grade) {
        if (!Validators.notBlank(name)){
            error.set("Nome não pode ser vazio");
            return;
        }
        if (!Validators.email(email)){
            error.set("E-mail não pode ser vazio");
            return;
        }
        if (!Validators.inRange(grade, 0, 10)){
            error.set("Nota deve estar entre 0 e 10");
            return;
        }

        loading.set(true);
        error.set("");
        try {
            Student s= new Student(name, email, grade, true);
            repository.save(s);
            items.setAll(repository.findAll());
        } catch (Throwable ex) {
            error.set("Falha ao salvar: " + ex.getMessage());
        } finally {
            loading.set(false);
        }
    }

    public void toggleFlag(String id) {
        loading.set(true);
        error.set("");
        try {
            repository.toggleFlag(id);
            items.setAll(repository.findAll());
        } catch (Exception ex) {
            error.set("Falha ao alternar: " + ex.getMessage());
        } finally {
            loading.set(false);
        }
    }

    public void updateField(String id, double newGrade) {
        if (!Validators.inRange(newGrade, 0, 10)){
            error.set("Nota deve estar entre 0 e 10");
            return;
        }
        loading.set(true);
        error.set("");
        try {
            repository.updateField(id, newGrade);
            items.setAll(repository.findAll());
        } catch (Exception ex) {
            error.set("Falha ao atualizar nota: " + ex.getMessage());
        } finally {
            loading.set(false);
        }
    }
}
