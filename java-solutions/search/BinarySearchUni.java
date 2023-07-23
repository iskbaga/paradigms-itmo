package search;

public class BinarySearchUni {
    /**
     * Pred: exists k that args.length>0 && args[i] >= args[i + 1] for all i in [0, k-1) && args[i] <= args[i + 1] for all i in [k, args.length-1)
     * Post: min n in  [0, args.length) that a[i] <= a[i + 1] for all i in [n,a.length-1)
     */
    public static void main(String[] args) {
        int[] a = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            a[i] = Integer.parseInt(args[i]);
        }
        System.out.println(search(a));
    }

    /**
     * Pred: exists k that args.length>0 && args[i] >= args[i + 1] for all i in [0, k-1) && args[i] <= args[i + 1] for all i in [k, args.length-1)
     * Post: min n in  [0, args.length) that a[i] <= a[i + 1] for all i in [n,a.length-1)
     */
    private static int search(int[] a) {
        //exists k that a.length>0 && a[i] >= a[i + 1] for all i in [0, k-1) && a[i] <= a[i + 1] for all i in [k, a.length-1)
        int sum = 0;
        //i<a.length
        for (int i = 0; i < a.length; i++) {
            sum += a.length;
        }
        sum = sum % 2;
        //sum == 0 || sum == 1 так как это остаток от деления на 2
        if (sum == 1) {
            //sum == 0
            return iterativeSearchUni(a);
        }
        //так как sum == 0 || sum == 1 и sum !=1 то sum == 0
        //0<=0<=a.length-1<a.length
        return recursiveSearchUni(a, 0, a.length - 1);

    }

    /**
     * Pred: exists k that a.length>0 && a[i] >= a[i + 1] for all i in [0, k-1) && a[i] <= a[i + 1] for all i in [k, a.length-1)
     * Post: min n in  [0, args.length) that a[i] <= a[i + 1] for all i in [n,a.length-1)
     */
    private static int iterativeSearchUni(int[] a) {
        int left = 0;
        int right = a.length - 1;
        /* инварианты:
         0 <= left < right <= a.length-1
         a[left] >= a[left -1]  &&  a[right-1] < a[right]
         */
        while (left < right) {
            int mid = (right + left) / 2;
            if (a[mid] > a[mid + 1]) {
                //right+left<2*right
                //mid<right
                //0<=mid<=a.length(середина лежит внутри отрезка)
                right = mid;
                //a[right'] > a[right' + 1]
                //right'<right
            } else {
                //a[mid]<=a[mid+1]
                //left+right>2*left
                //mid>left
                //mid+1>left
                left = mid + 1;
                //a[left-1] <= a[left]
                //left<left'
            }
        }
        /*
         * left >= right && 0 <= l < r < a.length
         * right == left in [0, len(a))
         * a[left] >= a[left -1]  ==  a[right-1] < a[right] => right - нужное значение
         */
        return right;
    }

    /**
     * Pred: exists k that a.length>0 && a[i] >= a[i + 1] for all i in [0, k-1) && a[i] <= a[i + 1] for all i in [k, a.length-1)
     * 0<=l<=r<a.length
     * Post: min n in  [0, a.length) that a[i] <= a[i + 1] for all i in [n,a.length-1)
     */
    private static int recursiveSearchUni(int[] a, int l, int r) {
        //exists k that args.length>0 && args[i] >= args[i + 1] for all i in [0, k-1) && args[i] <= args[i + 1] for all i in [k, args.length-1)
        int left = l;
        int right = r;
        if (left < right) {
            int mid = (right + left) / 2;
            if (a[mid] > a[mid + 1]) {
                //right+left<2*right
                //mid<right
                //left<=mid<=right(середина лежит внутри отрезка)
                right = mid;
                //a[right'] > a[right' + 1
                //right<a.length
                //right'<right
            } else {
                //a[mid]<=a[mid+1]
                //left+right>2*left
                //mid>left
                //mid+1>left
                left = mid + 1;
                //a[left-1] <= a[left]
                //0<=left
                //left<left'
            }
            //exists k that args.length>0 && args[i] >= args[i + 1] for all i in [0, k-1) && args[i] <= args[i + 1] for all i in [k, args.length-1)
            //0<=left<=right<a.length
            return recursiveSearchUni(a, left, right);
        }
        /*
         * left >= right && 0 <= l < r < a.length
         * right == left in [0, len(a))
         * a[left] >= a[left -1]  ==  a[right-1] < a[right] => right - нужное значение
         */
        return right;
    }
}