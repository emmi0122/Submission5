package se.yrgo.advice;

import org.aspectj.lang.ProceedingJoinPoint;

public class PerformanceTimingAdvice  {
    public Object performTimingMeasurement(ProceedingJoinPoint method) throws Throwable{
        double start = System.nanoTime();

        try {
            Object value = method.proceed();
            return value;
        } finally {
            double end = System.nanoTime();
            double timeTaken = end - start;
            System.out.println("It took " + method.getSignature().getName() + " from the class " + method.getTarget().getClass().getName() + " took " + (timeTaken / 1000000) + "ms");
        }

        
    }
}
