package br.cesul.trabalhob2.viewModelTest;

import br.cesul.trabalhob2.config.MongoConfig;
import br.cesul.trabalhob2.model.Livro;
import br.cesul.trabalhob2.repository.LivroRepository;
import br.cesul.trabalhob2.viewModel.LivroViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LivroViewModelTest {
        private LivroRepository repo;
        private LivroViewModel vm;

    @BeforeEach
    void setUp(){
        repo = new LivroRepository(MongoConfig.database(), "banco_de_testes");
        repo.deleteAll();
        vm = new LivroViewModel(repo);
    }

    @Test
    @DisplayName("loadAll preenche itens e desliga loading no fim")
    void loadAll_changesLoadingAndItens() {
        repo.save(new Livro("Ana","Moby Dick",true));
        repo.save(new Livro("Joao","Criminal",false));

        vm.loadAll();

        assertFalse(vm.loadingProperty().get(),"Loading deve ser falso no fim");
        assertEquals(2,vm.getItemsList().size(),"Itens deve conter exatamente 2 livrod");
    }

    @Test
    @DisplayName("addNew valida entradas e insere quando valido")
    void  addNew_validatesAndInserts(){
        // Cenario 1 - autor invalido
        vm.addNew(" ", "Moby Dick");
        assertTrue(vm.getItemsList().isEmpty(),"Não deve inserir com autor invalido");
        assertTrue(vm.errorProperty().get().contains("Autor"), "A mensagem deve explicar que o autor é invalido");

        // Cenario 2 - Titulo invalido
        vm.addNew("Joao", "");
        assertTrue(vm.getItens().isEmpty(),"Não deve inserir Email invalido");
        assertTrue(vm.errorProperty().get().contains("Email"), "A mensagem deve explicar o email é invalido");

        // Cenario 3 - Nota invalida
        vm.addNew("Paola", "P@kol.com", 14);
        assertTrue(vm.getItens().isEmpty(),"Não deve inserir com nota invalido");
        assertTrue(vm.errorProperty().get().contains("Nota"), "A mensagem deve explicar que a é nota invalida");

        // Cenario 4 - Tudo Valido
        vm.addNew("Pedro", "f@lok.com", 7);
        assertEquals(1, vm.getItens().size(), "Deve ter um aluno na lista");
        assertEquals("Pedro", vm.getItens().get(0).getName(), "O anluno inserido deve aparecer nos itens da vm");
    }


}
