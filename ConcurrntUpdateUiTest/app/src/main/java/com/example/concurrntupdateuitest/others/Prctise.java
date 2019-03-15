package com.example.concurrntupdateuitest.others;

public class Prctise {
    public static void main(String[] args) {
        ListNode one = new ListNode(2);
        ListNode two = new ListNode(4);
        ListNode three = new ListNode(3);
        one.next = two;
        two.next = three;

        ListNode one1 = new ListNode(5);
        ListNode two1 = new ListNode(6);
        ListNode three1 = new ListNode(4);
        one1.next = two1;
        two1.next = three1;

        ListNode res = new Prctise().addTwoNumbers(one1, one);
        while(res != null){
            System.out.println(res.val);
            res = res.next;
        }

    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode res = new ListNode(0);
        ListNode tmp = res;
        ListNode l1Poing = l1;
        ListNode l2Point = l2;
        int c = 0;
        int a = l1Poing == null ? 0 : l1Poing.val;
        int b = l2Point == null ? 0 : l2Point.val;
        while(c + a + b > 0 || !(l1Poing == null && l2Point == null)){
            ListNode caculation = new ListNode((c + a + b)%10);
            tmp.next = caculation;

            tmp = tmp.next;
            if (l1Poing != null){
                l1Poing = l1Poing.next;
            }

            if (l2Point != null){
                l2Point = l2Point.next;
            }

            c = (c + a + b)/10;
            a = l1Poing == null ? 0 : l1Poing.val;
            b = l2Point == null ? 0 : l2Point.val;
        }
        if (res.next == null){
            return new ListNode(0);
        }
        return res.next;
    }
}
