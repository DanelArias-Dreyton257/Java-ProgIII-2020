
public class Main {
	
	public static void main(String[] args) {
		combi2(26,2);
	}
	
	public static void combi(int lon) {
		combi(lon,"");
	}
	
	
	private static void combi(int lon, String acum) {
		if(lon<=0) {
			System.out.println(acum);
		}
		else {
			combi(lon-1,acum+"A");
			combi(lon-1,acum+"B");
			combi(lon-1,acum+"C");
		}
	}
	
	public static void combi2(int num, int lon) {
		combi2(num,lon,"");
	}
	
	private static void combi2(int num, int lon, String acum) {
		if(lon<=0) {
			System.out.println(acum);
		}
		else {	
			for(char i = 65; i<num+65;i++) {
				combi2(num,lon-1,acum+i);
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
