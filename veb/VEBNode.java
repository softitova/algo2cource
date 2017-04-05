package veb;

import java.util.Arrays;

class VEBNode {

    private int universeSize;
    VEBNode summary;
    VEBNode[] clusters;
    long max = IntegerSet.NO;
    long min = IntegerSet.NO;

    VEBNode(int universeSize) {
        this.universeSize = universeSize;
        initializeChildren(universeSize);
    }

    private void initializeChildren(int universeSize) {
        if (universeSize < 3) {
            summary = null;
            clusters = null;
        } else {
            int childSize = higherSquareRoot();
            summary = new VEBNode(childSize);
            clusters = new VEBNode[childSize];
            Arrays.fill(clusters,new VEBNode(childSize));
        }
    }

    private int higherSquareRoot() {
        return (int) Math.pow(2, Math.ceil((Math.log10(universeSize) / Math.log10(2)) / 2));
    }
}