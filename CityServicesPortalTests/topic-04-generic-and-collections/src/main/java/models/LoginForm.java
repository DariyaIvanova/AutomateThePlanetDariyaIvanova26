package models;

public record LoginForm(String email, String password) {

    //record съхранява данните за фпрмуляра
    // автоматично създава конструктор и методи email() и password() за достъп до стойностите.
}