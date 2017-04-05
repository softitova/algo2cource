package veb;

class VEBNode {

    int k;
    VEBNode summary;
    VEBNode[] clusters;
    long max = IntegerSet.NO;
    long min = IntegerSet.NO;

    VEBNode(int k) {
        this.k = k;
        initializeChildren(k);
    }

    private void initializeChildren(int k) {
        if (k == 1) {
            summary = null;
            clusters = null;
        } else {

            int childSize = k / 2;
            summary = new VEBNode(childSize);
            clusters = new VEBNode[1 << childSize];

            for (int i = 0; i < clusters.length; i++) {
                clusters[i] = new VEBNode(childSize);
            }
        }
    }
}