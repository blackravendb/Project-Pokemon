package de.pokemon;

public class TestClass {
	int tileX = 5;
	
	
	
	public  void test(int tileX){
		System.out.println("Vorher: this.tileX " + this.tileX);
		System.out.println("thileX: " + tileX);
		this.tileX=(tileX < 0) ? this.tileX-1 : this.tileX+1;
		System.out.println("Nachher: this.tileX " + this.tileX);
		System.out.println("thileX: " + tileX);
	}
	
	public static void main(String[] args){
		TestClass ttest = new TestClass();
		ttest.test(-1);
	}
	
}
