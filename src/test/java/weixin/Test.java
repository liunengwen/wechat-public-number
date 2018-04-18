package weixin;

public class Test {

	public static void main(String[] args) {
		String str ="[aa=111/bb=222]";
		String cc = str.substring(str.indexOf("[")+1, str.lastIndexOf("]") );
		System.out.println(cc);

	}

}
