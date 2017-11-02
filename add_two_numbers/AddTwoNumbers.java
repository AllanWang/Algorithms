package add_two_numbers;

/**
 * Created by Allan Wang on 2017-11-02.
 * <p>
 * The following passed the text with a runtime of 76ms
 */
public class AddTwoNumbers {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int carry = 0;
        ListNode result = null;
        ListNode curr = null;
        int sum = 0;
        ListNode next;
        //go through the first value just so we don't have to null check in the loop
        if (l1 != null) {
            sum += l1.val;
            l1 = l1.next;
        } else return l2;
        if (l2 != null) {
            sum += l2.val;
            l2 = l2.next;
        } else return l1;
        carry = sum / 10;
        sum %= 10;
        curr = new ListNode(sum);
        result = curr;
        //begin recursive call while both items are nonnull
        while (l1 != null && l2 != null) {
            sum = l1.val + l2.val + carry;
            carry = sum / 10;
            sum %= 10;
            next = new ListNode(sum);
            curr.next = next;
            curr = next;
            l1 = l1.next;
            l2 = l2.next;
        }
        ListNode rem = l1 != null ? l1 : l2;
        while (rem != null) {
            if (carry == 0) {
                //we no longer have carries, so we may simply reference the remaining nodes
                curr.next = rem;
                break;
            }
            sum = rem.val + carry;
            carry = sum / 10;
            sum %= 10;
            next = new ListNode(sum);
            curr.next = next;
            curr = next;
            rem = rem.next;
        }
        if (carry > 0)
            curr.next = new ListNode(carry);
        return result;
    }

}
