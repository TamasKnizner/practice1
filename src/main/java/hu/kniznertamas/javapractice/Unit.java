package hu.kniznertamas.javapractice;

public abstract class Unit {

    public static final int MAX_HP = 1000;

    protected int hitPoints;

    abstract void doSomething();

    public boolean isDead() {
        return 0 >= hitPoints;
    }
}
