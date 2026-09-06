package main;

import javax.swing.SwingUtilities;

import entity.MonsterVI;
import entity.NormalBullet;

/** Measures warmed entity construction, not gameplay FPS. */
public class AssetBenchmark {
	private static volatile Object result;

	public static void main(String[] args) throws Exception {
		SwingUtilities.invokeAndWait(() -> {
			GamePanel panel = new GamePanel();
			for(int i = 0; i < 30; i++) {
				result = new MonsterVI(panel);
				result = new NormalBullet(panel);
			}
			long start = System.nanoTime();
			for(int i = 0; i < 300; i++) result = new MonsterVI(panel);
			double monsterMillis = (System.nanoTime() - start) / 1e6;
			start = System.nanoTime();
			for(int i = 0; i < 3000; i++) result = new NormalBullet(panel);
			double bulletMillis = (System.nanoTime() - start) / 1e6;
			System.out.printf("300 MonsterVI spawns: %.3f ms%n3000 bullets: %.3f ms%n", monsterMillis, bulletMillis);
		});
	}
}
