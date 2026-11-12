package br.cesul.trabalhob2.viewModel;

import br.cesul.trabalhob2.model.Livro;
import br.cesul.trabalhob2.repository.LivroRepository;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class LivroViewModel {
    private final ObservableList<Livro> items = FXCollections.observableArrayList();
    private final BooleanProperty loading = new SimpleBooleanProperty(false);
    private final StringProperty error = new SimpleStringProperty("");

    private final LivroRepository  livroRepo;
    public LivroViewModel(LivroRepository livroRepo) {
                this.livroRepo = livroRepo;
    }
    public BooleanProperty loadingProperty() {
        return loading;
    }

    public StringProperty errorProperty() {
        return error;
    }

    public ObservableList<Livro> getItems() {
                return items;
    }

    public ObservableList<Livro> getItemsList() {
        return items;
    }

    //funcoes aqui

    public void loadAll (){
        loading.set(true);
        error.set("");
        try{
            List<Livro> result = livroRepo.findAll();
            items.setAll(result);

        }catch(Throwable ex){
            error.set("falha ao carregar: " + ex.getMessage());
        } finally{
            //depois de tudo faça isso
            loading.set(false);
        }
    }






    public void addNew(String autor, String titulo, boolean emprestado){

    }

}

