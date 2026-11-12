package br.cesul.trabalhob2.model;

import br.cesul.trabalhob2.exception.LivroIndisponivelException;

import java.util.UUID;

    public class Livro {
        private String id;
        private String autor;
        private String titulo;
        private boolean emprestado;


    public Livro() {
        // Gera um indentificador unico
        this.id = UUID.randomUUID().toString();
        this.autor = "";
        this.titulo = "";
        this.emprestado = false;
    }

    public Livro(String autor, String titulo,  boolean emprestado) {
        this();
        this.autor = autor;
        this.titulo = titulo;
        this.emprestado = emprestado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAutor() {
        return autor;
    }

    public String getTitulo() {
        return titulo;
}

   public boolean isEmprestado(){
        return emprestado;
   }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void emprestar() {
        if (emprestado) {
            throw new LivroIndisponivelException("o livro não esta disponivel para emprestar");
        } else {
            this.emprestado = true;
        }
    }

    public void devolver() {

    }
}
