package com.bookstore.cart.interceptor;

import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.InterceptorChain;
import org.axonframework.messaging.MessageHandlerInterceptor;
import org.axonframework.messaging.unitofwork.UnitOfWork;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
public class CartCommandHandlerInterceptor implements MessageHandlerInterceptor<CommandMessage<?>> {
    @Override
    public Object handle(@Nonnull UnitOfWork<? extends CommandMessage<?>> unitOfWork, @Nonnull InterceptorChain interceptorChain) {
        try {
            interceptorChain.proceed();
        }
        catch (Exception e){
            throw new CommandExecutionException("An exception has occurred during command execution! Please try again!", e, exceptionDetails(e));
        }
        return null;
    }
    private ShowException exceptionDetails(Throwable throwable) {
        return new ShowException("Err", throwable.getMessage());
    }
    static class ShowException{

        public ShowException(String name, String message) {
        }
    }
}
