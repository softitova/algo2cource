package veb;

import veb.IntegerSet;

import java.util.HashMap;

/**
 * Created by Sophia Titova on 22.03.17.
 */
public class VEBTree implements IntegerSet {

    private VEBNode root;

    public VEBTree(int k) {
        root = new VEBNode(k);
    }


    public void add(long x) {
        addT(root, x);
    }

    public void remove(long x) {
        removeT(root, x);
    }

    public long next(long x) {
        return nextT(root, x);
    }

    public long prev(long x) {
        return prevT(root, x);
    }

    public long getMin() {
        return root.min;
    }

    public long getMax() {
        return root.max;
    }

    private long high(VEBNode node, long key) {
        return (key >> (node.k / 2));
    }

    private long low(VEBNode node, long key) {
        return (key & ((1L << (node.k / 2)) - 1L));
    }

    private long merge(VEBNode node, long high, long low) {
        return ((high << (node.k / 2))) | low;
    }

    private boolean empty(VEBNode root) {
        return root != null && root.min == NO;
    }


    private void addT(VEBNode node, long x) {
        if (empty(node)) {
            node.min = x;
            node.max = x;
            return;
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
        if (node.k != 1) {
            node.clusters.putIfAbsent(high(node, x), new VEBNode(node.k / 2));
            if (empty(node.clusters.get(high(node, x)))) {
                addT(node.summary, high(node, x));
            }
            addT(node.clusters.get(high(node, x)), low(node, x));
        }
    }

    private void removeT(VEBNode node, long x) {
        if (node.min == node.max) {
            node.min = node.max = NO;
            return;
        }
        if (x == node.max) {
            if (node.k == 1) {
                node.max = node.min;
                return;
            }
            node.max = prevT(node, node.max);
        }
        if (x == node.min) {
            if (node.k == 1) {
                node.min = node.max;
                return;
            }
            if (empty(node.summary)) { // память / сравнение на нулл
                node.max = node.min = NO;
                return;
            }

            VEBNode clusterSumMin = node.clusters.get(node.summary.min);
            node.min = merge(node, node.summary.min, clusterSumMin.min);

            removeT(clusterSumMin, clusterSumMin.min);

            if (clusterSumMin.min == NO) {
                removeT(node.summary, node.summary.min);
            }
            return;
        }

        long curH = high(node, x);
        long curL = low(node, x);

        if (!node.clusters.containsKey(curH)) {
            return;
        }
        removeT(node.clusters.get(curH), curL);

        if (node.clusters.get(curH).min == NO) {
            removeT(node.summary, curH);
        }
        if (x == node.max) {

            curH = high(node, x);
            VEBNode curN = node.clusters.get(curH);

            if (curN == null) {
                if (empty(node.summary)) {
                    node.min = node.max = NO;
                    return;
                }
                curH = node.summary.max;
            }
            node.max = merge(node, curH, node.clusters.get(curH).max);
        }
    }

    private long nextT(VEBNode node, long x) {

        if (node.k == 1) {
            if (node.min == NO || x >= node.max) {
                return NO;
            }
            return x < node.min ? node.min : node.max;
        }
        if (empty(node) || node.max <= x) {
            return NO;
        }
        if (node.min > x) {
            return node.min;
        }

        long curH = high(node, x); // empty(node.summary);
        long curL = low(node, x);
        VEBNode clusterCurH = node.clusters.get(curH);

        if (clusterCurH != null && !empty(clusterCurH) && clusterCurH.max > curL) {
            return merge(node, curH, nextT(clusterCurH, curL));
        }

        long n = nextT(node.summary, curH);
        return n == NO ? node.max : merge(node, n, node.clusters.get(n).min);
    }

    private long prevT(VEBNode node, long x) {

        if (node.k == 1) {
            if (node.min == NO || x <= node.min) {
                return NO;
            }
            return x > node.max ? node.max : node.min;
        }

        if (empty(node) || node.min >= x) {
            return NO;
        }
        if (node.max < x) {
            return node.max;
        }

        long curH = high(node, x);
        long curL = low(node, x);
        VEBNode clusterCurH = node.clusters.get(curH);

        if (clusterCurH != null && !empty(clusterCurH) && clusterCurH.min < curL) {
            return merge(node, high(node, x), prevT(clusterCurH, curL));
        }
        long n = prevT(node.summary, high(node, x));
        return n == NO ? node.min : merge(node, n, node.clusters.get(n).max);
    }


    private class VEBNode {
        int k;
        VEBNode summary;
        HashMap<Long, VEBNode> clusters;
        long max = NO;
        long min = NO;

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
                clusters = new HashMap<>();
            }
        }
    }
}
