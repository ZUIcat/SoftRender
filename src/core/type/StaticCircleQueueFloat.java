package core.type;

import log.ZLogger;

public class StaticCircleQueueFloat {
    private int length;
    private Node nowNode;

    public StaticCircleQueueFloat(int length) {
        if (length <= 0) {
            ZLogger.ins().error("The length must larger than 0!");
            throw new IllegalArgumentException("The length must larger than 0!"); // TODO 简化
        }
        this.length = length;
        Node tempHeadNode = new Node();
        nowNode = tempHeadNode;
        for (int i = 0; i < length - 1; i++) {
            nowNode.next = new Node();
            nowNode = nowNode.next;
        }
        nowNode.next = tempHeadNode;
    }

    public void add(float nodeValue) {
        nowNode.value = nodeValue;
        nowNode = nowNode.next;
    }

    public float getAverage() {
        float sum = 0;
        for (int i = 0; i < length; i++) {
            sum += nowNode.value;
            nowNode = nowNode.next;
        }
        return sum / length;
    }

    public int getLength() {
        return length;
    }

    private static class Node {
        float value;
        Node next;

        Node() {
            value = 0.0f;
        }
    }
}
