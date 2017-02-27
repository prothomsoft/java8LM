package info.solidsoft.java8.defmethods;

public class RuleEngine implements Engine, Job, Lifecycle {

	public String start() {
		return Job.super.start();
	}
}
