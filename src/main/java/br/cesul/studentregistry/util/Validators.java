package br.cesul.studentregistry.util;

public class Validators {
    //denota q esta classe é estatica e nn precisa de instancias
    private Validators(){}

    public static boolean notBlank(String s){
        //1- checar null
        //2- remover espacos das pontas (com o trim())
        //3- verifico se sobrou algo
        return s != null && !s.trim().isEmpty();

    }
    //validação do email
    // precisa ter o @
    // precisa ter . depois do @
    // o . nao pode ser o ultimo caractere
    //negacao de notBlank é invalido

    public static boolean email(String s){
        if(!notBlank(s)) return false;

        // encontrar posicoes de @ e do ultimo ponto
        int at = s.indexOf("@"); //pega posicao do @ ( se nao tiver, -1)
        int dot = s.lastIndexOf("."); // posição do ultimo ponto , ou -1

        return at > 0 && dot > at +1 && dot < s.length()-1;
    }

    public static boolean inRange(double value, double min, double max){

       return value >= min && value <= max;
        }


}
