package server;

public class ServerConsole {
	private static boolean textOn;
	private static int tabIndex;
	
	public static void init(){
		textOn = true;
		tabIndex = 0;
	}
	
	public static void startTask(String taskPrompt){
		if (textOn) {
			for (int i = 0; i < tabIndex; i++)
				System.out.print("\t)");
			System.out.print(taskPrompt + "...");
		}
	}
	
	public static void endTask(){
		if(textOn)
			System.out.println("DONE!");
	}
	public static void startGroup(String task){
		if(textOn){
			for (int i = 0; i < tabIndex; i++)
				System.out.print("\t)");
			System.out.println(task + "...");
			tabIndex++;
		}
	}
	
	public static void endGroup(){
		if(textOn){
			tabIndex--;
			for (int i = 0; i < tabIndex; i++)
				System.out.print("\t)");
			System.out.println("DONE!");
		}
	}
	
	public static void message(String message){
		if(textOn)
			System.out.println("****"+message+"****");
	}
	
	public static void groupError(String message){
		if(textOn){
			tabIndex--;
			for (int i = 0; i < tabIndex; i++)
				System.out.print("\t)");
			System.out.println("DONE!");
		}
	}
}
