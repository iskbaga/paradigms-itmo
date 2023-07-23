package search;

public class BinarySearch {
    /**
     * Pred: args.length>0 && args[i] >= args[i + 1] for all i in [1, args.length-1)
     * let a[-1] = +inf && a[a.length] = -inf
     * Post: min i in  [0, args.length)
     */
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        int[] a = new int[args.length-1];
        for (int i = 0; i < a.length; i++) {
            a[i] = Integer.parseInt(args[i+1]);
        }
        //System.out.println(iterativeSearch(x, a));
        System.out.println(recursiveSearch(x,a));
    }

    /**
     * Pred: 0<=a.length<=1  || (a.length>1 && a[i] >= a[i + 1] for all i in [0, a.length-1))
     * let a[-1] = +inf && a[a.length] = -inf
     * Post: returned int >=0 && <=a.length
     * a[i] > x => returned int = i where i is min([0,a.length)) that a[i] > x, if no i in [0,a.length) returned int = a.length
     */
    private static int recursiveSearch(int x, int[] a){
        //  0<=a.length<=1  || (a.length>1 && a[i] >= a[i + 1] for all i in [0, a.length-1))
        //  a[-1] = +inf && a[a.length] = -inf
        //  a[left]>x>=a[right]
        //  -1 <= left < right <= a.length так как left = -1 и right = a.length
        return recursiveSearch(x,a,-1,a.length);
        // постусловия совпадают
    }

    /**
     * Pred: 0<=a.length<=1  || (a.length>1 && a[i] >= a[i + 1] for all i in [0, a.length-1))
     * -1 <= left < right <= a.length
     * let a[-1] = +inf && a[a.length] = -inf
     * a[left]>x>=a[right]
     * Post:  returned int > left && <=right
     * a[i] > x => returned int = i where i is min([left,right])
     */
    private static int recursiveSearch(int x, int[] a, int left, int right) {
        //0<=a.length<=1  || (a.length>1 && a[i] >= a[i + 1] for all i in [0, a.length-1))
        //-1 <= left < right <= a.length
        //a[left]>x>=a[right]
        int mid = (left + right)/2;
        if (left+1 >= right) {
            //left<right<=left+1 => right==left+1
            //a[left]>x>=a[left+1], так как в этом промежутке одно значение то left+1- минимвльное подходящее
            //a[right] > x => returned int = right where i is min([left,left+1])
            return right;
        }
        //left+1 <right
        if (a[mid] > x) {
            //a[left]>x>=a[right]
            //-1 <= left < right <= a.length
            //right+left<=2*right
            //mid<right
            //2*left<right+left
            //left<mid
            //-1 <= mid <= a.length(середина лежит внутри отрезка)
            left = mid;
            //left<left'
            //left<right
            //a[mid]>x>=a[right]
            //a[left]>x>=a[right]
            //-1 <= left < right <= a.length
            //left увеличился
        }
        //left+1 <right && a[mid] <= x
        else {
            //left+1 <right
            // a[mid] <= x
            //a[left]>x>=a[right]
            //-1 <= left < right <= a.length
            //left<right
            //right+left<=2*right
            //mid<right
            //-1 <= mid <= a.length(середина лежит внутри отрезка)
            right = mid;
            //right'<right
            //left<right
            //a[left]>x>=a[mid]
            //a[left]>x>=a[right]
            //-1 <= left < right <= a.length
            //right уменьшился
        }
        //right уменьшился или left увеличился(из этого следует завершаемость программы тк в определенный момент left+1 >= right)
        //left<right
        //a[left]>x>=a[right]
        //-1 <= left < right <= a.length
        //0<=a.length<=1  || (a.length>1 && a[i] >= a[i + 1] for all i in [0, a.length-1))
        return recursiveSearch(x, a, left, right);
    }
    /**
     * Pred: 0<=a.length<=1
     * || (a.length>1 && a[i] >= a[i + 1] for all i in [0, a.length-1))
     * let a[-1] = +inf && a[a.length] = -inf
     * Post:  returned int > -1 && <=a.length
     * a[i] > x => returned int = i where i is min([0,a.length)) that a[i] > x, if no i in [0,a.length) returned int = a.length
     */
    private static int iterativeSearch(int x, int[] a) {
        int left = -1;
        int right = a.length;
        //-1 <= left < right <= a.length
        //a[left]>x>=a[right]

        /* инварианты:
         -1 <= left < right <= a.length
         a[left]>x>=a[right]
         left возрастает либо right уменьшается
         */
        while (left < right - 1) {
            int mid = (left + right) / 2;
            if(a[mid] > x) {
                //left<right
                //left+right<2*right
                //mid<right
                //2*left<right+left
                //left<mid
                //-1<=mid<=a.length(середина лежит внутри отрезка)
                left = mid;
                //left'<left
                //left<right
                //-1<=left<right <=a.length

                //a[mid]>x
                //a[left]>x
                //a[right]<=x так как значение right не менялось, соблюдается в ветвлении где меняется right и в начале цикла
                //a[left]>=a[right] так как left<right
                //a[left]>x>=a[right]
                //left'<left
            } else {
                //left<right
                //left+right<2*right
                //mid<right
                //-1<=mid<=a.length(середина лежит внутри отрезка)
                right = mid;
                //right'<right
                //right = mid
                //2*left<left+right
                //-1 <= left < right <= a.length

                //a[mid]<=x
                //a[right]<=x
                //a[left]>x так как значение left не менялось, соблюдается в ветвлении где меняется left и в начале цикла
                //a[left]>=a[right] так как left<right
                //a[left]>x>=a[right]
                //right'<right

            }
            //-1 <= left < right <= a.length
            //a[left]>x>=a[right]
            //left возрос либо right уменьшился
        }
        //-1 <= left < right <= a.length
        //a[left]>x>=a[right]
        return right;
    }
}