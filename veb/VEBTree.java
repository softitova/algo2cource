package veb;

/**
 * Created by Sophia Titova on 22.03.17.
 */
public class VEBTree implements IntegerSet {

    private VEBNode root;
    private int k;


    public VEBTree(int k) {
        this.k = k;
        int z = (int) Math.pow(2, k);
        root = new VEBNode(z);
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

    private long high(long key) {
        return (key >> (k));
    }

    private long low(long key) {
        return (int) (key & ((1L << (k)) - 1L));
    }

    private long merge(long high, long low) {
        return (high << (k)) + low;
    }

    private boolean findR(VEBNode root, long x) {
        return !empty(root) && ((root.min == x || root.max == x) || findR(root.clusters[(int) high(x)], (int) low(x)));
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
        if (node.min == node.max) {
            if (node.min < x) {
                node.max = x;
            } else {
                node.min = x;
            }
            x = node.max; //
        }
        if (x == node.min) {
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
        if (k != 1) {
            if (empty(node.clusters[(int) high(x)])) {
                addT(node.summary, high(x));
            }
            addT(node.clusters[(int) high(x)], low(x));
        }
    }


    private void removeT(VEBNode node, long x) {
        if (x == node.min) {
            if (empty(node.summary)) {
                node.max = node.min = NO;
                return;
            }
            node.min = merge(node.summary.min, node.clusters[(int) node.summary.min].min);

            removeT(node.clusters[(int) node.summary.min], node.clusters[(int) node.summary.min].min);
            if (node.clusters[(int) node.summary.min].min == NO) {
                removeT(node.summary, node.summary.min);
            }
            return;
        }
        removeT(node.clusters[(int) high(x)], low(x));
        if (node.clusters[(int) high(x)].min == NO) {
            removeT(node.summary, high(x));
        }
        node.max = node.summary.min == NO ? node.min
                : merge(node.summary.max, node.clusters[(int) node.summary.max].max);
//        if (node.min == x && node.max == x) {
//            node.min = NO;
//            return;
//        }
//
//        if (node.min == x) {
//            if (empty(node.summary)) {
//                node.min = node.max;
//                return;
//            }
//            x = merge(node.summary.min, node.clusters[(int) node.summary.min].min);
//            node.min = x;
//        }
//        if (node.max == x) {
//            if (empty(node.summary)) {
//                node.max = node.min;
//                return;
//            }
//
//        } else {
//            x = merge(node.summary.max, node.clusters[(int) node.summary.max].max);
//            node.max = x;
//        }
//
//        if (empty(node.summary)) {
//            return;
//        }
//        removeT(node.clusters[(int) high(x)], low(x));
//        if (empty(node.clusters[(int) high(x)])) removeT(node.summary, high(x));
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
        if (!empty(node.clusters[(int) high(x)]) && node.clusters[(int) high(x)].max > low(x)) {
            return merge(high(x), nextT(node.clusters[(int) high(x)], low(x)));
        }
        long n = nextT(node.summary, high(x));
        return n == NO ? node.max : merge(n, node.clusters[(int) n].min);
    }

    private long prevT(VEBNode node, long x) {
        if (empty(node) || node.min >= x) {
            return NO;
        }
        if (node.max < x) {
            return node.max;
        }
        if (!empty(node.clusters[(int) high(x)]) && node.clusters[(int) high(x)].min < low(x)) {
            return merge(high(x), prevT(node.clusters[(int) high(x)], low(x)));
        }
        long n = prevT(node.summary, high(x));
        return n == NO ? node.min : merge(n, node.clusters[(int) n].max);
    }
}
