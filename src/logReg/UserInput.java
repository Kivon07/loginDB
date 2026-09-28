package logReg;

public class UserInput {
	private static String fName;
	private static String lName;
	private static String mInit;
	private int age;
	
	public UserInput(){
		fName = "";
		lName = "";
		mInit = "";
		age = 0;
		
	}
	public UserInput(String fName, String lName, String mInit, int age){
		this.fName = fName;
		this.lName = lName;
		this.mInit = mInit;
		this.age = age;
		
	}
	
	public void setfName(String fName) {
		this.fName = fName;
	}
	
	public void setlName(String lName) {
		this.lName = lName;
	}
	public void setmInit(String mInit) {
		this.mInit = mInit;
	}
	public void setAge(int age) {
		this.age= age;
	}
	
	public String getfName() {
		return fName;
	}
	
	public String getlName() {
		return lName;
	}
	
	public String getmInit() {
		return mInit;
	}
	
	public int getAge() {
		return age;
	}
}
