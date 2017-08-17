package com.future.awaker.data.realm;

import com.future.awaker.data.Level;

import io.realm.RealmObject;

/**
 * Copyright ©2017 by Teambition
 */

public class LevelRealm extends RealmObject {

    public String current;
    public String next;
    public int upgrade_require;
    public int left;
    public String percent;

    public static LevelRealm getLevelRealm(Level level) {
        LevelRealm levelRealm = new LevelRealm();
        if (level != null) {
            levelRealm.current = level.current;
            levelRealm.next = level.next;
            levelRealm.upgrade_require = level.upgrade_require;
            levelRealm.left = level.left;
            levelRealm.percent = level.percent;
        }
        return levelRealm;
    }

    public static Level getLevel(LevelRealm levelRealm) {
        Level level = new Level();
        if (levelRealm != null) {
            level.current = levelRealm.current;
            level.next = levelRealm.next;
            level.upgrade_require = levelRealm.upgrade_require;
            level.left = levelRealm.left;
            level.percent = levelRealm.percent;
        }
        return level;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public int getUpgrade_require() {
        return upgrade_require;
    }

    public void setUpgrade_require(int upgrade_require) {
        this.upgrade_require = upgrade_require;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }
}
