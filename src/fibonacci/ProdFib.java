package fibonacci;

public class ProdFib {
	public static void main(String[] args) {
		long[] res = productFib(1256578951521111112L);
		System.out.println(res[0] + ", " + res[1] + ", " + res[2] ) ;
		
	}
	
	public static long[] productFib(long prod) {
		long tempProd = 0L;
		int first = 0;
		int second = 1;
		
		while (prod > tempProd) {
			tempProd = (long) first * second;
			int tempSecond = second;
			second = first + second;
			first = tempSecond;
			System.out.println(tempProd);
		}
		if(tempProd== prod) {
			return new long[] {second-first, first, 1};
		}else {
			return new long[] {second-first, first, 0};
		}
	
	}
}
