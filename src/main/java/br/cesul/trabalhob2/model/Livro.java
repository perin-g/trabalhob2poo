package br.cesul.trabalhob2.model;

public class Livro {
    String autor;
    String titulo;
    boolean emprestado;

    public Livro(String autor, String titulo, int valor) {
        this.autor = autor;
        this.titulo = titulo;
        this.emprestado = false;
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




}
