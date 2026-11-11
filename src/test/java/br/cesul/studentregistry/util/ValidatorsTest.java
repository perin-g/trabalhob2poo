package br.cesul.studentregistry.util;

//como eu penso nessa classe na metodologia tdd
//  oque a vm vai precisar decidir? ela terá que validar entradas.
//      -nome nn pode ser vazio
//      -email tem q ter formato padrao
//      -nota tem q ser entre 0 e 10

//como testar funcoes c diversas possibilidades?(com diversas possibilidades...)
//      para cada função devemos listar varios exemplos de entradas e saidas esperadas.
//      ao inves de escrever 10 testes iguais mudando so os valores do arrange, utilizamos um teste parametrizado,
//      ou seja, um teste q roxa varias vezes
//      mas cada um com um conjunto de dados diferente


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

public class ValidatorsTest {

    //notBlank -> sera true se nn é null e tem algo alem de espaços

    //indica q esse metodod vai ser executado varias vezes
    //uma pra cada parametro q vc colocar nele
    @ParameterizedTest
    @DisplayName("notBlank deve detectar strings validas/invalidas")
    @CsvSource(
            value = {
                    //formato de cada linha: "entrada, resultadoEsperado"
                    //casos validos (espera = true)
                    "'abc', true" , //exemplo de texto normal
                    "' x', true" , //tem espaços mas tb conteudo

                    //casos invalidos: (esperado = false)
                    "' ', false", //so espaços
                    "'', false", //vazio
                    "null, false", //null real
            }, //transforma a palavra null do CSV em valor null de verdade
            nullValues = {"null"}
    )

    void notBlank_tests(String input, boolean expected) {
        boolean result = Validators.notBlank(input);
        assertEquals(expected, result, () -> "notBlank('" + input + "')" + "deveria ser" + expected);
    }

    //quais regras adotaremos pra validar email
    //- tem @
    //- existe ponto ( . ) depois do @
    //o ponto nao é o ultimo caractere
    @ParameterizedTest
    @DisplayName( "email simples deve validar formato basico")
    @CsvSource(
            value = {
                    //testes validos
                    "'ana@exemplo.com' , true",
                    "joao.silva@bol.org.br, true",
                    //testes invalidos
                    "carlos.com, false",
                    "vitor@feio, false",
                    "@gmail.com, false",
                    "ghisi@gmail.com., false",
                    "'  ', false",
                    "null, false"
            }, nullValues = {"null"}
    )
    void email_tests(String input, boolean expected) {
        boolean result = Validators.email(input);
        assertEquals(expected, result, () -> "email('" + input + "')" + "deveria ser" + expected);
    }

    @ParameterizedTest
    @DisplayName( "nota deve permitir max 10 e min 0")
    @CsvSource (
            value = {
                    //testes validos
                    "0, true",
                    "5.7 , true",
                    "10, true",
                    // testes invalidos
                    "10.1, false",
                    "-1, false",
            }
    )
    void inRange_tests(double value, boolean expected) {
        boolean result = Validators.inRange(value,  0, 10);
        assertEquals(expected, result, () -> "inRange(" + value + ") - deveria ser 0, 10" + expected);

    }

}
