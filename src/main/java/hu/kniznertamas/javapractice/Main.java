package hu.kniznertamas.javapractice;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    private void runGame() {
        try(ConfigurableApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml")) {
            Army armyOne = applicationContext.getBean("armyOne", Army.class);
            Army armyTwo = applicationContext.getBean("armyTwo", Army.class);
            while(!armyOne.isDestroyed() || !armyTwo.isDestroyed()) {
                doStuff(armyOne);
                doStuff(armyTwo);
            }
        }
    }

    private void doStuff(Army army) {
        army.getUnits().forEach(Unit::doSomething);
    }

    public static void main(String[] args) {
        new Main().runGame();
    }
}
