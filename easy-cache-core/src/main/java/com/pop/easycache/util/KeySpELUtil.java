package com.pop.easycache.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

/**
 * Created by xugang on 17/2/21.
 */
public class KeySpELUtil {
    public static final ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
    public static String getKey(MethodSignature signature, ProceedingJoinPoint pjp,String spel){
        EvaluationContext context = new StandardEvaluationContext();
        String[] parameterNames = signature.getParameterNames();
        Object[] parameterValues = pjp.getArgs();
        for (int i = 0; i < parameterNames.length; i++) {
            context.setVariable(parameterNames[i], parameterValues[i]);
        }
        ExpressionParser parser = new SpelExpressionParser();
        return parser.parseExpression(spel).getValue(context, String.class);
    }

    public static String getKey(Method method, Object[] args, String spel){
        EvaluationContext context = new StandardEvaluationContext();
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
        Object[] parameterValues = args;
        for (int i = 0; i < parameterNames.length; i++) {
            context.setVariable(parameterNames[i], parameterValues[i]);
        }
        ExpressionParser parser = new SpelExpressionParser();
        return parser.parseExpression(spel).getValue(context, String.class);
    }
}
