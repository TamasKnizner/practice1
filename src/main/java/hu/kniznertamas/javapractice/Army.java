package hu.kniznertamas.javapractice;

import java.util.List;

public class Army {

    private List<Unit> units;

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }

    public boolean isDestroyed() {
        return units.stream().allMatch(Unit::isDead);
    }
}
