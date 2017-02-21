package com.pop.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * Created by xugang on 17/2/21.
 */
public class KeySpELUtil {
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
}
