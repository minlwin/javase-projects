import java.nio.file.*;

public class DeleteClass {

	public static void main(String[] args) {
		
		try {

			Files.walk(Path.of("com"))
				.filter(a -> Files.isRegularFile(a))
				.filter(a -> a.toString().endsWith("class"))
				.forEach(a -> {
					try {
						System.out.println(a.toString());
						Files.delete(a);
					} catch(Exception e) {
						e.printStackTrace();
					}
				});

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}