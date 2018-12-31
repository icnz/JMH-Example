package jmh.benchmarks;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread) //Thread: 该状态为每个线程独享。
public class JavaBasicBenchmark {
	@Benchmark
	public void doubleCompare() {
		boolean b = Double.valueOf("0.00").compareTo(0.0) == 0;
	}

	@Benchmark
	public void BigdecimalCompare() {
		boolean b = BigDecimal.valueOf(0.00).compareTo(BigDecimal.ZERO) == 0;
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(JavaBasicBenchmark.class.getSimpleName()) //benchmark 所在的类的名字，注意这里是使用正则表达式对所有类进行匹配的
				.forks(2) //进行 fork 的次数。如果 fork 数是2的话，则 JMH 会 fork 出两个进程来进行测试
				.warmupIterations(2) //预热的迭代次数
				.warmupTime(TimeValue.valueOf("3"))
				.measurementIterations(5) //实际测量的迭代次数
				.measurementTime(TimeValue.valueOf("3"))
				.build();

		new Runner(opt).run();
	}
}
