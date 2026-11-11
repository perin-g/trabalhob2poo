package br.cesul.studentregistry.Viewmodel;
import br.cesul.studentregistry.config.MongoConfig;
import br.cesul.studentregistry.model.Student;
import br.cesul.studentregistry.repository.StudentRepository;
import br.cesul.studentregistry.viewmodel.StudentListViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// a view model mantem o estado da tela (itens da lista, o loading e o erro)
//e expoe metodos de ação (loadAll, addNew, toggleFlag e updateField


//quais contratos a vm deve cumprir
// loadAll:
//      ligar o loading durante a operacao e desligar no final
//      preencher a lista de itens com oque veio do repository
// addNew:
//      pegar e validar os campos. se invalido nao salva e escreve no label error, se validos, salva no repo e atualiza a lista
//toggleFlag:
//      solicitar ao repository q alternet o active do estudante x e att a lista de itens
//updateField:
// valida a nota, se invalida, nao atualiza e escreve no label error, se valida atualizar no repo e reflete na lista da table

public class StudentViewModelTest {

    private StudentRepository repo;
    private StudentListViewModel vm;

    @BeforeEach
    void setUp() {
        repo = new StudentRepository(MongoConfig.database(), "students_vm_test");
        repo.deleteAll();
        vm = new StudentListViewModel(repo);
    }
    @Test
    @DisplayName("loadall preenche itens e desliga loading no fim")
    void loadAll_changesLoadingAndItems() {

        // Padrão AAA para testes:
        // Arrange (preparar) - cria dados e contexto pra ação
        // Act (agir) - chamamos um comportamento especifico do objeto testado
        // Assert (Afirmar) - Compara o resultado obtido com o resultado esperado

        // Arrange
        repo.save(new Student("Ana", "ana@gmail.com", 7.5, true));
        repo.save(new Student("joao", "joao@gmail.com", 8, true));

        // Act
        vm.loadAll();
       // A vm deve ligar o loading, consultar o repo.findAll(), e setar itens na lista


        // Assert
       assertFalse(vm.loadingProperty().get(), "loading deve ser falso no final");
       assertEquals(2, vm.getItems().size(), "itens deve conter exatamente 2 alunos");
    }

    @Test
    @DisplayName("addNew Valida entradas e insere quando válido")
    void addNew_validatesAndInserts() {
        // Cenário 1 - Nome inválido
        vm.addNew("  ", "a@x.com", 8);
        assertTrue(vm.getItems().isEmpty(), "Não deve inserir com nome inválido");
        assertTrue(vm.errorProperty().get().contains("Nome"), "A mensagem deve explicar que o nome é inválido");
        // Cenário 2 - E-mail inválido
        vm.addNew("Gabriela", "a.x.com", 10);
        assertTrue(vm.getItems().isEmpty(), "Não deve inserir com e-mail inválido");
        assertTrue(vm.errorProperty().get().contains("E-mail"), "A mensagem deve explicar que o e-mail é inválido");
        // Cenário 3 - Nota inválida
        vm.addNew("Gabriela", "gabriela@x.com", 11);
        assertTrue(vm.getItems().isEmpty(), "Não deve inserir com nota inválido");
        assertTrue(vm.errorProperty().get().contains("Nota"), "A mensagem deve explicar que o e-mail é inválido");
        // Cenário 4 - Tudo válido
        vm.addNew("Ana", "ana@x.com", 8);
        assertEquals(1, vm.getItems().size(), "Deve ter um aluno na lista");
        assertEquals("Ana",vm.getItems().get(0).getName(), "O aluno inserido deve aparecer nos itens da vm");
    }

    //toggleFlag_updatesItem
    @Test
    @DisplayName("toggleFlag alternta 'active' e reflete na lista")
    void toggleFlag_updatesItem() {
        var s = new Student("Gabriela", "gabriela@vida.com.br", 9, true);
        repo.save(s);
        vm.loadAll();
        String id = vm.getItems().get(0).getId();

        vm.toggleFlag(id);
        assertFalse(vm.getItems().get(0).isActive(), "Deveria ter alternado para falso");
    }

    //updateField_validatesAndUpdates
    @Test
    @DisplayName("updateField valida faixa e atualiza nota")
    void updateField_validatesAndUpdates() {
        var s = new Student("Gabriela", "gabriela@vida.com.br", 9, true);
        repo.save(s);
        vm.loadAll();
        String id = vm.getItems().get(0).getId();

        // Cenário 1 - Nota fora da faixa
        vm.updateField(id, -1);
        assertTrue(vm.errorProperty().get().contains("Nota"), "VM deve exibir erro");

        // Cenário 2 - Válido
        vm.updateField (id, 9.0);
        assertEquals(9, vm.getItems().get(0).getGrade(), 0.0001, "A nota deve ser atualizada e refletida na lista");
    }
}
