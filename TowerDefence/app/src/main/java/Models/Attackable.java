// Copyright 2023 Olimpeiv Y. Y.
package Models;

public interface Attackable extends Movable {
    void causeDamage(double damage);
    double getHealth();
    boolean isAlive();
}
