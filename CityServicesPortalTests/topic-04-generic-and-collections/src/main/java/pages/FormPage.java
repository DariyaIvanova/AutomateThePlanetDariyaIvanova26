package pages;

public abstract class FormPage<TForm> {

    public abstract void fillForm(TForm data);
    /*
    abstract class - клас, от който ще наследяваме, без да създаваме директно обекти.
     <TForm> - generic тип, който ще бъде различен за всяка форма.
     fillForm(TForm data) - метод, който наследяващите класове трябва да имплементират.
     */
}