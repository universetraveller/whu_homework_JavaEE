package cn.edu.whu.ProductManager.aspect;
public class TimeCounter{
	Long maxTime;
	Long minTime;
	Long totalTime;
	int round;
	public TimeCounter(Long init){
		this.maxTime = init;
		this.minTime = init;
		this.totalTime = init;
		this.round = 1;
	}
	public Double getMean(){
		return (this.totalTime.doubleValue()) / this.round;
	}
	public void addTime(Long init){
		this.totalTime += init;
		this.round ++;
		if(init > this.maxTime)
			this.maxTime = init;
		if(init < this.minTime)
			this.minTime = init;
	}
	public Long getMax(){
		return this.maxTime;
	}
	public Long getMin(){
		return this.minTime;
	}
	public String toString(){
		return "maxTime: " + this.getMax() + "\nminTime: " + this.getMin() + "\nmeanTime: " + this.getMean();
	}
}
