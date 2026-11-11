package br.cesul.studentregistry.model;

/*
* Passo a passo para desenvolver um teste:
* 1 - Pensar no CONTRATO (o que a entidade deve garantir?).
*       "Ao nascer, Student tem um ID gerado, Strings Vazias, nota 0.0 e ativo = true"
*
* 2 - Escrever o teste primeiro (RED)
*       Expressar este contrato no teste "defaultConstructor_initializeFields"
*       Rodamos o teste e ele falhará por que ainda não temos a classe implementada
*
* 3 - Implementa o mínimo para passar no teste (GREEN)
*       Implemente um Student com o construtor e os getters/setters
*
* 4 - Refatora (Sem mudar comportamento)
*       Refinar, otimizar, nomear melhor, limpar o código, pensar em desempenho...
*       Sempre mantendo os teses passando
*
* Por que testar o model primeiro?
*   - É a parte mais estável e sem dependências (sem UI, banco, threads)
*   - Erros encontrados aqui são fáceis de consertar
*   - Os contratos fechados nas classes de domínio, sustentam os testes das camadas superiores
* */

// Import estático para deixar asserts mais legíveis
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
* Padrão AAA para testes:
* - Arrange (Preparar) - Cria dados e contexto necessários para a ação
* - Act (Agir) - Chamamos um comportamento específico do objeto testado
* - Assert (Afirmar) - Compara o resultado obtido com o resultado esperado
*
* Por que é importante deixar apenas uma ação por teste?
* - Para que a falha conte uma história clara
* - Evitar muitas verificações num teste só, ou seja, testes que quebram por múltiplas razões
* */
public class StudentTest {
    @Test
    @DisplayName("Construtor padrão inicia ID, strings vazias, nota 0.0 e ativo=true")
    void defaultConstructor_initializeFilds() {
        // Arrange - não precisa neste caso
        // Act
        Student s = new Student();
        // Assert
        // 1° parâmetro = É o resultado que obtive após o Act
        // 2° parâmetro = A mensagem de erro exibida quando este teste falhar
        assertNotNull(s.getId(), "ID deve ser gerado, nunca NULL");
        assertEquals("", s.getName(), "Nome deve iniciar como string vazia");
        assertEquals("", s.getEmail(), "E-mail deve iniciar como string vazia");
        assertEquals(0.0, s.getGrade(), 0.0001, "Nota deve iniciar exatamente com 0.0");
        assertTrue(s.isActive(), "Aluno novo deve vir com ativo = true");
    }

    @Test
    @DisplayName("Construtor completo preenche todos os campos (exceto ID)")
    void fullConstructor_setsFields(){
        // Arrange
        String name = "Gabriela";
        String email = "gabriela@exemplo.com";
        double grade = 8.5;
        boolean active = false;

        // Act
        Student s = new Student(name, email, grade, active);

        // Assert
        assertNotNull(s.getId());
        assertEquals(name, s.getName(), "Nome deve refletir o argumento 'name'");
        assertEquals(email, s.getEmail(), "E-mail deve refletir o argumento 'email'");
        // Por que colocamos o delta 0.0001 em assertEquals de double?
        // Em Java (Acredito ser na maioria das linguagens), números com ponto flutuante sofrem imprecisão binária.
        // Portanto em alguns casos, como por ex.: uma operação que deveria dar 0.3 pode virar 0.30000000004
        // Neste caso um assertEquals(0.3, valor) pode falhar.
        // E o JUnit fornece a opção de definir uma tolerância para doubles.
        // Onde o delta é o orro máximo esperado
        assertEquals(grade, s.getGrade(), 0.0001,"Grade deve refletir o argumento 'grade'");
        assertFalse(s.isActive(), "Active deve refletir o argumento 'active', que no teste é falso");
    }
}
