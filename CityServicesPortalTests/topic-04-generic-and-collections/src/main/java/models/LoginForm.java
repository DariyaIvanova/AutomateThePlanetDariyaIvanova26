package models;

public record LoginForm(String email, String password) {

    //record автоматично създава конструктор и методи email() и password() за достъп до стойностите.
}