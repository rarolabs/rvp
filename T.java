import java.text.*;
import java.util.*;

public class T{
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println(sdf.format(new Date()));
		sdf = new SimpleDateFormat("EEE, dd 'de' MMMMM 'de' yyyy 'às' HH:mm");
		System.out.println(sdf.format(new Date()));	
	}
}