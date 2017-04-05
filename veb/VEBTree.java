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

    private final long high(long key) {
        return (key >> (k >> 1));
    }

    private final long low(long key) {
        return (int) (key & ((1L << (k >> 1)) - 1L));
    }

    private final long merge(long high, long low) {
        return (high << (k >> 1)) + low;
    }

    private boolean findR(VEBNode root, long x) {
        return !empty(root) && ((root.min == x || root.max == x) || findR(root.clusters[(int)high(x)], (int)low(x)));
    }

    private boolean empty(VEBNode root) {
        return root.min == NO;
    }

    private void addT(VEBNode node, long x) {
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
                long t = node.min;
                node.min = x;
                x = t;
            }
            if (x > node.max) {
                long t = node.max;
                node.max = x;
                x = t;
            }
            if (k != 1) {
                if (empty(node.clusters[(int)high(x)])) {
                    addT(node.summary, high(x));
                    addT(node.clusters[(int)high(x)], low(x));
                }
            }
        }
    }

    private void removeT(VEBNode node, long x) {

        if (node.min == x && node.max == x) {
            node.min = NO;

            return;
        }

        if (node.min == x) {
            node.min = removeHelper(node, x, node.summary.min, node.clusters[(int)node.summary.min].min, node.max);
        } else {
            node.max = removeHelper(node, x, node.summary.max, node.clusters[(int)node.summary.max].max, node.min);
        }
        if (empty(node.summary)) return;
        removeT(node.clusters[(int)high(x)], low(x));
        if (empty(node.clusters[(int)high(x)])) removeT(node.summary, high(x));
    }

    private long removeHelper(VEBNode node, long x, long curHigh, long curLow, long val1) {
        if (empty(node.summary)) {
            return val1;
        }
        x = merge(curHigh, curLow);
        return x;
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
        if (!empty(node.clusters[(int)high(x)]) && node.clusters[(int)high(x)].max > low(x)) {
            return merge(high(x), nextT(node.clusters[(int)high(x)], low(x)));
        } else {
            long n = nextT(node.summary, high(x));
            return (n == -1) ? node.max : merge(n, node.clusters[(int)n].min);
        }
    }

    private long prevT(VEBNode node, long x) {
        if (empty(node) || node.min >= x) {
            return NO;
        }
        if (node.max < x) {
            return node.max;
        }
        if (empty(node.summary)) {
            return node.min;
        }
        if (!empty(node.clusters[(int)high(x)]) && node.clusters[(int)high(x)].min > low(x)) {
            return merge(high(x), prevT(node.clusters[(int)high(x)], low(x)));
        } else {
            long p = prevT(node.summary, high(x));
            return (p == -1) ? node.min : merge(p, node.clusters[(int)p].max);
        }
    }
}
