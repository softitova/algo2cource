package veb;

/**
 * Created by Sophia Titova on 22.03.17.
 */
public class VEBTree implements IntegerSet {

    private VEBNode root;
    private int k;

    public VEBTree(int k) {
        this.k = k;
        root = new VEBNode((int) Math.pow(2, k));
    }

    @Override
    public void add(int x) {
        addT(root, x);
    }

    @Override
    public void remove(int x) {
        removeT(root, x);
    }

    @Override
    public int next(int x) {
        return nextT(root, x);
    }

    @Override
    public int prev(int x) {
        return prevT(root, x);
    }

    @Override
    public int getMin() {
        return root.min;
    }

    @Override
    public int getMax() {
        return root.max;
    }

    public boolean find(int x) {
        return findR(root, x);
    }

    private final int high(int key) {
        return (key >> (k >> 1));
    }

    private final int low(int key) {
        return (int) (key & ((1L << (k >> 1)) - 1L));
    }

    private final int merge(int high, int low) {
        return (high << (k >> 1)) + low;
    }

    private boolean findR(VEBNode root, int x) {
        return !empty(root) && ((root.min == x || root.max == x) || findR(root.clusters[high(x)], low(x)));
    }

    private boolean empty(VEBNode root) {
        return root.min == NO;
    }

    private void addT(VEBNode node, int x) {
        if (empty(node)) {
            node.min = node.max = x;
        } else if (node.min == node.max) {
            if (node.min < x) {
                node.max = x;
            } else {
                node.min = x;
            }

        } else {
            if (x < node.min) {
                int t = node.min;
                node.min = x;
                x = t;
            }
            if (x > node.max) {
                int t = node.max;
                node.max = x;
                x = t;
            }
            if (k != 1) {
                if (empty(node.clusters[high(x)])) {
                    addT(node.summary, high(x));
                    addT(node.clusters[high(x)], low(x));
                }
            }
        }
    }

    private void removeT(VEBNode node, int x) {

        if (node.min == x && node.max == x) {
            node.min = NO;

            return;
        }

        if (node.min == x) {
            node.min = removeHelper(node, x, node.summary.min, node.clusters[node.summary.min].min, node.max);
        } else {
            node.max = removeHelper(node, x, node.summary.max, node.clusters[node.summary.max].max, node.min);
        }
        if (empty(node.summary)) return;
        removeT(node.clusters[high(x)], low(x));
        if (empty(node.clusters[high(x)])) removeT(node.summary, high(x));
    }

    private int removeHelper(VEBNode node, int x, int curHigh, int curLow, int val1) {
        if (empty(node.summary)) {
            return val1;
        }
        x = merge(curHigh, curLow);
        return x;
    }

    private int nextT(VEBNode node, int x) {
        if (empty(node) || node.max <= x) {
            return NO;
        }
        if (node.min > x) {
            return node.min;
        }
        if (empty(node.summary)) {
            return node.max;
        }
        if (!empty(node.clusters[high(x)]) && node.clusters[high(x)].max > low(x)) {
            return merge(high(x), nextT(node.clusters[high(x)], low(x)));
        } else {
            int n = nextT(node.summary, high(x));
            return (n == -1) ? node.max : merge(n, node.clusters[n].min);
        }
    }

    private int prevT(VEBNode node, int x) {
        if (empty(node) || node.min >= x) {
            return NO;
        }
        if (node.max < x) {
            return node.max;
        }
        if (empty(node.summary)) {
            return node.min;
        }
        if (!empty(node.clusters[high(x)]) && node.clusters[high(x)].min > low(x)) {
            return merge(high(x), prevT(node.clusters[high(x)], low(x)));
        } else {
            int p = prevT(node.summary, high(x));
            return (p == -1) ? node.min : merge(p, node.clusters[p].max);
        }
    }
}
