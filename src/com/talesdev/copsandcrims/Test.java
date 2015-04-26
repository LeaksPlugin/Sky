package com.talesdev.copsandcrims;

import com.talesdev.core.config.Savable;
import org.apache.commons.lang3.RandomStringUtils;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Random;

public class Test {

    public static void main(String[] args) {
        Test test = new Test();
        for (int i = 0; i < 1000; i++) {
            System.out.println(test.sectionOf(new TestSavable(), "node"));
        }
    }

    public String sectionOf(Savable savable, String node) {
        return (savable.getName() != null ? (savable.getName().length() > 0 ? savable.getName() + "." : "") : "") + node;
    }

    static class TestSavable implements Savable {
        int random = new Random().nextInt(10);

        public TestSavable() {
            System.out.println("length : " + random);
        }

        @Override
        public void loadFrom(FileConfiguration config) {

    }

        @Override
        public void saveTo(FileConfiguration config) {

        }

        @Override
        public String getName() {
            return RandomStringUtils.randomAlphanumeric(random);
        }
    }
}