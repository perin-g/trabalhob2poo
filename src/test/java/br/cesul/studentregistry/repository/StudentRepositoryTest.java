package br.cesul.studentregistry.repository;

//como pensamentos esta classe no TDD
//1- oque o repository precisa oferecer pra VM funcionar?
//      o metodos
//          - findAll(),Save(), toggle...(), update()
//2- quais comportamentos minimos devemos provar c os testes?
//      toda vez q der save+findAll, deve aumentar a lista
//      toda vez q chamar o toggleFlag deve alternar o booleano 'active' do documento em questao
//      chamando update , deve alterar o campo grade do documento em questao

//Decisões a serem tomadas
//  testar o repository concreto (mongo real)
//        ->para mostrar input e output de vdd, sem abstração
//        -> isolar os testes: limpar a coleção antes de cada teste
//        -> decidir sobre assincronismo, se os testes vão ou nao se submeter a chamadas que n são imediatas
//

import br.cesul.studentregistry.config.MongoConfig;
import br.cesul.studentregistry.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class StudentRepositoryTest {
    private StudentRepository repo;

    @BeforeEach
    void setup(){
        //aq definimos q o repository deve ter um construtor q aceite setar em qual data base e qual collection eu quero manipular
        // pq nesse caso, a VM vai operar na collection de teste
        repo = new StudentRepository(MongoConfig.database(), "students_test");
        //esse metodo garante o isolamento entre testes por que zera a coleção antes de cada teste
        repo.deleteAll();

    }

    @Test
    @DisplayName("save + findAll, a lista deve crescer")
    void saveAndFindAll_growsList(){
        //Arrange
        Student s1 = new Student("ana", "anax@hotmail.com", 7.5, true );

        //act
        repo.save(s1);
        List<Student> all = repo.findAll();

        //Assert
        assertEquals(1, all.size(), "apos salvar 1 aluno, find all deve retornar 1");
        assertEquals("ana", all.get(0).getName(),"o nome do aluno salvo deve ser 'ana'");

        //outras alternativas:
        // validar todos os campos do aluno inserido
        // usar uma asserçãp ,aos forte (comparando o objetio inteiro)
        // ao inves de dar get no 0, dar um find na lista e verificar pelo id qual é o aluno

    }

    @Test
    @DisplayName("toggleFlag alterna o campo 'active'")
    void toggleFlag_changesActive(){
        //Arrange
        Student s = new Student ("juliano", "juliano@gmail.com", 8.6, true);

        //act
        repo.save(s);
        String id = s.getId();
        //sanity check: garantir q está ativo antes de alternar
    assertTrue(repo.findAll().get(0).isActive(), " pre-condição, deve iniciar como ativo= true");

        //ACT
        repo.toggleFlag(id);

        //assert

        assertFalse(repo.findAll().get(0).isActive(), "apos toggle, acitve deve ser falso");
    }

    @Test
    @DisplayName("updateField deve atualizar a grade")
    void updateField_growsList(){
        //arrange
        Student s = new Student ("Lia", "lia@gmail.com", 5, true);
        repo.save(s);
        assertEquals(5,repo.findAll().get(0).getGrade(), 0.0001, "a nota deve ser cinco antes do teste rodar");

        //act
        String id = s.getId();
        repo.updateField(id, 9.0);

        //assert
        assertEquals(9.0, repo.findAll().get(0).getGrade(), 0.0001,"a nota deve ter sido alterada pra 9");
    }

}
