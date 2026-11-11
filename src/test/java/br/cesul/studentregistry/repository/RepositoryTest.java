package br.cesul.studentregistry.repository;

/*
* Como pensamos esta classe no TDD:
* 1 - O que o repository precisa oferecer pra VM funcionar?
*   -> findAll(), save(), toggle...(), update()
* 2 - Quais comportamentos mínimos devemos provar com os testes?
*   -> Ex.: Toda vez que der save + findAll, deve aumentar a lista
*           Toda vez que chamar o toggleFlag deve alternar o booleano 'active' do documento em questão
*           Chamando update, deve alterar o campo grade do documento em questão
*
* Decisões:
*   - Testar o repositorio CONCRETO (MONGO REAL)
*       -> Para mostrar input e output de verdade, sem abstração
*   - Isolar os testes:
*       -> Limpar a coleção ANTES de cada teste
*   - Decidir sobre assincronismo:
*       -> Se os testes vão ou não se submeter a chamadas que não são imediatas
* */

import br.cesul.studentregistry.config.MongoConfig;
import br.cesul.studentregistry.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class RepositoryTest {
    private StudentRepository repo;

    @BeforeEach
    void setup() {
        // Aqui definimos que o respository deve ter um construtor que aceite setar
        // em qual database e qual collection eu quero manipular. Por quê?
        // Por que neste caso, a VM vai operar na collection de produção e o teste vai operar na collection de teste
        repo = new StudentRepository(MongoConfig.database(), "students_test");

        //Este método garante o isolamento entre testes porque zer a coleção antes de cada teste.
        repo.deleteAll();
    }

    @Test
    @DisplayName("Save + findAll a lista deve crescer")
    void saveAndFindAll_growsList() {
        // Arrange
        Student s1 = new Student("Gabriela", "gabriela@gmail.com", 9.8, true);

        // Act
        repo.save(s1);
        List<Student> all = repo.findAll();

        // Assert
        assertEquals(1, all.size(), "Após salvar 1 aluno, findAll deve retornar 1");
        assertEquals("Gabriela",all.get(0).getName(), "O nome do aluno salvo deve ser 'Gabriela'");
        /*
        * Alternativas:
        *   - Validar todos os campos do aluno inserido.
        *   - Usar uma asserção mais forte (comprar o objeto inteiro)
        *   - Ao invés de dar get no 0, dar um find na lista e verificar pelo ID qual é o aluno
        * */
    }

    /*
    * Faça um teste para testar se quando eu chamei o toggleFlag
    * do repo o atributo active do Student em questão foi alterado
    * */

    @Test
    @DisplayName("toggleFlag altera o estado de ativo")
    void toggleFlag_changesActive(){
        // Arrange
        Student s = new Student("Gabriel", "gabriel@gmail.com", 8, true);
        repo.save(s);
        String id = s.getId().toString();
        // Sanity Check: Garantir que está ativo ANTES de alternar, para evitar falso positivo.
        assertTrue(repo.findAll().get(0).isActive(), "Pré-condição: deve iniciar como ativo = true");
        // Act
        repo.toggleFlag(id);
        // Assert
        assertFalse(repo.findAll().get(0).isActive(), "Após toggle, active deve ser falso");
    }

    @Test
    @DisplayName("updateFild deve atualizar a grade")
    void updateFild_udateGrade(){
        // Arrange
        Student s = new Student("Maycão", "maycao@yandex.com", 5.0, true);
        repo.save(s);
        String id = s.getId();
        assertEquals(5.0, repo.findAll().get(0).getGrade(), "A nota deve ser 5 antes do teste rodar");
        // Act
        repo.updateField(id, 9.0);
        // Assert
        assertEquals(9.0, repo.findAll().get(0).getGrade(), "A nova deve ter sido atualizada para 9.0");
    }
}
