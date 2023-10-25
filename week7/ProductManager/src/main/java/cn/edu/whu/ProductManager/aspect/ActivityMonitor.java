package cn.edu.whu.ProductManager.aspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Hashtable;

@Aspect
@Component
public class ActivityMonitor{
	private Hashtable<String, Integer> invocationCounter = new Hashtable<String, Integer>();
	private Hashtable<String, TimeCounter> runtimeCounter = new Hashtable<String, TimeCounter>();
	private Hashtable<String, Integer> exceptionCounter = new Hashtable<String, Integer>();

	@Around("@within(org.springframework.stereotype.Service)")
	public Object calculateTime(ProceedingJoinPoint joinPoint) throws Throwable {
		long t1= Calendar.getInstance().getTimeInMillis();
		Object retValue = joinPoint.proceed();
		long t2 = Calendar.getInstance().getTimeInMillis();
		String methodSig = joinPoint.getSignature().toString();
		int base = invocationCounter.containsKey(methodSig) ? invocationCounter.get(methodSig) : 0;
		invocationCounter.put(methodSig, base+1);
		TimeCounter tc = runtimeCounter.getOrDefault(methodSig, null);
		if(tc != null)
			tc.addTime(t2 - t1);
		else
			runtimeCounter.put(methodSig, new TimeCounter(t2 - t1));
		return retValue;
	}


	@AfterThrowing(pointcut="@within(org.springframework.stereotype.Service)", throwing="e")
        private void addException(JoinPoint j, Exception e){
                int base = this.exceptionCounter.containsKey(j.getSignature()) ? this.exceptionCounter.get(j.getSignature()) : 0;
                this.exceptionCounter.put(j.getSignature().toString(), base + 1);
        }

        public Hashtable<String, Integer> getExceptionMap(){
                return this.exceptionCounter;
        }
	public Hashtable<String, Integer> getInvocationMap(){
		return this.invocationCounter;
	}
	public Hashtable<String, TimeCounter> getTimeMap(){
		return this.runtimeCounter;
	}
}
