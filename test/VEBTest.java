package test;

import org.junit.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import veb.VEBTree;

import java.util.Random;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)


public class VEBTest {

    public static void main(String[] args) throws Exception {
        JUnitCore runner = new JUnitCore();
        Result result = runner.run(VEBTest.class);
        System.out.println("run tests: " + result.getRunCount());
        System.out.println("failed tests: " + result.getFailureCount());
        System.out.println("ignored tests: " + result.getIgnoreCount());
        System.out.println("success: " + result.wasSuccessful());
    }

    @Test
    public void add() {
        for (int i = 2; i < 32; i+=2) {
            VEBTree v = new VEBTree(i);
            for (int j = 0; j < (Math.pow(2, i)); j++) {
                v.add(j);
                assertTrue(v.find(j));
            }
        }
    }

    @Test
    public void remove() {
        for (int i = 2; i < 32; i += 2) {
            VEBTree v = new VEBTree(i);
            for (int j = 0; j < (Math.pow(2, i)); j++) {
                v.add(j);
                assertTrue(v.find(j));
                v.remove(j);
                assertFalse(v.find(j));
            }
        }
    }

}