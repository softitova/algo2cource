package veb;

/**
 * Created by Sophia Titova on 22.03.17.
 */
public class VEBTree implements IntegerSet {

    private VEBNode root;

    public VEBTree(int k) {
        root = new VEBNode(k);
    }

    @Override
    public void add(long x) {
        addT(root, x);
    }

    @Override
    public void remove(long x) {
        removeT(root, x);
    }

    @Override
    public long next(long x) {
        return nextT(root, x);
    }

    @Override
    public long prev(long x) {
        return prevT(root, x);
    }

    @Override
    public long getMin() {
        return root.min;
    }

    @Override
    public long getMax() {
        return root.max;
    }

    public boolean find(long x) {
        return findR(root, x);
    }

    private long high(VEBNode node, long key) {
        return (key >> (node.k / 2));
    }

    private long low(VEBNode node, long key) {
        return (int) (key & ((1L << (node.k / 2)) - 1L));
    }

    private long merge(VEBNode node, long high, long low) {
        return (high << (node.k / 2)) + low;
    }

    private boolean findR(VEBNode root, long x) {
        return !empty(root)
                && ((root.min == x || root.max == x)
                || findR(root.clusters[(int) high(root, x)], (int) low(root, x)));
    }

    private boolean empty(VEBNode root) {
        return root.min == NO;
    }

    private void addT(VEBNode node, long x) {
        if (empty(node)) {
            node.min = x;
            node.max = x;
            return;
        }
        if (x < node.min) {
            long t = node.min;
            node.min = x;
            x = t;
        }
        if (x > node.max) {
            node.max = x;
        }
        if (node.k != 1) {
            if (empty(node.clusters[(int) high(node, x)])) {
                addT(node.summary, high(node, x));
            }
            addT(node.clusters[(int) high(node, x)], (int) low(node, x));
        }
    }

    private void removeT(VEBNode node, long x) {

        if (x == node.min) {

            if (node.k == 1 || empty(node.summary)) {
                node.max = node.min = NO;
                return;
            }

            int summaryMin = (int) node.summary.min;
            VEBNode clusterSumMin = node.clusters[summaryMin];

            node.min = merge(node, summaryMin, clusterSumMin.min);
            removeT(clusterSumMin, clusterSumMin.min);

            if (clusterSumMin.min == NO) {
                removeT(node.summary, summaryMin);
            }
            return;
        }

        int curH = (int) high(node, x);
        int curL = (int) low(node, x);

        removeT(node.clusters[curH], curL);

        if (node.clusters[curH].min == NO) {
            removeT(node.summary, curH);
        }
    }

    private long nextT(VEBNode node, long x) {

        if (empty(node) || node.max <= x) {
            return NO;
        }
        if (node.min > x) {
            return node.min;
        }
        if (empty(node.summary)) {
            return node.max;
        }

        int curH = (int) high(node, x);
        int curL = (int) low(node, x);
        VEBNode clusterCurH = node.clusters[curH];

        if (!empty(clusterCurH) && clusterCurH.max > curL) {
            return merge(node, curH, nextT(clusterCurH, curL));
        }

        long n = nextT(node.summary, curH);
        return n == NO ? node.max : merge(node, n, node.clusters[(int) n].min);
    }

    private long prevT(VEBNode node, long x) {

        if (empty(node) || node.min >= x) {
            return NO;
        }
        if (node.max < x) {
            return node.max;
        }

        int curH = (int) high(node, x);
        int curL = (int) low(node, x);
        VEBNode clusterCurH = node.clusters[curH];

        if (!empty(clusterCurH) && clusterCurH.min < curL) {
            return merge(node, high(node, x), prevT(clusterCurH, curL));
        }
        long n = prevT(node.summary, high(node, x));
        return n == NO ? node.min : merge(node, n, node.clusters[(int) n].max);
    }
}