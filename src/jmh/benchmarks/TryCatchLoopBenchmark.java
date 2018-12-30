package jmh.benchmarks;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread) //Thread: 该状态为每个线程独享。
public class TryCatchLoopBenchmark {
	@Benchmark
	public void tryCatchInLoop() {
		for (int i=0;i<100;i++) {
			try {
				"jmh string utils replace".replace("utils", "");
			} catch (Throwable t) {
				// ignore
			}
		}
	}

	@Benchmark
	public void tryCatchOutLoop() {
		int sum = 0;
		try {
			for (int i=0;i<100;i++) {
				"jmh string utils replace".replace("utils", "");
			}
		} catch (Throwable t) {
			// ignore
		}
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(TryCatchLoopBenchmark.class.getSimpleName()) //benchmark 所在的类的名字，注意这里是使用正则表达式对所有类进行匹配的
				.forks(1) //进行 fork 的次数。如果 fork 数是2的话，则 JMH 会 fork 出两个进程来进行测试
				.warmupIterations(2) //预热的迭代次数
				.warmupTime(TimeValue.valueOf("3"))
				.measurementIterations(5) //实际测量的迭代次数
				.measurementTime(TimeValue.valueOf("3"))
				.build();

		new Runner(opt).run();
	}
}
