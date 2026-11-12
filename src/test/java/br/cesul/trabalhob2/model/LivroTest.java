package br.cesul.trabalhob2.model;

import br.cesul.trabalhob2.exception.LivroIndisponivelException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LivroTest {
    @Test
    @DisplayName("Construtor padrão inicia id, Strings vazias e emprestado = false")
    void defaultConstructor_initializeFields(){

        Livro l = new Livro();

        assertNotNull(l.getId(), "ID deve ser gerado, Nunca NULL");
        assertEquals("", l.getAutor(), "Autor deve ser gerado como string vazia");
        assertEquals("", l.getTitulo(), "Titulo deve ser gerado como string vazia");
        assertFalse(l.isEmprestado(), "Livro novo deve vir como emprestado = false");

    }

    @Test
    @DisplayName("Construtor completo preenche todos os campos (exeto id)")
    void fullContructor_setsFields() {
        String autor = "Ana";
        String titulo = "Moby Dick";
        boolean emprestado = true;

        Livro l = new Livro(autor, titulo, emprestado);

        assertNotNull(l.getId(), "ID deve ser gerado");
        assertEquals(autor, l.getAutor(), "Autor deve refletir o argumento 'autor'");
        assertEquals(titulo, l.getTitulo(), "Titulo deve refletir o argumento 'titulo'");
        assertFalse(l.isEmprestado(), "active deve refletir o argumento 'emprestado', que no teste é falso");
    }

    @Test
    @DisplayName("Deve marcar como emprestando quando emprestado")
    void shouldMarkasLoandedWhenLoaned() {
        Livro livro = new Livro("POO em Java", "Gilberto", false);
        livro.emprestar();
        assertTrue(livro.isEmprestado());
    }

    @Test
    @DisplayName("Deve notificar que o livro já foi emprestado")
    void bookUnavailableException () {
        //
        Livro livro = new Livro ("Java: Ruindade pura", "Perin Gabriel", true);
        livro.emprestar();

        assertThrows(LivroIndisponivelException.class, livro::emprestar);
    }

    @Test
    @DisplayName("Deve marcar o livro como devolvido")
    void devolverMarcarLivroComoDisponivel
}
